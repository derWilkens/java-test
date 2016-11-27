alter table PAXBASE_PAYLOAD drop column CRAFT_TYPE_ID cascade ;
-- alter table PAXBASE_PAYLOAD add column CRAFT_TYPE_ID varchar(32) ^
-- update PAXBASE_PAYLOAD set CRAFT_TYPE_ID = <default_value> ;
-- alter table PAXBASE_PAYLOAD modify column CRAFT_TYPE_ID varchar(32) not null ;
alter table PAXBASE_PAYLOAD add column CRAFT_TYPE_ID varchar(32) not null ;
