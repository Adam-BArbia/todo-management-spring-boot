-- Insert a sample todo for the 'admin' user if that user exists
INSERT INTO todos (user_id, description, target_date)
SELECT id, 'Learn Spring Boot', '2026-05-17' FROM users WHERE username = 'admin_';

-- Insert additional sample todos with priority and status for testing
INSERT INTO todos (user_id, description, target_date, priority, status)
SELECT id, 'Complete project documentation', '2026-05-20', 'HIGH', 'IN_PROGRESS' FROM users WHERE username = 'admin_';

INSERT INTO todos (user_id, description, target_date, priority, status)
SELECT id, 'Code review for team members', '2026-05-21', 'URGENT', 'TODO' FROM users WHERE username = 'admin_';

INSERT INTO todos (user_id, description, target_date, priority, status)
SELECT id, 'Update dependencies', '2026-05-25', 'MEDIUM', 'TODO' FROM users WHERE username = 'admin_';

INSERT INTO todos (user_id, description, target_date, priority, status)
SELECT id, 'Fix bug in authentication module', '2026-05-19', 'URGENT', 'IN_PROGRESS' FROM users WHERE username = 'admin_';

