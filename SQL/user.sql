CREATE TABLE user (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL DEFAULT '',
    `password` VARCHAR(32) NOT NULL DEFAULT '',
    salt CHAR(32) NOT NULL DEFAULT '',
    first_name VARCHAR(30) NOT NULL DEFAULT '',
    last_name VARCHAR(30) NOT NULL DEFAULT '',
    email VARCHAR(50) NOT NULL DEFAULT '',
    phone_number VARCHAR(20) NOT NULL DEFAULT '',
    image_path VARCHAR(50) NOT NULL DEFAULT '',
    address VARCHAR(100) NOT NULL DEFAULT '',
    postcode VARCHAR(10) NOT NULL DEFAULT '',
    role_id INT UNSIGNED NOT NULL DEFAULT 0,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table role(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(30) NOT NULL DEFAULT ''
)