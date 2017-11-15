alter table PAXBASE_SITE_ROLE_LINK add constraint FK_SITROL_ROLE foreign key (ROLE_ID) references PAXBASE_ROLE(ID);
alter table PAXBASE_SITE_ROLE_LINK add constraint FK_SITROL_SITE foreign key (SITE_ID) references PAXBASE_SITE(ID);
