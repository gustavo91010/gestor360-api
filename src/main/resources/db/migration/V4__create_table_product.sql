CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    current_cost DECIMAL(19, 4) NOT NULL DEFAULT 0
);

CREATE TABLE product_items (
    product_id BIGINT,
    item_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (item_id) REFERENCES item(id)
);