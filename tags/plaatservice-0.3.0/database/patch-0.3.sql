--
--  ============
--  PlaatService
--  ============
--
--  Created by wplaat
--
--  For more information visit the following website.
--  Website : www.plaatsoft.nl 
--
--  Or send an email to the following address.
--  Email   : info@plaatsoft.nl
--
--  All copyrights reserved (c) 2008-2016 PlaatSoft
--

CREATE TABLE `user` (
  `uid` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `nickname` varchar(10) NOT NULL,
  `ip` varchar(20) NOT NULL,
  `country` varchar(5) NOT NULL,
  `city` varchar(50) NOT NULL,
   `create_date` datetime NOT NULL,
	`last_update` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

ALTER TABLE `user`  ADD PRIMARY KEY (`uid`);
ALTER TABLE `user`  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT;

CREATE TABLE `product` (
  `pid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `version` varchar(10) NOT NULL,
  `os` varchar(50) NOT NULL,
  `create_date` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

ALTER TABLE `product` ADD PRIMARY KEY (`pid`);
ALTER TABLE `product` MODIFY `pid` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE score DROP city;
ALTER TABLE score DROP user;
ALTER TABLE score DROP ip;
ALTER TABLE score DROP country;
ALTER TABLE score DROP os;
ALTER TABLE score DROP product;
ALTER TABLE score DROP version;
ALTER TABLE score ADD pid INT NOT NULL AFTER sid;
ALTER TABLE score ADD uid INT NOT NULL AFTER pid;

ALTER TABLE registration CHANGE last_date last_update DATETIME NOT NULL;

