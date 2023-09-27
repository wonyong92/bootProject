drop table if exists Member;
create table MEMBER(
    id varchar(30) primary key,
    name varchar(30),
    pwd varchar(30),
    email varchar(40)
);
drop table if exists Post;

create table POST(
    id integer auto_increment primary key ,
    title varchar(254),
    content text
)