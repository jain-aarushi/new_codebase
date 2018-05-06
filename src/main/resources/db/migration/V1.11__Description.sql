 CREATE TABLE STUDENT (
	roll_no BIGINT primary key,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	dept_name varchar(255) not null,
	age int not null,
);

INSERT INTO STUDENT VALUES(1,'Aarushi','Jain','LS',23)
