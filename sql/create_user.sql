create database atm_app;
use atm_app;

#show tables;

create user 'atm_app_user'@'%' identified by '1234';
grant all privileges on atm_app.* to 'atm_app_user'@'%';
flush privileges;