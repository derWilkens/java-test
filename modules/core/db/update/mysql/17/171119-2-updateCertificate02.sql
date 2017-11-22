alter table PAXBASE_CERTIFICATE add column CLIENT integer ^
update PAXBASE_CERTIFICATE set CLIENT = 0 where CLIENT is null ;
alter table PAXBASE_CERTIFICATE modify column CLIENT integer not null ;
