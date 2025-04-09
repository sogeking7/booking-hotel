create table users
(
    id_            serial primary key,
    first_name_    varchar(50)                            not null,
    last_name_     varchar(50)                            not null,
    email_         varchar(50) unique                     not null,
    password_hash_ varchar(255)                           not null,
    created_at_    timestamp with time zone default now() not null
)