alter table PAXBASE_TRANSFER_WAYPOINT_LINK add constraint FK_PTWL_WAYPOINT foreign key (WAYPOINT_ID) references PAXBASE_WAYPOINT(ID);
alter table PAXBASE_TRANSFER_WAYPOINT_LINK add constraint FK_PTWL_TRANSFER foreign key (TRANSFER_ID) references PAXBASE_TRANSFER(ID);
