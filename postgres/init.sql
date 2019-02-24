-- Create app users
CREATE USER note WITH ENCRYPTED PASSWORD 'note';
CREATE USER "user" WITH ENCRYPTED PASSWORD 'user';

-- Create app databases
CREATE DATABASE note WITH OWNER note;
CREATE DATABASE "user" WITH OWNER "user";
