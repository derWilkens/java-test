alter table SEC_USER add constraint FK_SEC_USER_OE foreign key (OE_ID) references PAXBASE_OE(ID);
create index IDX_SEC_USER_OE on SEC_USER (OE_ID);
