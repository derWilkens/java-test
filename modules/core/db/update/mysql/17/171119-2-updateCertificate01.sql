alter table PAXBASE_CERTIFICATE add constraint FK_PAXBASE_CERTIFICATE_APP_USER foreign key (APP_USER_ID) references PAXBASE_APP_USER(ID);
create index IDX_PAXBASE_CERTIFICATE_APP_USER on PAXBASE_CERTIFICATE (APP_USER_ID);
