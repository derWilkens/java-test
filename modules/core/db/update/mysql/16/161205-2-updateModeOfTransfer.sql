alter table PAXBASE_MODE_OF_TRANSFER add column MODE_ varchar(50) ;
alter table PAXBASE_MODE_OF_TRANSFER drop column NAME cascade ;
