alter table PAXBASE_PERIOD_TYPE add column TYPE_NAME varchar(20) ;
alter table PAXBASE_PERIOD_TYPE drop column TYPE_ cascade ;
