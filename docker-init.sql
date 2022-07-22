CREATE DATABASE `test`;
CREATE USER 'test'@'%' IDENTIFIED BY 'test';
GRANT ALL PRIVILEGES ON `test`.* TO 'test'@'%';