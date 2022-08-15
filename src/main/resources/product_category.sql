DROP TABLE IF EXISTS product_category;

CREATE TABLE product_category (
    product_id INT NOT NULL,
    category_id VARCHAR(6) NOT NULL,
    PRIMARY KEY (product_id, category_id)
);

SELECT * FROM product_category;