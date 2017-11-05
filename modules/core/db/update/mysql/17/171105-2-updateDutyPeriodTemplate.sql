-- update PAXBASE_DUTY_PERIOD_TEMPLATE set FUNCTION_CATEGORY_ID = <default_value> where FUNCTION_CATEGORY_ID is null ;
alter table PAXBASE_DUTY_PERIOD_TEMPLATE modify column FUNCTION_CATEGORY_ID varchar(32) not null ;
