drop table if exists postvote;
drop table if exists comment;
drop table if exists POST;
drop table if exists Member;
create table MEMBER(
    member_id varchar(30) primary key,
    name varchar(30),
    pwd varchar(30),
    email varchar(40)
);
create table POST(
                     post_id integer auto_increment primary key ,
                     title varchar(254),
                     content longtext,
                    writer_id varchar(30),
                    parent_id integer,
                    file1 varchar(254),
                    file2 varchar(254),
                    score integer default 0,
                    constraint post_member_writer_id_fk foreign key (writer_id) references member(member_id) on delete cascade ,
                    constraint post_post_parent_id_fk foreign key (parent_id) references post(post_id) on delete cascade ,
                    unique(post_id,file1,file2)
                 );
create table comment(
    comment_id bigint auto_increment primary key ,
    content longtext,
    writer_id varchar(30),
    post_id integer,
    constraint comment_member_writer_id_fk foreign key (writer_id) references member(member_id) on delete cascade ,
    constraint comment_post_post_id_fk foreign key (post_id) references post(post_id) on delete cascade
);

ALTER TABLE post
    ADD FULLTEXT INDEX idx_fulltext_title (title);

CREATE INDEX idx_post_id_desc ON post (post_id DESC);

CREATE TABLE postVote (
                          MEMBER_ID varchar(30),
                          POST_ID INTEGER,
                          SCORE INTEGER default 0,
                          PRIMARY KEY (MEMBER_ID, POST_ID),
                          FOREIGN KEY (MEMBER_ID) REFERENCES member (member_id),
                          FOREIGN KEY (POST_ID) REFERENCES post (post_id)
);
