-- 用户表
CREATE TABLE user (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      username VARCHAR(50) NOT NULL UNIQUE,  # 用户名
                          password VARCHAR(100) NOT NULL,  # 加密后的密码
                          nickname VARCHAR(50),  # 昵称
                          create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 文章表
CREATE TABLE article (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         title VARCHAR(100) NOT NULL,  # 标题
                             content TEXT NOT NULL,  # 内容
                             author_id INT NOT NULL,  # 关联用户表
                             view_count INT DEFAULT 0,  # 浏览量
                             create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                         update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (author_id) REFERENCES user(id)
);

-- 评论表
CREATE TABLE comment (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         article_id INT NOT NULL,  # 关联文章表
                             content VARCHAR(500) NOT NULL,  # 评论内容
                             user_id INT NOT NULL,  # 评论用户
                             create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (article_id) REFERENCES article(id),
                         FOREIGN KEY (user_id) REFERENCES user(id)
);