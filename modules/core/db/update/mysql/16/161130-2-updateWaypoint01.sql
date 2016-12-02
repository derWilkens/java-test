alter table PAXBASE_WAYPOINT drop column TRANSFER_DURATION cascade ;
alter table PAXBASE_WAYPOINT add column TRANSFER_DURATION time(3) ;
