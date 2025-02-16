--liquibase formatted sql

--changeset db_user_01:1
CREATE TABLE Customer (
    id int,
    LastName varchar(255),
    FirstName varchar(255),
    City varchar(255)
);

--changeset db_user_01:2
ALTER TABLE customer ADD column license_number varchar(255);
--rollback ALTER TABLE customer DROP COLUMN license_number;