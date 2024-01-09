CREATE DATABASE IF NOT EXISTS petscreening;

create table if not exists petscreening_schema.pet_owner
(
    id         bigint primary key not null ,
    p_id       varchar not null unique,
    created_at timestamp with time zone,
    birthdate  date,
    name       varchar,
    email      varchar,
    address    varchar,
    phone      varchar
);

create table if not exists petscreening_schema.pet
(
    id                 bigint  not null primary key,
    p_id               varchar not null unique,
    created_at         timestamp with time zone,
    pet_owner          varchar,
    birthdate          date,
    name               varchar,
    weight             varchar,
    breed              varchar,
    vaccination_status varchar,
    training_level     int
);

alter table petscreening_schema.pet
    add constraint fk_pet_owner_p_id
        foreign key (pet_owner)
            references petscreening_schema.pet_owner(p_id);
