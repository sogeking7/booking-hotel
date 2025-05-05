create table room_types
(
    id_          bigserial primary key,
    created_at_  timestamp with time zone default now()   not null,
    updated_at_  timestamp with time zone default now()   not null,
    removed_     bool                     default (false) not null,

    hotel_id_    bigint                                   not null,
    bed_type_id_ bigint                                   not null,

    name_        varchar(1000)                            not null,
    count_       int                                      not null
)