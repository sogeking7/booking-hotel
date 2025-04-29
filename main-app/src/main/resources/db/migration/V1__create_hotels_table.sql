create table hotels
(
    id_         bigserial primary key,
    created_at_ timestamp with time zone default now() not null,
    updated_at_ timestamp with time zone default now() not null,
    removed_    bool                     default (false),

    name_       varchar(255)                           not null,
    address_    varchar(255)                           not null,
    phone_      varchar(255)                           not null
);

CREATE TABLE orders
(
    id_         bigserial primary key,
    created_at_ timestamp with time zone default now() not null,
    updated_at_ timestamp with time zone default now() not null,
    removed_    bool                     default (false),

    from_date_  date                                   not null,
    to_data_    date                                   not null,
    hotel_id_   bigint                                 not null,
    user_id_    bigint                                 not null
);