CREATE TABLE user_like_article (
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY
,user_id INT NOT NULL
,article_id INT NOT NULL
,FOREIGN KEY(user_id) REFERENCES user (id)
,FOREIGN KEY(article_id) REFERENCES article (id)
,UNIQUE (user_id, article_id)
)