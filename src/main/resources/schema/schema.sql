drop table if exists Member;
create table MEMBER(
    id varchar(30) primary key,
    name varchar(30),
    pwd varchar(30),
    email varchar(40)
);