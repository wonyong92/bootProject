drop table if exists POST;
drop table if exists Member;
create table MEMBER(
    id varchar(30) primary key,
    name varchar(30),
    pwd varchar(30),
    email varchar(40)
);
create table POST(
                     id integer auto_increment primary key ,
                     title varchar(254),
                     content longtext,
                    writer_id varchar(30),
                    foreign key (writer_id) references member(id)
                 );
