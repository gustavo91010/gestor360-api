ALTER TABLE product
ADD COLUMN user_id BIGINT;

ALTER TABLE product
ADD FOREIGN KEY (user_id) REFERENCES users(id);

CREATE TABLE user_product (
    product_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);