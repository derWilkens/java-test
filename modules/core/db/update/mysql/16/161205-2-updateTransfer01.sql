alter table PAXBASE_TRANSFER add constraint FK_PAXBASE_TRANSFER_MODE_OF_TRANSFER foreign key (MODE_OF_TRANSFER_ID) references PAXBASE_MODE_OF_TRANSFER(ID);
create index IDX_PAXBASE_TRANSFER_MODE_OF_TRANSFER on PAXBASE_TRANSFER (MODE_OF_TRANSFER_ID);