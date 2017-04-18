alter table PAXBASE_MODE_OF_TRANSFER add column CLIENT integer ^
update PAXBASE_MODE_OF_TRANSFER set CLIENT = 0 where CLIENT is null ;
alter table PAXBASE_MODE_OF_TRANSFER modify column CLIENT integer not null ;
