CREATE TABLE purchase_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    quantity DOUBLE NOT NULL,
    unit_price DECIMAL(19, 4) NOT NULL DEFAULT 0,
    total_price DECIMAL(19, 4) NOT NULL DEFAULT 0,
    purchase_id BIGINT,
    FOREIGN KEY (purchase_id) REFERENCES purchase(id)
);