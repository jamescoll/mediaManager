CREATE TABLE `files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Filetype` int(11) NOT NULL,
  `Filepath` varchar(400) NOT NULL,
  `Filename` varchar(300) NOT NULL,
  `Fileextension` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28684 DEFAULT CHARSET=utf8