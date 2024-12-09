--liquibase formatted sql

--changeset db_user_01:1
CREATE TABLE Customer (
    id int,
    LastName varchar(255),
    FirstName varchar(255),
    City varchar(255)
);