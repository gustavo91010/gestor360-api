-- Adiciona a coluna user_id na tabela purchase
ALTER TABLE purchase
ADD COLUMN user_id BIGINT;

-- Cria a chave estrangeira entre purchase e users
ALTER TABLE purchase
ADD FOREIGN KEY (user_id) REFERENCES users(id);