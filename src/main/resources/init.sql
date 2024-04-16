create database sharding_test;

use sharding_test;

create table test_0 (
    `id` INT(11) PRIMARY KEY,
    `content` VARCHAR(32)
);

create table test_1 like test_0;
create table test_2 like test_0;
create table test_3 like test_0;
create table test_4 like test_0;