drop table auth_branch cascade ;
drop table auth_role;
drop table auth_user_account;
drop table auth_user_role;

create table public.auth_branch
(
    id                  bigserial
        primary key,
    secure_id           varchar(255) not null
        constraint uk_i8b73s6lk6ouq6hb6kdw82i6o
            unique,

    branch_abbreviation varchar(255)
        constraint uk_kokb9gpfpdfufwjplt6ey1ppa
            unique,
    branch_code         numeric
        constraint uk_poy7lye5al8r98t0lpr4x2eeu
            unique,
    branch_level        varchar(255),
    branch_name         varchar(255),
    branch_region       varchar(255),
    deleted             boolean      not null,
    created_date        timestamp(6),
    modified_date       timestamp(6)
);

alter table public.auth_branch
    owner to userdb;

create table public.auth_role
(
    id            bigserial
        primary key,

    secure_id     varchar(255) not null
        constraint uk_oh4pjg0k0iba7c7sph9xfe1r9
            unique,
    role_name     varchar(255)
        constraint uk_7nny9jtiql7w5waa79gwppyhe
            unique,
    deleted       boolean      not null,
    created_date  timestamp(6),
    modified_date timestamp(6)
);

alter table public.auth_role
    owner to userdb;

create table public.auth_user_account
(
    id            bigserial
        primary key,

    secure_id     varchar(255) not null
        constraint uk_59esx8hp32ef1m6lvhuuk51c1
            unique,
    email         varchar(255),
    full_name     varchar(255),
    password      varchar(255),
    username      varchar(255),
    branch_code   varchar(255)
        constraint fkox5dfjf525dyripugh3cwocx5
            references public.auth_branch (branch_abbreviation),
    deleted       boolean      not null,
    created_date  timestamp(6),
    modified_date timestamp(6)

);

alter table public.auth_user_account
    owner to userdb;

create table public.auth_user_role
(
    user_id varchar(255) not null,
    role_id bigint       not null
        constraint fk3eldmba9luu9l0apl0791x8vd
            references public.auth_role
);

alter table public.auth_user_role
    owner to userdb;

