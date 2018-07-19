CREATE TABLE lesson (
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY
,title VARCHAR(255) NOT NULL
,image_url VARCHAR(255)
,event_datetime DATETIME NOT NULL
,price INT default 0
,description TEXT NOT NULL
,email_address VARCHAR(255) NOT NULL
,owner_id INT NOT NULL
,is_open BOOLEAN default true
,created_at	timestamp	not null default current_timestamp
,updated_at	timestamp	not null default current_timestamp on update current_timestamp
,FOREIGN KEY(owner_id) REFERENCES user(id)
)