-- ----------------------------
-- Table structure for association_value_entry
-- ----------------------------
DROP TABLE IF EXISTS "public"."association_value_entry";
CREATE TABLE "public"."association_value_entry" (
"id" int8 NOT NULL,
"association_key" varchar(255) COLLATE "default" NOT NULL,
"association_value" varchar(255) COLLATE "default",
"saga_id" varchar(255) COLLATE "default" NOT NULL,
"saga_type" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for domain_event_entry
-- ----------------------------
DROP TABLE IF EXISTS "public"."domain_event_entry";
CREATE TABLE "public"."domain_event_entry" (
"global_index" int8 NOT NULL,
"event_identifier" varchar(255) COLLATE "default" NOT NULL,
"meta_data" bytea,
"payload" bytea NOT NULL,
"payload_revision" varchar(255) COLLATE "default",
"payload_type" varchar(255) COLLATE "default" NOT NULL,
"time_stamp" varchar(255) COLLATE "default" NOT NULL,
"aggregate_identifier" varchar(255) COLLATE "default" NOT NULL,
"sequence_number" int8 NOT NULL,
"type" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for saga_entry
-- ----------------------------
DROP TABLE IF EXISTS "public"."saga_entry";
CREATE TABLE "public"."saga_entry" (
"saga_id" varchar(255) COLLATE "default" NOT NULL,
"revision" varchar(255) COLLATE "default",
"saga_type" varchar(255) COLLATE "default",
"serialized_saga" oid
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for snapshot_event_entry
-- ----------------------------
DROP TABLE IF EXISTS "public"."snapshot_event_entry";
CREATE TABLE "public"."snapshot_event_entry" (
"aggregate_identifier" varchar(255) COLLATE "default" NOT NULL,
"sequence_number" int8 NOT NULL,
"type" varchar(255) COLLATE "default" NOT NULL,
"event_identifier" varchar(255) COLLATE "default" NOT NULL,
"meta_data" bytea,
"payload" bytea NOT NULL,
"payload_revision" varchar(255) COLLATE "default",
"payload_type" varchar(255) COLLATE "default" NOT NULL,
"time_stamp" varchar(255) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for token_entry
-- ----------------------------
DROP TABLE IF EXISTS "public"."token_entry";
CREATE TABLE "public"."token_entry" (
"processor_name" varchar(255) COLLATE "default" NOT NULL,
"segment" int4 NOT NULL,
"owner" varchar(255) COLLATE "default",
"timestamp" varchar(255) COLLATE "default" NOT NULL,
"token" oid,
"token_type" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By
-- ----------------------------

-- ----------------------------
-- Indexes structure for table association_value_entry
-- ----------------------------
CREATE INDEX "idxk45eqnxkgd8hpdn6xixn8sgft" ON "public"."association_value_entry" USING btree ("saga_type", "association_key", "association_value");
CREATE INDEX "idxgv5k1v2mh6frxuy5c0hgbau94" ON "public"."association_value_entry" USING btree ("saga_id", "saga_type");

-- ----------------------------
-- Primary Key structure for table association_value_entry
-- ----------------------------
ALTER TABLE "public"."association_value_entry" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table domain_event_entry
-- ----------------------------
ALTER TABLE "public"."domain_event_entry" ADD UNIQUE ("aggregate_identifier", "sequence_number");
ALTER TABLE "public"."domain_event_entry" ADD UNIQUE ("event_identifier");

-- ----------------------------
-- Primary Key structure for table domain_event_entry
-- ----------------------------
ALTER TABLE "public"."domain_event_entry" ADD PRIMARY KEY ("global_index");

-- ----------------------------
-- Primary Key structure for table saga_entry
-- ----------------------------
ALTER TABLE "public"."saga_entry" ADD PRIMARY KEY ("saga_id");

-- ----------------------------
-- Uniques structure for table snapshot_event_entry
-- ----------------------------
ALTER TABLE "public"."snapshot_event_entry" ADD UNIQUE ("event_identifier");

-- ----------------------------
-- Primary Key structure for table snapshot_event_entry
-- ----------------------------
ALTER TABLE "public"."snapshot_event_entry" ADD PRIMARY KEY ("aggregate_identifier", "sequence_number", "type");

-- ----------------------------
-- Primary Key structure for table token_entry
-- ----------------------------
ALTER TABLE "public"."token_entry" ADD PRIMARY KEY ("processor_name", "segment");


-- domain table


-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS "public"."company";
CREATE TABLE "public"."company" (
"company_id" varchar(255) COLLATE "default" NOT NULL,
"created_at" timestamp(6),
"name" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;
ALTER TABLE "public"."company" ADD PRIMARY KEY ("company_id");