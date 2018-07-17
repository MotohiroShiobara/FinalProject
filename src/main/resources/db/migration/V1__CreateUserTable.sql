CREATE TABLE user (
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY
,account_name VARCHAR(50) NOT NULL UNIQUE
,email_address VARCHAR(255) NOT NULL UNIQUE
,password VARCHAR(255) NOT NULL
,profile VARCHAR (255)
,created_at	timestamp	not null default current_timestamp
,updated_at	timestamp	not null default current_timestamp on update current_timestamp
)