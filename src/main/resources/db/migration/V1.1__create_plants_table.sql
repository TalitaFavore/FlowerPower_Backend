create table if not exists plants(
    id serial,
    name varchar(100) not null,
    subtitle varchar(200) not null,
    price numeric(10, 2) not null,
    description varchar(300) not null,
    img varchar(100),
    type_id integer not null,
    constraint pk_plants primary key(id),
    constraint fk_plant_type foreign key(type_id) references type(id)
);
