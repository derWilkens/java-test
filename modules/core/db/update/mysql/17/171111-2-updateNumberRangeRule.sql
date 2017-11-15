alter table PAXBASE_NUMBER_RANGE_RULE add column SITE_ROLE_RULE_ID varchar(32) ;
alter table PAXBASE_NUMBER_RANGE_RULE drop column SITE_ROLE_RULES_ID cascade ;
