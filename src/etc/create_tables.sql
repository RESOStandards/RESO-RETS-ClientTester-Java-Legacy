use RETS;
CREATE TABLE user (
  id varchar(255) NOT NULL default '',
  company varchar(255) default NULL,
  email varchar(255) default NULL,
  naming varchar(255) default NULL,
  name varchar(255) default NULL,
  phone varchar(255) default NULL,
  product varchar(255) default NULL,
  version varchar(255) default NULL,
  password varchar(255) default NULL,
  userAgent varchar(255) default NULL,
  PRIMARY KEY  (id)
) ; 

CREATE TABLE test (
  id varchar(255) NOT NULL default '',
  testType varchar(255) NOT NULL default '',
  description varchar(255) default NULL,
  passed tinyint(1) default NULL,
  request varchar(255) default NULL,
  response varchar(255) default NULL,
  session_id varchar(255) default NULL,
  list_index int(11) default NULL,
status varchar(20) default 'NA',
  PRIMARY KEY  (id),
  KEY FK364492630DDF64 (session_id)
) ; 

CREATE TABLE client_session (
  id varchar(255) NOT NULL default '',
  lastRun int(11) NOT NULL default '0',
  username varchar(255) NOT NULL default '',
  PRIMARY KEY  (id)
) ; 

