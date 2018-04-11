-- Run this script on System database to create user and empty database

CREATE USER uixy_user WITH PASSWORD 'DoIt4Tweety';

CREATE DATABASE uixy_db
  WITH ENCODING='UTF8'
       OWNER=uixy_user
       CONNECTION LIMIT=-1;
