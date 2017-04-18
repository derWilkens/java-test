alter table PAXBASE_SITE_TYPE add column CLIENT integer ^
update PAXBASE_SITE_TYPE set CLIENT = 0 where CLIENT is null ;
alter table PAXBASE_SITE_TYPE modify column CLIENT integer not null ;
