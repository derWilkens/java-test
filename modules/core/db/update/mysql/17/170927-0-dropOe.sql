alter table paxbase_oe drop foreign key FK_PAXBASE_OE_PARENT_OE;
alter table sec_user drop foreign key FK_SEC_USER_OE;
drop table paxbase_oe cascade ;
