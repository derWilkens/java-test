alter table PAXBASE_CRAFT_TYPE add constraint FK_PAXBASE_CRAFT_TYPE_MODE_OF_TRANSFER foreign key (MODE_OF_TRANSFER_ID) references PAXBASE_MODE_OF_TRANSFER(ID);
create index IDX_PAXBASE_CRAFT_TYPE_MODE_OF_TRANSFER on PAXBASE_CRAFT_TYPE (MODE_OF_TRANSFER_ID);