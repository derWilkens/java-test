alter table PAXBASE_CONTRACTOR_DUTY_PERIOD add constraint FK_PAXBASE_CONTRACTOR_DUTY_PERIOD_CONTRACTOR foreign key (CONTRACTOR_ID) references PAXBASE_COMPANY(ID);
alter table PAXBASE_CONTRACTOR_DUTY_PERIOD add constraint FK_PAXBASE_CONTRACTOR_DUTY_PERIOD_FUNCTION_CATEGORY foreign key (FUNCTION_CATEGORY_ID) references PAXBASE_FUNCTION_CATEGORY(ID);
create index IDX_PAXBASE_CONTRACTOR_DUTY_PERIOD_CONTRACTOR on PAXBASE_CONTRACTOR_DUTY_PERIOD (CONTRACTOR_ID);
create index IDX_PAXBASE_CONTRACTOR_DUTY_PERIOD_FUNCTION_CATEGORY on PAXBASE_CONTRACTOR_DUTY_PERIOD (FUNCTION_CATEGORY_ID);
