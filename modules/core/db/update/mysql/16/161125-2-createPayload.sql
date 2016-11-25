alter table PAXBASE_PAYLOAD add constraint FK_PAXBASE_PAYLOAD_CRAFT_TYPE foreign key (CRAFT_TYPE_ID) references PAXBASE_CRAFT_TYPES(ID);
alter table PAXBASE_PAYLOAD add constraint FK_PAXBASE_PAYLOAD_SITE_A foreign key (SITE_A_ID) references PAXBASE_SITE(ID);
alter table PAXBASE_PAYLOAD add constraint FK_PAXBASE_PAYLOAD_SITE_B foreign key (SITE_B_ID) references PAXBASE_SITE(ID);
create index IDX_PAXBASE_PAYLOAD_SITE_A on PAXBASE_PAYLOAD (SITE_A_ID);
create index IDX_PAXBASE_PAYLOAD_SITE_B on PAXBASE_PAYLOAD (SITE_B_ID);
create index IDX_PAXBASE_PAYLOAD_CRAFT_TYPE on PAXBASE_PAYLOAD (CRAFT_TYPE_ID);
