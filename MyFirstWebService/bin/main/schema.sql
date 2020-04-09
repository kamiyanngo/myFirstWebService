CREATE TABLE work
(
	id INT NOT NULL AUTO_INCREMENT,
	work_name VARCHAR(100) NOT NULL,
	number VARCHAR(30) NOT NULL,
	money VARCHAR(100) NOT NULL,
	gd_name VARCHAR(20),
	sg_name VARCHAR(20),
	contents VARCHAR(255) NOT NULL,
	created DATETIME NOT NULL,
	filename VARCHAR(255),
	filename2 VARCHAR(255),
	PRIMARY KEY(id)
);

CREATE TABLE user(
    user_id INT AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY(user_id)
);

