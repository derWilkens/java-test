alter table PAXBASE_OPERATION_PERIOD add constraint FK_PAXBASE_OPERATION_PERIOD_PARENT_PERIOD foreign key (PARENT_PERIOD_ID) references PAXBASE_OPERATION_PERIOD(ID);
alter table PAXBASE_OPERATION_PERIOD add constraint FK_PAXBASE_OPERATION_PERIOD_SITE foreign key (SITE_ID) references PAXBASE_SITE(ID);
alter table PAXBASE_OPERATION_PERIOD add constraint FK_PAXBASE_OPERATION_PERIOD_FUNCTION_CATEGORY foreign key (FUNCTION_CATEGORY_ID) references PAXBASE_FUNCTION_CATEGORY(ID);
create index IDX_PAXBASE_OPERATION_PERIOD_PARENT_PERIOD on PAXBASE_OPERATION_PERIOD (PARENT_PERIOD_ID);
create index IDX_PAXBASE_OPERATION_PERIOD_SITE on PAXBASE_OPERATION_PERIOD (SITE_ID);
create index IDX_PAXBASE_OPERATION_PERIOD_FUNCTION_CATEGORY on PAXBASE_OPERATION_PERIOD (FUNCTION_CATEGORY_ID);
