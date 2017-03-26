alter table PAXBASE_CAMPAIGN add column CAMPAIGN_NUMBER varchar(10) ;
alter table PAXBASE_CAMPAIGN add column SHUTDOWN_ boolean ;
alter table PAXBASE_CAMPAIGN add column START_ date ;
alter table PAXBASE_CAMPAIGN add column END_ date ;
alter table PAXBASE_CAMPAIGN drop column CAMPAIGN_NO cascade ;
alter table PAXBASE_CAMPAIGN drop column BEGIN_DATE cascade ;
alter table PAXBASE_CAMPAIGN drop column END_DATE cascade ;
