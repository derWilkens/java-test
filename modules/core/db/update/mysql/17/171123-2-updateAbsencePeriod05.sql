alter table PAXBASE_ABSENCE_PERIOD add constraint FK_PAXBASE_ABSENCE_PERIOD_FUNCTION_CATEGORY foreign key (FUNCTION_CATEGORY_ID) references PAXBASE_FUNCTION_CATEGORY(ID);
