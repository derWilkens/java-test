alter table PAXBASE_SITE_ROLE_RULE add constraint FK_PAXBASE_SITE_ROLE_RULE_ROLE foreign key (ROLE_ID) references PAXBASE_ROLE(ID);
create index IDX_PAXBASE_SITE_ROLE_RULE_ROLE on PAXBASE_SITE_ROLE_RULE (ROLE_ID);
