alter table PAXBASE_CAMPAIGN add constraint FK_PAXBASE_CAMPAIGN_FUNCTION_CATEGORY foreign key (FUNCTION_CATEGORY_ID) references PAXBASE_FUNCTION_CATEGORY(ID);
create index IDX_PAXBASE_CAMPAIGN_FUNCTION_CATEGORY on PAXBASE_CAMPAIGN (FUNCTION_CATEGORY_ID);
