alter table PAXBASE_CAMPAIGN add constraint FK_PAXBASE_CAMPAIGN_TYPE foreign key (TYPE_ID) references PAXBASE_PERIOD_TYPE(ID);
create index IDX_PAXBASE_CAMPAIGN_TYPE on PAXBASE_CAMPAIGN (TYPE_ID);
