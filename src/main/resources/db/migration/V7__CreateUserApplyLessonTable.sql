CREATE TABLE user_apply_lesson(
id INT AUTO_INCREMENT NOT NULL PRIMARY KEY
,user_id INT NOT NULL
,lesson_id INT NOT NULL
,apply_datetime DATETIME NOT NULL
,created_at	timestamp	not null default current_timestamp
,UNIQUE (user_id, lesson_id)
,FOREIGN KEY (user_id) REFERENCES user(id)
,FOREIGN KEY (lesson_id) REFERENCES lesson(id)
)