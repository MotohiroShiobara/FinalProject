CREATE TABLE user (
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY
,account_name VARCHAR(50) NOT NULL UNIQUE
,email_address VARCHAR(191) NOT NULL UNIQUE
,password VARCHAR(191) NOT NULL
,profile VARCHAR (191)
,created_at	timestamp	not null default current_timestamp
,updated_at	timestamp	not null default current_timestamp on update current_timestamp
) character set utf8mb4;