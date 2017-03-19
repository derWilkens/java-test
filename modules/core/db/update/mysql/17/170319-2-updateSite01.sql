alter table PAXBASE_SITE add column SITE_NAME varchar(50) ^
update PAXBASE_SITE set SITE_NAME = '' where SITE_NAME is null ;
alter table PAXBASE_SITE modify column SITE_NAME varchar(50) not null ;
alter table PAXBASE_SITE drop column NAME cascade ;
