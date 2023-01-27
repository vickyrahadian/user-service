drop table if exists auth_user_role cascade;
drop table if exists auth_role cascade;
drop table if exists auth_user_account cascade;

create table auth_role
(
    id            bigserial primary key,
    role_name     varchar(255)
        constraint uk_auth_role unique,
    secure_id     varchar(255) not null
        constraint uk_role_secure_id
            unique,
    deleted       boolean      not null,
    created_date  timestamp(6),
    modified_date timestamp(6)
);

alter table auth_role
    owner to userdb;


-- auto-generated definition
create table auth_user_account
(
    id            bigserial primary key,
    username      varchar(255),
    full_name     varchar(255),
    email         varchar(255),
    password      varchar(255),
    secure_id     varchar(255) not null
        constraint uk_account_secure_id
            unique,
    deleted       boolean      not null,
    created_date  timestamp(6) not null,
    modified_date timestamp(6)
);

alter table auth_user_account
    owner to userdb;


-- auto-generated definition
create table auth_user_role
(
    user_id varchar(255) not null,
    role_id bigint       not null
        constraint r_user_role references auth_role
);

alter table auth_user_role
    owner to userdb;

