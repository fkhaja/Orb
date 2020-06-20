CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(129) UNIQUE NOT NULL,
    image_url VARCHAR(2048),
    password VARCHAR(128),
    provider VARCHAR(255) NOT NULL,
    provider_id VARCHAR(255),
    username VARCHAR(16) NOT NULL
);

CREATE TABLE task_cards (
    id BIGSERIAL PRIMARY KEY,
    creation_date DATE NOT NULL,
    description VARCHAR(100),
    done BOOLEAN DEFAULT FALSE NOT NULL,
    image_url VARCHAR(2048),
    title VARCHAR(56) NOT NULL,
    term TIMESTAMP,
    user_id BIGINT NOT NULL
);

CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    completed BOOLEAN DEFAULT FALSE NOT NULL,
    value VARCHAR(255) NOT NULL,
    task_card_id BIGINT NOT NULL
);

ALTER TABLE IF EXISTS task_cards
    ADD CONSTRAINT task_cards_users_fkey
    FOREIGN KEY (user_id) REFERENCES users;

ALTER TABLE IF EXISTS tasks
    ADD CONSTRAINT tasks_task_cards_fkey
    FOREIGN KEY (task_card_id) REFERENCES task_cards;