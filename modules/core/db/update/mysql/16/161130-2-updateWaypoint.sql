alter table PAXBASE_WAYPOINT drop column TAKE_OFF cascade ;
alter table PAXBASE_WAYPOINT add column TAKE_OFF time(3) ;
update PAXBASE_WAYPOINT set TAKE_OFF = current_time where TAKE_OFF is null ;
alter table PAXBASE_WAYPOINT modify column TAKE_OFF time(3) not null ;
