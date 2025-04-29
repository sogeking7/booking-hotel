create table users
(
    id_            bigserial primary key,
    created_at_    timestamp with time zone default now() not null,
    updated_at_    timestamp with time zone default now() not null,
    removed_       bool                     default (false),

    first_name_    varchar(50)                            not null,
    last_name_     varchar(50)                            not null,
    email_         varchar(50) unique                     not null,
    password_hash_ varchar(255)                           not null
);

create type user_role as enum ('admin', 'user');

alter table users
    add column role_ user_role;