CREATE TABLE IF NOT EXISTS Quiz(username VARCHAR(20), quiz_id VARCHAR(10),number_of_questions BIGINT,PRIMARY KEY(quiz_id));ALTER TABLE Quiz ALTER COLUMN quiz_id SET NOT NULL;ALTER TABLE Quiz ALTER COLUMN number_of_questions SET DEFAULT 3;CREATE INDEX IF NOT EXISTS quiz_id_index on Quiz(quiz_id);CREATE INDEX IF NOT EXISTS username_index on Quiz(username);