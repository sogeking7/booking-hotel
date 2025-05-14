create extension if not exists pgcrypto;

create table sessions
(
    token   text        not null primary key default encode(gen_random_bytes(32), 'base64'),
    user_id bigint      not null,
    created timestamptz not null             default clock_timestamp(),
    expires timestamptz not null             default clock_timestamp() + '15min'::interval,
    check (expires > created)
);