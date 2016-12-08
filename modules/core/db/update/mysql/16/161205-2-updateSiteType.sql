alter table PAXBASE_SITE_TYPE add column TYPE_ varchar(50) ;
alter table PAXBASE_SITE_TYPE drop column NAME cascade ;
