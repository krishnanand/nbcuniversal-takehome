INSERT INTO Questions(question_id, question_text, answer) VALUES (1, 'Is 2016 a leap year?', TRUE);INSERT INTO Questions(question_id, question_text, answer) VALUES(2, 'Is Sunday a weekday?', FALSE);INSERT INTO Questions(question_id, question_text, answer) VALUES(3, 'Is August the 8th month?', TRUE);INSERT INTO Questions(question_id, question_text, answer) VALUES(4, 'Is July 25 independence day of United States of America?', FALSE);INSERT INTO Questions(question_id, question_text, answer) VALUES(5, 'Does January have 31 days in a month?', TRUE);INSERT INTO Questions(question_id, question_text, answer) VALUES(6, 'Does UK have a monarchy?', TRUE);INSERT INTO Questions(question_id, question_text, answer) VALUES(7, 'Does Lebron play football?', FALSE);INSERT INTO Questions(question_id, question_text, answer) VALUES(8, 'Is Los Angeles in New York state?', FALSE);INSERT INTO Questions(question_id, question_text, answer) VALUES(9, 'Is Canada to the north of United States?', TRUE);INSERT INTO Questions(question_id, question_text, answer) VALUES(10, 'Does November have 30 days in a month?', TRUE);INSERT INTO Quiz(username, quiz_id) VALUES('test', 'ABCDE12345');INSERT INTO QuizQuestions(quiz_id, question_id) VALUES('ABCDE12345', 5);INSERT INTO QuizStatus(quiz_id, questions_asked) VALUES('ABCDE12345', 1);