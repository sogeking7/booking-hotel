create table hotels
(
    id_         bigserial primary key,
    created_at_ timestamp with time zone default now()   not null,
    updated_at_ timestamp with time zone default now()   not null,
    removed_    bool                     default (false) not null,

    name_       varchar(255)                             not null,
    address_    varchar(255)                             not null,
    phone_      varchar(255)                             not null
);