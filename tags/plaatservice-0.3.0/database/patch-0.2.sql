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

CREATE TABLE `score` (
  `sid` int(11) NOT NULL,
  `product` varchar(20) NOT NULL,
  `version` varchar(20) NOT NULL,
  `dt` datetime NOT NULL,
  `score` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `ip` varchar(20) NOT NULL,
  `user` varchar(50) NOT NULL,
  `os` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

ALTER TABLE `score` ADD PRIMARY KEY (`sid`);
ALTER TABLE `score` MODIFY `sid` int(11) NOT NULL AUTO_INCREMENT;


ALTER TABLE `score` ADD `country` VARCHAR(5) NOT NULL AFTER `os`;
ALTER TABLE `score` ADD `city` VARCHAR(50) NOT NULL AFTER `country`;





