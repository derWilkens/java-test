alter table PAXBASE_SITE add column ITEM_DESIGNATION varchar(4) ;
alter table PAXBASE_SITE add column SHORT_ITEM_DESIGNATION varchar(4) ;
alter table PAXBASE_SITE drop column SAP_DESIGNATION cascade ;
