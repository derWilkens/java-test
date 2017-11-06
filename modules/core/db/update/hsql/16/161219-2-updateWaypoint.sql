alter table PAXBASE_WAYPOINT add column ORDER_NO integer default 0 not null ;
-- update PAXBASE_WAYPOINT set TRANSFER_ID = <default_value> where TRANSFER_ID is null ;
alter table PAXBASE_WAYPOINT alter column TRANSFER_ID set not null ;
