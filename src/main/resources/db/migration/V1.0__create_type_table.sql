create table if not exists type(
    id serial,
    name varchar(100) not null,
    constraint pk_type primary key(id)
);