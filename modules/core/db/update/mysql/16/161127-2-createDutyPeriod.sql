alter table PAXBASE_DUTY_PERIOD add constraint FK_PAXBASE_DUTY_PERIOD_USER foreign key (USER_ID) references PAXBASE_OFFSHORE_USER(ID);
alter table PAXBASE_DUTY_PERIOD add constraint FK_PAXBASE_DUTY_PERIOD_SITE foreign key (SITE_ID) references PAXBASE_SITE(ID);
create index IDX_PAXBASE_DUTY_PERIOD_SITE on PAXBASE_DUTY_PERIOD (SITE_ID);
create index IDX_PAXBASE_DUTY_PERIOD_USER on PAXBASE_DUTY_PERIOD (USER_ID);
