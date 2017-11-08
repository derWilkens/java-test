alter table PAXBASE_WAYPOINT add column ORDER_NO integer ;
alter table PAXBASE_WAYPOINT drop column PREV_WAYPOINT_ID cascade ;
alter table PAXBASE_WAYPOINT drop column NEXT_WAYPOINT_ID cascade ;
