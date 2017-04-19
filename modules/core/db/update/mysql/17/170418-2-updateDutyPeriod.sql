alter table PAXBASE_DUTY_PERIOD add column FUNCTION_CATEGORY_ID varchar(32) ;
alter table PAXBASE_DUTY_PERIOD drop column OUTBOUND_TRANSFER_ID cascade ;
alter table PAXBASE_DUTY_PERIOD drop column INBOUND_TRANSFER_ID cascade ;
alter table PAXBASE_DUTY_PERIOD drop column CATEGORY_ID cascade ;
