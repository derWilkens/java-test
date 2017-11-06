alter table PAXBASE_TRANSFER drop column TRANSFER_ORDER_NO cascade ;
alter table PAXBASE_TRANSFER add column TRANSFER_ORDER_NO integer default 0 not null ;
