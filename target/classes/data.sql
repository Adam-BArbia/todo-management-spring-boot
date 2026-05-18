-- Insert a sample todo for the 'admin' user if that user exists
INSERT INTO todos (user_id, description, target_date)
SELECT id, 'Learn Spring Boot', '2026-05-17' FROM users WHERE username = 'admin_';
