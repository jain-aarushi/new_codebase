  
 CREATE TABLE EMPLOYEE (
	employee_id BIGINT auto_increment primary key,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	dept_name varchar(255) not null,
	age int not null,
);

create sequence employee_sequence start with 1 increment by 1; 
