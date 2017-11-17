alter table PAXBASE_DEPARTMENT drop column LEADER_ID cascade ;
alter table PAXBASE_DEPARTMENT add column LEADER_ID varchar(32) ;
alter table PAXBASE_DEPARTMENT drop column DEPUTY_LEADER_ID cascade ;
alter table PAXBASE_DEPARTMENT add column DEPUTY_LEADER_ID varchar(32) ;
