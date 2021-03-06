alter table PAXBASE_APP_USER add constraint FK_PAXBASE_APP_USER_COMPANY foreign key (COMPANY_ID) references PAXBASE_COMPANY(ID);
alter table PAXBASE_APP_USER add constraint FK_PAXBASE_APP_USER_DEPARTMENT foreign key (DEPARTMENT_ID) references PAXBASE_DEPARTMENT(ID);
create index IDX_PAXBASE_APP_USER_COMPANY on PAXBASE_APP_USER (COMPANY_ID);
create index IDX_PAXBASE_APP_USER_DEPARTMENT on PAXBASE_APP_USER (DEPARTMENT_ID);
