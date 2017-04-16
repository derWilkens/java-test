alter table PAXBASE_DUTY_PERIOD add column START_ datetime(3) ;
alter table PAXBASE_DUTY_PERIOD add column END_ datetime(3) ;
alter table PAXBASE_DUTY_PERIOD add column TYPE_ID varchar(32) ;
alter table PAXBASE_DUTY_PERIOD drop column CLIENT cascade ;
alter table PAXBASE_DUTY_PERIOD drop column BEGIN_DATE cascade ;
alter table PAXBASE_DUTY_PERIOD drop column END_DATE cascade ;
