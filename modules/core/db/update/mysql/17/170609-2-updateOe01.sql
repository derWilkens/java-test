alter table PAXBASE_OE add constraint FK_PAXBASE_OE_LEADER foreign key (LEADER_ID) references SEC_USER(ID);
create index IDX_PAXBASE_OE_LEADER on PAXBASE_OE (LEADER_ID);
