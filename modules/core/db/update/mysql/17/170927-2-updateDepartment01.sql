alter table PAXBASE_DEPARTMENT add constraint FK_PAXBASE_DEPARTMENT_PARENT_DEPARTMENT foreign key (PARENT_DEPARTMENT_ID) references PAXBASE_DEPARTMENT(ID);
create index IDX_PAXBASE_DEPARTMENT_PARENT_DEPARTMENT on PAXBASE_DEPARTMENT (PARENT_DEPARTMENT_ID);
