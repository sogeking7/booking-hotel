create table countries
(
    id_         bigserial primary key,
    created_at_ timestamp with time zone default now() not null,
    updated_at_ timestamp with time zone default now() not null,
    removed_    bool                     default (false),

    name_       varchar(50)                            not null,
    code_       varchar(5)                             not null,
    currency_   varchar(5)                             not null
);

create table cities
(
    id_         bigserial primary key,
    created_at_ timestamp with time zone default now() not null,
    updated_at_ timestamp with time zone default now() not null,
    removed_    bool                     default (false),

    name_       varchar(50)                            not null,
    country_id_ bigint
);


alter table hotels
    add column city_id_ bigint