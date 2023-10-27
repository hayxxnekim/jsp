--2023-09-20
 CREATE TABLE board (
  bno int NOT NULL AUTO_INCREMENT,
  title varchar(200) NOT NULL,
  writer varchar(100) NOT NULL,
  content text,
  regdate datetime default now(),
  moddate datetime default now(),
  readcount int default 0,
  PRIMARY KEY (`bno`));
  
--2023-09-21
create table member(
id varchar(100),
pwd varchar(100) not null,
email varchar(100) not null,
age int default 0,
regdate datetime default now(),
lastlogin datetime default now(),
primary key(id));

--2023-09-22
create table comment(
cno int auto_increment,
bno int not null,
writer varchar(200) not null default "unknown",
content varchar(1000),
regdate datetime default now(),
primary key(cno));

--2023-09-25
alter table board add image_File varchar(500);