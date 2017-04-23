alter table PAXBASE_OE add constraint FK_PAXBASE_OE_PARENT_OE foreign key (PARENT_OE_ID) references PAXBASE_OE(ID);
create index IDX_PAXBASE_OE_PARENT_OE on PAXBASE_OE (PARENT_OE_ID);
