create table bed_types
(
    id_         bigserial primary key,
    created_at_ timestamp with time zone default now()   not null,
    updated_at_ timestamp with time zone default now()   not null,
    removed_    bool                     default (false) not null,

    name_       varchar(1000)                            not null,
    icon_ref_   varchar(1000)                            not null
)
