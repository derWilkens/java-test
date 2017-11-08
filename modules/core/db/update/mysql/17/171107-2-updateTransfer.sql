alter table PAXBASE_TRANSFER drop column TRANSFER_ORDER_NO cascade ;
alter table PAXBASE_TRANSFER add column TRANSFER_ORDER_NO integer ^
update PAXBASE_TRANSFER set TRANSFER_ORDER_NO = 0 where TRANSFER_ORDER_NO is null ;
alter table PAXBASE_TRANSFER modify column TRANSFER_ORDER_NO integer not null ;
