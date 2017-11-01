# Create Testuser

CREATE DATABASE IF NOT EXISTS `expensetracker` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `expensetracker`;
# Create Table
DROP TABLE IF EXISTS `user`;

CREATE TABLE IF NOT EXISTS `user` (
  `id` Long(11) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# Add Data
INSERT INTO `user` (`id`, `email`, `password`) VALUES (1, "test@test.com", "test");
