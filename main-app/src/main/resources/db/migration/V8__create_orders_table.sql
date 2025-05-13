CREATE TABLE orders
(
    id_           bigserial primary key,
    created_at_   timestamp with time zone default now()   not null,
    updated_at_   timestamp with time zone default now()   not null,
    removed_      bool                     default (false) not null,

    from_date_    date                                     not null,
    to_data_      date                                     not null,
    hotel_id_     bigint                                   not null,
    user_id_      bigint                                   not null,
    room_type_id_ bigint                                   not null
);