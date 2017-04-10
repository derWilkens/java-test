alter table PAXBASE_PERIOD_TYPE add column PARENT_TYPE_ID varchar(32) ;
alter table PAXBASE_PERIOD_TYPE add column PERIOD_SUB_CLASS varchar(50) ;
alter table PAXBASE_PERIOD_TYPE drop column TYPE_GROUP cascade ;
