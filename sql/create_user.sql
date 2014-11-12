drop user 'felipe'@'localhost';
create user 'felipe'@'localhost' identified by 'felipe';
grant all privileges on booksdb.* to 'books'@'localhost';
flush privileges;