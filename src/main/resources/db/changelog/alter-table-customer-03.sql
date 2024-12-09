--liquibase formatted sql

--changeset db_user_01:1
ALTER TABLE customer ADD column license_number varchar(255);
--rollback ALTER TABLE customer DROP COLUMN license_number;