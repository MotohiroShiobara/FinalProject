CREATE TABLE comment (
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY
,article_id INT NOT NULL
,user_id INT NOT NULL
,markdown_text TEXT NOT NULL
,created_at	timestamp	not null default current_timestamp
,updated_at	timestamp	not null default current_timestamp on update current_timestamp
,FOREIGN KEY(article_id) REFERENCES article(id)
,FOREIGN KEY(user_id) REFERENCES user(id)
)