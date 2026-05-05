-- ============================================================
-- V3__seed_test_data.sql — Test Data for HMS
-- Adds: employees (all roles), patients, appointments,
--       queue tickets and medical records for testing.
-- Password for all new employees: Admin@123
-- ============================================================

-- ============================================================
-- Additional Employees
-- ============================================================
INSERT INTO employees (full_name, cpf, email, password_hash, role, specialty, crm, active) VALUES
  ('Dr. Renata Oliveira',   '444.444.444-44', 'cardiologia@hms.com',
   '$2a$10$N/gqM7oJy7MZ7P5LtN1z3eFxm7X/ZxEXgqQ.wKl2IXfXdXGMEMnIi',
   'MEDICO', 'Cardiologia', 'CRM-54321', true),

  ('Dr. Marcos Ferreira',   '555.555.555-55', 'ortopedia@hms.com',
   '$2a$10$N/gqM7oJy7MZ7P5LtN1z3eFxm7X/ZxEXgqQ.wKl2IXfXdXGMEMnIi',
   'MEDICO', 'Ortopedia', 'CRM-99887', true),

  ('Enf. Patrícia Lima',    '666.666.666-66', 'enfermeiro2@hms.com',
   '$2a$10$N/gqM7oJy7MZ7P5LtN1z3eFxm7X/ZxEXgqQ.wKl2IXfXdXGMEMnIi',
   'ENFERMEIRO', NULL, NULL, true),

  ('João Recepção',         '777.777.777-77', 'recepcao2@hms.com',
   '$2a$10$N/gqM7oJy7MZ7P5LtN1z3eFxm7X/ZxEXgqQ.wKl2IXfXdXGMEMnIi',
   'RECEPCIONISTA', NULL, NULL, true),

  ('Farm. Lucia Santos',    '888.888.888-88', 'farmacia@hms.com',
   '$2a$10$N/gqM7oJy7MZ7P5LtN1z3eFxm7X/ZxEXgqQ.wKl2IXfXdXGMEMnIi',
   'FARMACEUTICO', NULL, NULL, true);

-- ============================================================
-- Patients
-- ============================================================
INSERT INTO patients (full_name, cpf, date_of_birth, phone, email, address, blood_type, allergies, insurance_plan) VALUES
  ('Pedro Alves Souza',        '100.200.300-10', '1985-03-15',
   '(11) 98765-4321', 'pedro.souza@email.com',
   'Rua das Flores, 123 - São Paulo, SP', 'A+',
   'Penicilina', 'Unimed'),

  ('Maria Fernanda Costa',     '200.300.400-20', '1972-07-22',
   '(11) 91234-5678', 'mariafernanda@email.com',
   'Av. Paulista, 456 - São Paulo, SP', 'O-',
   NULL, 'Bradesco Saúde'),

  ('João Carlos Lima',         '300.400.500-30', '1990-11-08',
   '(21) 99876-5432', 'joaocarlos@email.com',
   'Rua do Comércio, 789 - Rio de Janeiro, RJ', 'B+',
   'Dipirona, Ibuprofeno', 'SulAmérica'),

  ('Ana Paula Rodrigues',      '400.500.600-40', '2001-01-30',
   '(31) 97654-3210', 'anapaula@email.com',
   'Av. Afonso Pena, 321 - Belo Horizonte, MG', 'AB+',
   NULL, NULL),

  ('Roberto Nascimento',       '500.600.700-50', '1958-09-12',
   '(85) 98877-6655', 'roberto.nasc@email.com',
   'Rua Barão de Studart, 654 - Fortaleza, CE', 'O+',
   'Sulfa', 'Hapvida'),

  ('Claudia Mendes Barros',    '600.700.800-60', '1995-06-18',
   '(41) 96543-2109', 'claudia.mendes@email.com',
   'Rua XV de Novembro, 987 - Curitiba, PR', 'A-',
   NULL, 'Amil');

