alter table PAXBASE_DUTY_PERIOD add constraint FK_PAXBASE_DUTY_PERIOD_CONTRACTOR foreign key (CONTRACTOR_ID) references PAXBASE_COMPANY(ID);
create index IDX_PAXBASE_DUTY_PERIOD_CONTRACTOR on PAXBASE_DUTY_PERIOD (CONTRACTOR_ID);
