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

CREATE TABLE `registration` (
  `rid` int(11) NOT NULL,
  `product` VARCHAR(20) NOT NULL,
  `version` VARCHAR(5) NOT NULL,
  `ip`  VARCHAR(20) NOT NULL,
  `count` int NOT NULL,
  `create_date` datetime NOT NULL,
  `last_date` datetime NOT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

ALTER TABLE `registration` MODIFY `rid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;





