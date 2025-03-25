INSERT INTO category (id, name, parent_id) VALUES (1, 'Electronics', NULL);
INSERT INTO category (id, name, parent_id) VALUES (2, 'It staff', 1);
INSERT INTO Brand (id, name) VALUES (3, 'Samsung');
INSERT INTO Brand (id, name) VALUES (4, 'Sony');
INSERT INTO Product (id, name,count,description,price,category_id,brand_id,discount_percentage) VALUES (5, 'TV',2,'Szép tv',200000,2,4,null);
INSERT INTO Product (id, name,count,description,price,category_id,brand_id,discount_percentage) VALUES (6, 'Microwave',3,'Jó mikró',20000,1,3,10);
INSERT INTO users (id, role, email, first_name, last_name, city, street,postal_code,number,country)
VALUES ('a3d3ef0b-6888-4a49-b329-7dea9dae614a', 'ADMIN', 'admin@example.com', 'John', 'Doe', 'New York', 'Main Street 10', '10001','12A','HU');

