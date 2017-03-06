-- begin PAXBASE_COMPANY
create unique index IDX_PAXBASE_COMPANY_UNIQ_EMAIL on PAXBASE_COMPANY (EMAIL) ^
-- end PAXBASE_COMPANY
-- begin PAXBASE_TRANSFER
alter table PAXBASE_TRANSFER add constraint FK_PAXBASE_TRANSFER_CREW_CHANGE foreign key (CREW_CHANGE_ID) references PAXBASE_CREW_CHANGE(ID)^
alter table PAXBASE_TRANSFER add constraint FK_PAXBASE_TRANSFER_OPERATED_BY foreign key (OPERATED_BY_ID) references PAXBASE_COMPANY(ID)^
alter table PAXBASE_TRANSFER add constraint FK_PAXBASE_TRANSFER_MODE_OF_TRANSFER foreign key (MODE_OF_TRANSFER_ID) references PAXBASE_MODE_OF_TRANSFER(ID)^
create index IDX_PAXBASE_TRANSFER_OPERATED_BY on PAXBASE_TRANSFER (OPERATED_BY_ID)^
create index IDX_PAXBASE_TRANSFER_CREW_CHANGE on PAXBASE_TRANSFER (CREW_CHANGE_ID)^
create index IDX_PAXBASE_TRANSFER_MODE_OF_TRANSFER on PAXBASE_TRANSFER (MODE_OF_TRANSFER_ID)^
-- end PAXBASE_TRANSFER
-- begin PAXBASE_SITE
alter table PAXBASE_SITE add constraint FK_PAXBASE_SITE_PARENT_SITE foreign key (PARENT_SITE_ID) references PAXBASE_SITE(ID)^
alter table PAXBASE_SITE add constraint FK_PAXBASE_SITE_SITE_TYPE foreign key (SITE_TYPE_ID) references PAXBASE_SITE_TYPE(ID)^
create unique index IDX_PAXBASE_SITE_UNIQ_NAME on PAXBASE_SITE (NAME) ^
create index IDX_PAXBASE_SITE_PARENT_SITE on PAXBASE_SITE (PARENT_SITE_ID)^
create index IDX_PAXBASE_SITE_SITE_TYPE on PAXBASE_SITE (SITE_TYPE_ID)^
-- end PAXBASE_SITE
-- begin PAXBASE_WAYPOINT
alter table PAXBASE_WAYPOINT add constraint FK_PAXBASE_WAYPOINT_TRANSFER foreign key (TRANSFER_ID) references PAXBASE_TRANSFER(ID)^
alter table PAXBASE_WAYPOINT add constraint FK_PAXBASE_WAYPOINT_SITE foreign key (SITE_ID) references PAXBASE_SITE(ID)^
create index IDX_PAXBASE_WAYPOINT_SITE on PAXBASE_WAYPOINT (SITE_ID)^
create index IDX_PAXBASE_WAYPOINT_TRANSFER on PAXBASE_WAYPOINT (TRANSFER_ID)^
-- end PAXBASE_WAYPOINT
-- begin PAXBASE_PAYLOAD
alter table PAXBASE_PAYLOAD add constraint FK_PAXBASE_PAYLOAD_CRAFT_TYPE foreign key (CRAFT_TYPE_ID) references PAXBASE_CRAFT_TYPE(ID)^
alter table PAXBASE_PAYLOAD add constraint FK_PAXBASE_PAYLOAD_SITE_A foreign key (SITE_A_ID) references PAXBASE_SITE(ID)^
alter table PAXBASE_PAYLOAD add constraint FK_PAXBASE_PAYLOAD_SITE_B foreign key (SITE_B_ID) references PAXBASE_SITE(ID)^
create index IDX_PAXBASE_PAYLOAD_SITE_A on PAXBASE_PAYLOAD (SITE_A_ID)^
create index IDX_PAXBASE_PAYLOAD_SITE_B on PAXBASE_PAYLOAD (SITE_B_ID)^
create index IDX_PAXBASE_PAYLOAD_CRAFT_TYPE on PAXBASE_PAYLOAD (CRAFT_TYPE_ID)^
-- end PAXBASE_PAYLOAD
-- begin PAXBASE_DUTY_PERIOD
alter table PAXBASE_DUTY_PERIOD add constraint FK_PAXBASE_DUTY_PERIOD_USER foreign key (USER_ID) references SEC_USER(ID)^
alter table PAXBASE_DUTY_PERIOD add constraint FK_PAXBASE_DUTY_PERIOD_SITE foreign key (SITE_ID) references PAXBASE_SITE(ID)^
alter table PAXBASE_DUTY_PERIOD add constraint FK_PAXBASE_DUTY_PERIOD_OUTBOUND_TRANSFER foreign key (OUTBOUND_TRANSFER_ID) references PAXBASE_TRANSFER(ID)^
alter table PAXBASE_DUTY_PERIOD add constraint FK_PAXBASE_DUTY_PERIOD_INBOUND_TRANSFER foreign key (INBOUND_TRANSFER_ID) references PAXBASE_TRANSFER(ID)^
alter table PAXBASE_DUTY_PERIOD add constraint FK_PAXBASE_DUTY_PERIOD_CONTRACTOR foreign key (CONTRACTOR_ID) references PAXBASE_COMPANY(ID)^
create index IDX_PAXBASE_DUTY_PERIOD_OUTBOUND_TRANSFER on PAXBASE_DUTY_PERIOD (OUTBOUND_TRANSFER_ID)^
create index IDX_PAXBASE_DUTY_PERIOD_CONTRACTOR on PAXBASE_DUTY_PERIOD (CONTRACTOR_ID)^
create index IDX_PAXBASE_DUTY_PERIOD_SITE on PAXBASE_DUTY_PERIOD (SITE_ID)^
create index IDX_PAXBASE_DUTY_PERIOD_USER on PAXBASE_DUTY_PERIOD (USER_ID)^
create index IDX_PAXBASE_DUTY_PERIOD_INBOUND_TRANSFER on PAXBASE_DUTY_PERIOD (INBOUND_TRANSFER_ID)^
-- end PAXBASE_DUTY_PERIOD
-- begin SEC_USER
alter table SEC_USER add constraint FK_SEC_USER_COMPANY foreign key (COMPANY_ID) references PAXBASE_COMPANY(ID)^
create index IDX_SEC_USER_COMPANY on SEC_USER (COMPANY_ID)^
-- end SEC_USER
