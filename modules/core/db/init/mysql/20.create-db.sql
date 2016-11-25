-- begin PAXBASE_OFFSHORE_USER
alter table PAXBASE_OFFSHORE_USER add constraint FK_PAXBASE_OFFSHORE_USER_USER foreign key (USER_ID) references SEC_USER(ID)^
create index IDX_PAXBASE_OFFSHORE_USER_USER on PAXBASE_OFFSHORE_USER (USER_ID)^
-- end PAXBASE_OFFSHORE_USER
-- begin PAXBASE_COMPANY
create unique index IDX_PAXBASE_COMPANY_UNIQ_EMAIL on PAXBASE_COMPANY (EMAIL) ^
-- end PAXBASE_COMPANY
-- begin PAXBASE_TRANSFER
alter table PAXBASE_TRANSFER add constraint FK_PAXBASE_TRANSFER_CREW_CHANGE foreign key (CREW_CHANGE_ID) references PAXBASE_CREW_CHANGE(ID)^
alter table PAXBASE_TRANSFER add constraint FK_PAXBASE_TRANSFER_OPERATED_BY foreign key (OPERATED_BY_ID) references PAXBASE_COMPANY(ID)^
create index IDX_PAXBASE_TRANSFER_OPERATED_BY on PAXBASE_TRANSFER (OPERATED_BY_ID)^
create index IDX_PAXBASE_TRANSFER_CREW_CHANGE on PAXBASE_TRANSFER (CREW_CHANGE_ID)^
-- end PAXBASE_TRANSFER
-- begin PAXBASE_SITE
alter table PAXBASE_SITE add constraint FK_PAXBASE_SITE_PARENT_SITE foreign key (PARENT_SITE_ID) references PAXBASE_SITE(ID)^
create unique index IDX_PAXBASE_SITE_UNIQ_NAME on PAXBASE_SITE (NAME) ^
create index IDX_PAXBASE_SITE_PARENT_SITE on PAXBASE_SITE (PARENT_SITE_ID)^
-- end PAXBASE_SITE
-- begin PAXBASE_WAYPOINT
alter table PAXBASE_WAYPOINT add constraint FK_PAXBASE_WAYPOINT_PREV_WAYPOINT foreign key (PREV_WAYPOINT_ID) references PAXBASE_WAYPOINT(ID)^
alter table PAXBASE_WAYPOINT add constraint FK_PAXBASE_WAYPOINT_NEXT_WAYPOINT foreign key (NEXT_WAYPOINT_ID) references PAXBASE_WAYPOINT(ID)^
alter table PAXBASE_WAYPOINT add constraint FK_PAXBASE_WAYPOINT_TRANSFER foreign key (TRANSFER_ID) references PAXBASE_TRANSFER(ID)^
alter table PAXBASE_WAYPOINT add constraint FK_PAXBASE_WAYPOINT_SITE foreign key (SITE_ID) references PAXBASE_SITE(ID)^
create index IDX_PAXBASE_WAYPOINT_SITE on PAXBASE_WAYPOINT (SITE_ID)^
create index IDX_PAXBASE_WAYPOINT_TRANSFER on PAXBASE_WAYPOINT (TRANSFER_ID)^
-- end PAXBASE_WAYPOINT
-- begin PAXBASE_PAYLOAD
alter table PAXBASE_PAYLOAD add constraint FK_PAXBASE_PAYLOAD_CRAFT_TYPE foreign key (CRAFT_TYPE_ID) references PAXBASE_CRAFT_TYPES(ID)^
alter table PAXBASE_PAYLOAD add constraint FK_PAXBASE_PAYLOAD_SITE_A foreign key (SITE_A_ID) references PAXBASE_SITE(ID)^
alter table PAXBASE_PAYLOAD add constraint FK_PAXBASE_PAYLOAD_SITE_B foreign key (SITE_B_ID) references PAXBASE_SITE(ID)^
create index IDX_PAXBASE_PAYLOAD_SITE_A on PAXBASE_PAYLOAD (SITE_A_ID)^
create index IDX_PAXBASE_PAYLOAD_SITE_B on PAXBASE_PAYLOAD (SITE_B_ID)^
create index IDX_PAXBASE_PAYLOAD_CRAFT_TYPE on PAXBASE_PAYLOAD (CRAFT_TYPE_ID)^
-- end PAXBASE_PAYLOAD
