alter table PAXBASE_CAMPAIGN add column TYPE_ID varchar(32) ;
alter table PAXBASE_CAMPAIGN drop column START_ cascade ;
alter table PAXBASE_CAMPAIGN add column START_ datetime(3) ;
alter table PAXBASE_CAMPAIGN drop column END_ cascade ;
alter table PAXBASE_CAMPAIGN add column END_ datetime(3) ;
