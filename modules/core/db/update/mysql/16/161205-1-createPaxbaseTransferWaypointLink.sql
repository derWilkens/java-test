create table PAXBASE_TRANSFER_WAYPOINT_LINK (
    WAYPOINT_ID varchar(32),
    TRANSFER_ID varchar(32),
    primary key (WAYPOINT_ID, TRANSFER_ID)
);
