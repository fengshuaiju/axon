FROM debezium/connect:1.1
ENV DEBEZIUM_DIR=$KAFKA_CONNECT_PLUGINS_DIR/debezium-transformer

RUN mkdir $DEBEZIUM_DIR
COPY ./target/connector-0.0.1-SNAPSHOT.jar $DEBEZIUM_DIR
