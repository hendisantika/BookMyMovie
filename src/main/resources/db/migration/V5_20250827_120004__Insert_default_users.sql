-- Insert default users with BCrypt encoded passwords
-- Password for all users is "53cret" (BCrypt encoded with strength 10)

INSERT INTO USER (USERNAME, PASSWORD) VALUES ('yuji', '$2a$12$Cs78t9qlBzawm6TNJQ8bFuK8K9KOU4cA2uA6xDGvTnltVMQo4yUke'),
                                             ('geto', '$2a$12$Cs78t9qlBzawm6TNJQ8bFuK8K9KOU4cA2uA6xDGvTnltVMQo4yUke'),
                                             ('gojo', '$2a$12$Cs78t9qlBzawm6TNJQ8bFuK8K9KOU4cA2uA6xDGvTnltVMQo4yUke'),
                                             ('senku', '$2a$12$Cs78t9qlBzawm6TNJQ8bFuK8K9KOU4cA2uA6xDGvTnltVMQo4yUke'),
                                             ('yaiba', '$2a$12$Cs78t9qlBzawm6TNJQ8bFuK8K9KOU4cA2uA6xDGvTnltVMQo4yUke'),
                                             ('tanjiro',
                                              '$2a$12$Cs78t9qlBzawm6TNJQ8bFuK8K9KOU4cA2uA6xDGvTnltVMQo4yUke');