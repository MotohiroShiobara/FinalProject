CREATE TABLE article (
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY
,title VARCHAR(255) NOT NULL
,user_id INT NOT NULL
,released_at	DATETIME	NOT NULL
,markdown_text TEXT
,created_at	timestamp	not null default current_timestamp
,updated_at	timestamp	not null default current_timestamp on update current_timestamp
,FOREIGN KEY(user_id) REFERENCES user(id)
)