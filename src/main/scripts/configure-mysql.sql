# connect to mysql and run as root user
#Create Databases
CREATE DATABASE blog_dev;
CREATE DATABASE blog_prod;

#Create database service accounts
CREATE USER 'blog_dev_user'@'localhost' IDENTIFIED BY 'intec';
CREATE USER 'blog_prod_user'@'localhost' IDENTIFIED BY 'intec';
CREATE USER 'blog_dev_user'@'%' IDENTIFIED BY 'intec';
CREATE USER 'blog_prod_user'@'%' IDENTIFIED BY 'intec';

#Database grants
GRANT SELECT ON blog_dev.* to 'blog_dev_user'@'localhost';
GRANT INSERT ON blog_dev.* to 'blog_dev_user'@'localhost';
GRANT DELETE ON blog_dev.* to 'blog_dev_user'@'localhost';
GRANT UPDATE ON blog_dev.* to 'blog_dev_user'@'localhost';
GRANT SELECT ON blog_prod.* to 'blog_prod_user'@'localhost';
GRANT INSERT ON blog_prod.* to 'blog_prod_user'@'localhost';
GRANT DELETE ON blog_prod.* to 'blog_prod_user'@'localhost';
GRANT UPDATE ON blog_prod.* to 'blog_prod_user'@'localhost';
GRANT SELECT ON blog_dev.* to 'blog_dev_user'@'%';
GRANT INSERT ON blog_dev.* to 'blog_dev_user'@'%';
GRANT DELETE ON blog_dev.* to 'blog_dev_user'@'%';
GRANT UPDATE ON blog_dev.* to 'blog_dev_user'@'%';
GRANT SELECT ON blog_prod.* to 'blog_prod_user'@'%';
GRANT INSERT ON blog_prod.* to 'blog_prod_user'@'%';
GRANT DELETE ON blog_prod.* to 'blog_prod_user'@'%';
GRANT UPDATE ON blog_prod.* to 'blog_prod_user'@'%';