-- ============================================================
-- Appointments (referencing employee IDs from V2 seed)
-- V2 creates: admin=1, medico(Carlos)=2, enfermeiro(Ana)=3, recepcao=4
-- V3 adds:    cardiologia(Renata)=5, ortopedia(Marcos)=6, ...
-- ============================================================
INSERT INTO appointments (patient_id, doctor_id, scheduled_at, status, specialty, notes) VALUES
  -- Past appointments (COMPLETED)
  (1, 2, NOW() - INTERVAL '5 days', 'COMPLETED', 'Clínica Geral',
   'Paciente queixou-se de dor de cabeça frequente e fadiga. Solicitados exames laboratoriais.'),

  (2, 5, NOW() - INTERVAL '3 days', 'COMPLETED', 'Cardiologia',
   'Avaliação de rotina. Paciente com pressão arterial levemente elevada. Iniciado tratamento.'),

  (3, 6, NOW() - INTERVAL '10 days', 'COMPLETED', 'Ortopedia',
   'Queixa de dor no joelho direito após queda. Raio-X sem fraturas. Fisioterapia recomendada.'),

  -- Today appointments (SCHEDULED)
  (4, 2, NOW() + INTERVAL '1 hour', 'SCHEDULED', 'Clínica Geral',
   'Primeira consulta. Paciente encaminhado pelo pronto-socorro.'),

  (5, 5, NOW() + INTERVAL '2 hours', 'SCHEDULED', 'Cardiologia',
   'Retorno para análise dos exames cardiológicos.'),

  (6, 6, NOW() + INTERVAL '3 hours', 'SCHEDULED', 'Ortopedia',
   'Acompanhamento pós-cirúrgico do joelho.'),

  -- Future appointments
  (1, 5, NOW() + INTERVAL '7 days', 'SCHEDULED', 'Cardiologia',
   'Avaliação preventiva solicitada pelo médico de clínica geral.'),

  (2, 2, NOW() + INTERVAL '14 days', 'SCHEDULED', 'Clínica Geral',
   'Retorno para análise dos exames de sangue.');

-- ============================================================
-- Queue Tickets
-- ============================================================
INSERT INTO queue_tickets (patient_id, arrived_at, urgency_level, status, counter) VALUES
  -- Currently waiting
  (1, NOW() - INTERVAL '30 minutes', 'YELLOW',  'WAITING',    NULL),
  (3, NOW() - INTERVAL '20 minutes', 'GREEN',   'WAITING',    NULL),
  (6, NOW() - INTERVAL '10 minutes', 'RED',     'WAITING',    NULL),

  -- Being attended
  (2, NOW() - INTERVAL '45 minutes', 'ORANGE',  'ATTENDING',  'C1'),

  -- Already called
  (4, NOW() - INTERVAL '15 minutes', 'BLUE',    'CALLED',     'C2'),

  -- Historical (from earlier today)
  (5, NOW() - INTERVAL '3 hours',   'GREEN',   'ATTENDED',   'C1');

-- ============================================================
-- Medical Records (for completed appointments)
-- ============================================================
INSERT INTO medical_records (patient_id, doctor_id, appointment_id, diagnosis, prescription, notes) VALUES
  (1, 2, 1,
   'Cefaleia tensional crônica. Suspeita de anemia ferropriva.',
   'Paracetamol 750mg - 1 comprimido de 8 em 8 horas por 5 dias.\nSulfato Ferroso 40mg - 1 comprimido ao dia por 60 dias.',
   'Solicitado hemograma completo, ferritina e vitamina B12. Retorno em 30 dias.'),

  (2, 5, 2,
   'Hipertensão arterial sistêmica estágio 1.',
   'Losartana 50mg - 1 comprimido ao dia, em jejum.\nHidroclorotiazida 25mg - 1 comprimido ao dia pela manhã.',
   'Orientado sobre dieta hipossódica e prática regular de exercícios. Monitoramento da PA em casa. Retorno em 15 dias.'),

  (3, 6, 3,
   'Gonalgia direita por trauma contuso. Sem lesão óssea ao RX.',
   'Ibuprofeno 600mg - 1 comprimido de 8 em 8 horas por 7 dias (com alimento).\nGel de Diclofenaco - aplicar na região 3x ao dia.',
   'Encaminhado para fisioterapia 2x por semana. Orientado repouso relativo e compressas de gelo. Retorno em 21 dias.');
