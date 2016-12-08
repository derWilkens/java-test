alter table PAXBASE_TRANSFER add column MODE_OF_TRANSFER_ID varchar(32) ;
alter table PAXBASE_TRANSFER drop column MODE_OF_TRANSPORT cascade ;
