alter table SEC_USER add column DEPARTMENT_ID varchar(32) ;
alter table SEC_USER drop column OE_ID cascade ;
