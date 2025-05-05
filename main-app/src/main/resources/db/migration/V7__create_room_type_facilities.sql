create table room_type_facilities
(
    id_           bigserial primary key,
    created_at_   timestamp with time zone default now()   not null,
    updated_at_   timestamp with time zone default now()   not null,
    removed_      bool                     default (false) not null,

    room_type_id_ bigint                                   not null,
    facility_id_  bigint                                   not null
)