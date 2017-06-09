alter table PAXBASE_FUNCTION_ROLE_LINK add constraint FK_PFRL_JOBFUNCTION foreign key (FUNCTION_ID) references PAXBASE_JOBFUNCTION(ID);
alter table PAXBASE_FUNCTION_ROLE_LINK add constraint FK_PFRL_ROLE foreign key (ROLE_ID) references PAXBASE_ROLE(ID);
