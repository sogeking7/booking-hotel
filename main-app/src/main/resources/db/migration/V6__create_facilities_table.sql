create type facility_types as enum ('main', 'specific');

create table facilities
(
    id_         bigserial primary key,
    created_at_ timestamp with time zone default now()   not null,
    updated_at_ timestamp with time zone default now()   not null,
    removed_    bool                     default (false) not null,

    name_       varchar(1000)                            not null,
    icon_ref_   varchar(1000)                            not null,
    type_       facility_types
);


