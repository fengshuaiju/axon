#! /bin/bash
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" 127.0.0.1:8083/connectors/ -d '{
  "name": "axon-connector",
  "config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
    "tasks.max": 1,
    "database.hostname": "postgres",
    "database.port": "5432",
    "database.user": "postgres",
    "database.dbname": "command",
    "database.password": "fengshuaiju",
    "database.server.name": "command",
    "tombstones.on.delete": "false",
    "table.whitelist": "public.domain_event_entry",
    "transforms": "outbox",
    "transforms.outbox.type": "com.feng.axon.connector.AxonEventTransformation",
    "heartbeat.interval.ms": "1000",
    "plugin.name": "pgoutput"
  }
}'