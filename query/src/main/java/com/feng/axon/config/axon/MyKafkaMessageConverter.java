package com.feng.axon.config.axon;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
import org.axonframework.messaging.MetaData;
import org.axonframework.serialization.LazyDeserializingObject;
import org.axonframework.serialization.SerializedMessage;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.SimpleSerializedObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.axonframework.common.Assert.notNull;
import static org.axonframework.extensions.kafka.eventhandling.HeaderUtils.*;
import static org.axonframework.messaging.Headers.*;

@Slf4j
public class MyKafkaMessageConverter implements KafkaMessageConverter<String, byte[]> {
    private final Serializer serializer;
    private final ObjectMapper objectMapper;
    private TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
    private static final String METADATA_COMMANDER = MESSAGE_METADATA + "-commander";

    public MyKafkaMessageConverter(Serializer serializer, ObjectMapper objectMapper) {
        this.serializer = serializer;
        this.objectMapper = objectMapper;
    }

    @Override
    public ProducerRecord<String, byte[]> createKafkaMessage(EventMessage<?> eventMessage, String topic) {
        throw new UnsupportedOperationException("This converter is only used to read kafka message.");
    }

    @Override
    public Optional<EventMessage<?>> readKafkaMessage(ConsumerRecord<String, byte[]> consumerRecord) {
        try {
            Headers headers = consumerRecord.headers();
            if (isAxonMessage(headers)) {
                byte[] payload = extractPayload(consumerRecord.value());
                SerializedMessage<?> message = extractSerializedMessage(headers, payload);
                Optional<EventMessage<?>> eventMessage = buildMessage(headers, message);
                if (log.isTraceEnabled()) {
                    eventMessage.ifPresent(m -> log.trace("EventMessage: {}", m));
                }
                return eventMessage;
            }
        } catch (Exception e) {
            log.warn("Error converting ConsumerRecord [{}] to an EventMessage", consumerRecord, e);
        }

        return Optional.empty();
    }

    private byte[] extractPayload(byte[] messageBody) {
        try {
            Map<String, Object> messageMap = objectMapper.readValue(messageBody, typeReference);
            return Base64.decodeBase64((String) messageMap.get("payload"));
        } catch (IOException e) {
            log.error("Extract payload from message body failed:", e);
            throw new RuntimeException(e);
        }
    }

    private boolean isAxonMessage(Headers headers) {
        return keys(headers).containsAll(Arrays.asList(MESSAGE_ID, MESSAGE_TYPE));
    }

    private SerializedMessage<?> extractSerializedMessage(Headers headers, byte[] payload) {
        SimpleSerializedObject<byte[]> serializedObject = new SimpleSerializedObject<>(
                payload,
                byte[].class,
                valueAsString(headers, MESSAGE_TYPE),
                valueAsString(headers, MESSAGE_REVISION, null)
        );

        return new SerializedMessage<>(
                valueAsString(headers, MESSAGE_ID),
                new LazyDeserializingObject<>(serializedObject, serializer),
                new LazyDeserializingObject<>(MetaData.from(extractAxonMetadata(headers)))
        );
    }

    private Map<String, Object> extractAxonMetadata(Headers headers) {
        notNull(headers, () -> "Headers may not be null");

        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> isValidMetadataKey(header.key()))
                .collect(Collectors.toMap(
                        header -> extractKey(header.key()),
                        this::extractValue
                ));
    }

    private Object extractValue(Header header) {
        if (StringUtils.equals(METADATA_COMMANDER, header.key())) {
            return Try.of(() -> objectMapper.readValue(Base64.decodeBase64(header.value()), User.class)).get();
        } else {
            return asString(header.value());
        }
    }

    private boolean isValidMetadataKey(String key) {
        return key.startsWith(MESSAGE_METADATA + "-");
    }

    private String asString(byte[] value) {
        return value != null ? new String(value, StandardCharsets.UTF_8) : null;
    }

    private Optional<EventMessage<?>> buildMessage(Headers headers, SerializedMessage<?> message) {
        long timestamp = Instant.parse(valueAsString(headers, MESSAGE_TIMESTAMP)).toEpochMilli();
        return headers.lastHeader(AGGREGATE_ID) != null
                ? buildDomainEvent(headers, message, timestamp)
                : buildEvent(message, timestamp);
    }

    private Optional<EventMessage<?>> buildDomainEvent(Headers headers, SerializedMessage<?> message, long timestamp) {
        return Optional.of(new GenericDomainEventMessage<>(
                valueAsString(headers, AGGREGATE_TYPE),
                valueAsString(headers, AGGREGATE_ID),
                Long.parseLong(valueAsString(headers, AGGREGATE_SEQ)),
                message,
                () -> Instant.ofEpochMilli(timestamp)
        ));
    }

    private Optional<EventMessage<?>> buildEvent(SerializedMessage<?> message, long timestamp) {
        return Optional.of(new GenericEventMessage<>(message, () -> Instant.ofEpochMilli(timestamp)));
    }

}
