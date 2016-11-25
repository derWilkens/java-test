alter table PAXBASE_CRAFT_TYPES add column NAME varchar(50) ;
update PAXBASE_CRAFT_TYPES set NAME = '' where NAME is null ;
alter table PAXBASE_CRAFT_TYPES modify column NAME varchar(50) not null ;
alter table PAXBASE_CRAFT_TYPES add column SEATS integer ;
update PAXBASE_CRAFT_TYPES set SEATS = 0 where SEATS is null ;
alter table PAXBASE_CRAFT_TYPES modify column SEATS integer not null ;
alter table PAXBASE_CRAFT_TYPES add column CLIENT integer ;
update PAXBASE_CRAFT_TYPES set CLIENT = 0 where CLIENT is null ;
alter table PAXBASE_CRAFT_TYPES modify column CLIENT integer not null ;
