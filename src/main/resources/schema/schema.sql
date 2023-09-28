drop table if exists comment;
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
                    parent_id integer,
                    file1 varchar(254),
                    file2 varchar(254),
                    constraint post_member_writer_id_fk foreign key (writer_id) references member(id),
                    constraint post_post_parent_id_fk foreign key (parent_id) references post(id),
                    unique(id,file1,file2)
                 );
create table comment(
    id bigint auto_increment primary key ,
    content longtext,
    writer_id varchar(30),
    post_id integer,
    constraint comment_member_writer_id_fk foreign key (writer_id) references member(id),
    constraint comment_post_post_id_fk foreign key (post_id) references post(id)
);

ALTER TABLE post
    ADD FULLTEXT INDEX idx_fulltext_title (title);