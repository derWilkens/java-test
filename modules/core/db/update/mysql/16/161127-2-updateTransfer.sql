alter table PAXBASE_TRANSFER add column TRANSFER_ORDER_NO varchar(50) ;
alter table PAXBASE_TRANSFER drop column SAP_ORDER_NO cascade ;
