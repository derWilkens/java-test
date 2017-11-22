alter table PAXBASE_MAINTENANCE_CAMPAIGN add constraint FK_PAXBASE_MAINTENANCE_CAMPAIGN_SITE foreign key (SITE_ID) references PAXBASE_SITE(ID);
alter table PAXBASE_MAINTENANCE_CAMPAIGN add constraint FK_PAXBASE_MAINTENANCE_CAMPAIGN_FUNCTION_CATEGORY foreign key (FUNCTION_CATEGORY_ID) references PAXBASE_FUNCTION_CATEGORY(ID);
create index IDX_PAXBASE_MAINTENANCE_CAMPAIGN_SITE on PAXBASE_MAINTENANCE_CAMPAIGN (SITE_ID);
create index IDX_PAXBASE_MAINTENANCE_CAMPAIGN_FUNCTION_CATEGORY on PAXBASE_MAINTENANCE_CAMPAIGN (FUNCTION_CATEGORY_ID);
