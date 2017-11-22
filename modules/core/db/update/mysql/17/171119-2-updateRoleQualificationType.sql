alter table PAXBASE_ROLE_QUALIFICATION_TYPE add column CLIENT integer ^
update PAXBASE_ROLE_QUALIFICATION_TYPE set CLIENT = 0 where CLIENT is null ;
alter table PAXBASE_ROLE_QUALIFICATION_TYPE modify column CLIENT integer not null ;
