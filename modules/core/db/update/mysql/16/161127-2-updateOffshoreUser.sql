-- alter table PAXBASE_OFFSHORE_USER add column COMPANY_ID varchar(32) ^
-- update PAXBASE_OFFSHORE_USER set COMPANY_ID = <default_value> ;
-- alter table PAXBASE_OFFSHORE_USER modify column COMPANY_ID varchar(32) not null ;
alter table PAXBASE_OFFSHORE_USER add column COMPANY_ID varchar(32) not null ;
