create table todos
(
    id_          serial primary key,
    title_       varchar(255)                           not null,
    description_ varchar(1000),
    created_at_  timestamp with time zone default now() not null
);

alter table todos
    add column user_id_ int not null;

alter table todos
    add constraint fk_user
        foreign key (user_id_) references users (id_) on delete cascade;