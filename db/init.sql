CREATE DATABASE IF NOT EXISTS petscreening;

CREATE SCHEMA petscreening_schema;

create sequence owner_seq
    increment by 1;

alter sequence owner_seq owner to postgres;

create sequence pet_seq
    increment by 1;

alter sequence pet_seq owner to postgres;

create sequence pet_owner_seq
    increment by 1;

alter sequence pet_owner_seq owner to postgres;

create table if not exists petscreening_schema.pet_owner
(
    id         bigint  not null
    primary key,
    p_id       varchar not null
    unique,
    created_at timestamp with time zone,
    birthdate  date,
    name       varchar,
    email      varchar,
    address    varchar,
    phone      varchar
);

alter table petscreening_schema.pet_owner
    owner to postgres;

create table if not exists petscreening_schema.pet
(
    id                 bigint  not null
    primary key,
    p_id               varchar not null
    unique,
    created_at         timestamp with time zone,
    pet_owner          varchar
    constraint fk_pet_owner_p_id
    references pet_owner (p_id),
    birthdate          timestamp(6),
    name               varchar,
    weight             double precision,
    breed              varchar,
    vaccination_status boolean,
    training_level     integer
    );

alter table petscreening_schema.pet
    owner to postgres;


alter table petscreening_schema.pet
    add constraint fk_pet_owner_p_id
        foreign key (pet_owner)
            references petscreening_schema.pet_owner(p_id);
