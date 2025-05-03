create table files
(
    id_         bigserial primary key,
    created_at_ timestamp with time zone default now()   not null,
    updated_at_ timestamp with time zone default now()   not null,
    removed_    bool                     default (false) not null,

    name_       varchar(255)                             not null,
    url_path_   text                                     not null,
    type_       varchar(10)                              not null,
    size_       bigint                                   not null
);

create table media
(
    id_         bigserial primary key,
    created_at_ timestamp with time zone default now()   not null,
    updated_at_ timestamp with time zone default now()   not null,
    removed_    bool                     default (false) not null,

    ref_id_     bigint                                   not null,
    ref_type_   varchar(255)                             not null,

    file_id_    bigint                                   not null
);