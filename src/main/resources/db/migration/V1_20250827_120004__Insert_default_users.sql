-- Insert default users with BCrypt encoded passwords
-- Password for all users is "53cret" (BCrypt encoded with strength 10)

INSERT INTO USER (USERNAME, PASSWORD) VALUES
('yuji', '$2a$10$DQzFHMPXzSzCJ3GczB1kRuBKnO7wlNUpNnRnP7vxvHnBv0pNYNmRC'),
('geto', '$2a$10$DQzFHMPXzSzCJ3GczB1kRuBKnO7wlNUpNnRnP7vxvHnBv0pNYNmRC'),
('gojo', '$2a$10$DQzFHMPXzSzCJ3GczB1kRuBKnO7wlNUpNnRnP7vxvHnBv0pNYNmRC'),
('senku', '$2a$10$DQzFHMPXzSzCJ3GczB1kRuBKnO7wlNUpNnRnP7vxvHnBv0pNYNmRC'),
('yaiba', '$2a$10$DQzFHMPXzSzCJ3GczB1kRuBKnO7wlNUpNnRnP7vxvHnBv0pNYNmRC'),
('tanjiro', '$2a$10$DQzFHMPXzSzCJ3GczB1kRuBKnO7wlNUpNnRnP7vxvHnBv0pNYNmRC');