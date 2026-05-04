-- ============================================================
-- V2__seed_admin.sql — Default Users
-- Password for all users: Admin@123
-- BCrypt hash of "Admin@123"
-- ============================================================

INSERT INTO employees (full_name, cpf, email, password_hash, role, active)
VALUES
  ('Administrador', '000.000.000-00', 'admin@hms.com',
   '$2a$10$N/gqM7oJy7MZ7P5LtN1z3eFxm7X/ZxEXgqQ.wKl2IXfXdXGMEMnIi', 'ADMIN', true),
  ('Dr. Carlos Silva', '111.111.111-11', 'medico@hms.com',
   '$2a$10$N/gqM7oJy7MZ7P5LtN1z3eFxm7X/ZxEXgqQ.wKl2IXfXdXGMEMnIi', 'MEDICO', true),
  ('Enf. Ana Costa', '222.222.222-22', 'enfermeiro@hms.com',
   '$2a$10$N/gqM7oJy7MZ7P5LtN1z3eFxm7X/ZxEXgqQ.wKl2IXfXdXGMEMnIi', 'ENFERMEIRO', true),
  ('Ana Recepção', '333.333.333-33', 'recepcao@hms.com',
   '$2a$10$N/gqM7oJy7MZ7P5LtN1z3eFxm7X/ZxEXgqQ.wKl2IXfXdXGMEMnIi', 'RECEPCIONISTA', true);

UPDATE employees SET specialty = 'Clínica Geral', crm = 'CRM-12345'
WHERE email = 'medico@hms.com';
