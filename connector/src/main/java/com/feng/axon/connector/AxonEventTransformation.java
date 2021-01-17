package com.feng.axon.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.header.Headers;
import org.apache.kafka.connect.transforms.Transformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AxonEventTransformation<R extends ConnectRecord<R>> implements Transformation<R> {
    public static final String AXON_PREFIX = "axon-";
    public static final String AXON_MESSAGE_PREFIX = AXON_PREFIX + "message-";

    /**
     * Key pointing to a message global identifier.
     */
    public static final String MESSAGE_GLOBAL_INDEX = AXON_MESSAGE_PREFIX + "global-index";
    /**
     * Key pointing to a message identifier.
     */
    public static final String MESSAGE_ID = AXON_MESSAGE_PREFIX + "id";
    /**
     * Key pointing to the serialized payload of a message.
     */
    public static final String SERIALIZED_MESSAGE_PAYLOAD = AXON_PREFIX + "serialized-message-payload";
    /**
     * Key pointing to the payload type of a message.
     */
    public static final String MESSAGE_TYPE = AXON_MESSAGE_PREFIX + "type";
    /**
     * Key pointing to the revision of a message.
     */
    public static final String MESSAGE_REVISION = AXON_MESSAGE_PREFIX + "revision";
    /**
     * Key pointing to the timestamp of a message.
     */
    public static final String MESSAGE_TIMESTAMP = AXON_MESSAGE_PREFIX + "timestamp";
    /**
     * Key pointing to the aggregate identifier of a message.
     */
    public static final String AGGREGATE_ID = AXON_MESSAGE_PREFIX + "aggregate-id";
    /**
     * Key pointing to the aggregate sequence of a message.
     */
    public static final String AGGREGATE_SEQ = AXON_MESSAGE_PREFIX + "aggregate-seq";
    /**
     * Key pointing to the aggregate type of a message.
     */
    public static final String AGGREGATE_TYPE = AXON_MESSAGE_PREFIX + "aggregate-type";
    /**
     * Key pointing to the MetaData of a message.
     */
    public static final String MESSAGE_METADATA = AXON_PREFIX + "metadata-";
    /**
     * Key pointing to the deadline name of a DeadlineMessage.
     */
    public static final String DEADLINE_NAME = AXON_PREFIX + "deadline-name";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper objectMapper = new ObjectMapper();
    private TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
    };

    @Override
    public R apply(R record) {
        Struct kStruct = (Struct) record.value();

        if (StringUtils.endsWith(record.topic(), "_entry")) {
            String databaseOperation = kStruct.getString("op");

            //Handle only the Read's and Create's
            if ("r".equalsIgnoreCase(databaseOperation) || "c".equalsIgnoreCase(databaseOperation)) {
                // Get the details.
                Struct after = (Struct) kStruct.get("after");
                Long globalIndex = after.getInt64("global_index");
                String eventIdentifier = after.getString("event_identifier");
                byte[] metaData = after.getBytes("meta_data");
                byte[] payload = after.getBytes("payload");
                String payloadRevision = after.getString("payload_revision");
                String payloadType = after.getString("payload_type");
                Instant timestamp = Instant.parse(after.getString("time_stamp"));
                String aggregateIdentifier = after.getString("aggregate_identifier");
                Long sequenceNumber = after.getInt64("sequence_number");
                String type = after.getString("type");

                Headers headers = record.headers();
                addMetadataToHeaders(headers, bytesToMap(metaData));

                headers.addLong(MESSAGE_GLOBAL_INDEX, globalIndex);
                headers.addString(MESSAGE_ID, eventIdentifier);
                headers.addString(MESSAGE_REVISION, payloadRevision);
                headers.addString(MESSAGE_TYPE, payloadType);
                headers.addTimestamp(MESSAGE_TIMESTAMP, Date.from(timestamp));
                headers.addString(AGGREGATE_ID, aggregateIdentifier);
                headers.addLong(AGGREGATE_SEQ, sequenceNumber);
                headers.addString(AGGREGATE_TYPE, type);

                // Build the event to be published.
                record = record.newRecord(record.topic(), null, Schema.STRING_SCHEMA, aggregateIdentifier,
                        Schema.BYTES_SCHEMA, payload, timestamp.toEpochMilli(), headers);

                if (logger.isDebugEnabled()) {
                    logger.debug("Record transformed: {}", record);
                }
            }
        }

        return record;
    }

    private Map<String, Object> bytesToMap(byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, typeReference);
        } catch (IOException e) {
            logger.error("Convert bytes to map failed:", e);
        }
        return new HashMap<>();
    }

    private void addMetadataToHeaders(Headers headers, Map<String, Object> metadata) {
        metadata.forEach((k, v) -> {
            if (v instanceof String) {
                headers.addString(MESSAGE_METADATA + k, (String) v);
            } else if (v instanceof Long) {
                headers.addLong(MESSAGE_METADATA + k, (Long) v);
            } else if (v instanceof Map) {
                try {
                    headers.addBytes(MESSAGE_METADATA + k, objectMapper.writeValueAsBytes(v));
                } catch (JsonProcessingException e) {
                    logger.error("Write Map to JSON bytes failed:", e);
                }
            } else {
                logger.warn("Unrecognized value type: {}", v.getClass().getName());
            }
        });
    }

    @Override
    public ConfigDef config() {
        return new ConfigDef();
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
