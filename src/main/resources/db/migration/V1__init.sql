-- ============================================================
-- V1__init.sql — HMS Database Schema
-- ============================================================

CREATE TABLE IF NOT EXISTS employees (
    id            BIGSERIAL PRIMARY KEY,
    full_name     VARCHAR(200) NOT NULL,
    cpf           VARCHAR(14)  NOT NULL UNIQUE,
    email         VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(30)  NOT NULL,
    specialty     VARCHAR(100),
    crm           VARCHAR(20),
    active        BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS patients (
    id             BIGSERIAL PRIMARY KEY,
    full_name      VARCHAR(200) NOT NULL,
    cpf            VARCHAR(14)  NOT NULL UNIQUE,
    date_of_birth  DATE         NOT NULL,
    phone          VARCHAR(20),
    email          VARCHAR(150),
    address        VARCHAR(300),
    blood_type     VARCHAR(5),
    allergies      TEXT,
    insurance_plan VARCHAR(100),
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS appointments (
    id           BIGSERIAL PRIMARY KEY,
    patient_id   BIGINT       NOT NULL REFERENCES patients(id),
    doctor_id    BIGINT       NOT NULL REFERENCES employees(id),
    scheduled_at TIMESTAMP    NOT NULL,
    status       VARCHAR(20)  NOT NULL DEFAULT 'SCHEDULED',
    specialty    VARCHAR(100),
    notes        TEXT,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS queue_tickets (
    id             BIGSERIAL PRIMARY KEY,
    patient_id     BIGINT      NOT NULL REFERENCES patients(id),
    arrived_at     TIMESTAMP   NOT NULL DEFAULT NOW(),
    called_at      TIMESTAMP,
    attended_at    TIMESTAMP,
    urgency_level  VARCHAR(20) NOT NULL DEFAULT 'GREEN',
    status         VARCHAR(20) NOT NULL DEFAULT 'WAITING',
    counter        VARCHAR(10),
    created_at     TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS medical_records (
    id             BIGSERIAL PRIMARY KEY,
    patient_id     BIGINT    NOT NULL REFERENCES patients(id),
    doctor_id      BIGINT    NOT NULL REFERENCES employees(id),
    appointment_id BIGINT    REFERENCES appointments(id),
    diagnosis      TEXT,
    prescription   TEXT,
    notes          TEXT,
    created_at     TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Indexes
CREATE INDEX idx_patients_cpf          ON patients(cpf);
CREATE INDEX idx_patients_name         ON patients(full_name);
CREATE INDEX idx_appointments_date     ON appointments(scheduled_at);
CREATE INDEX idx_appointments_doctor   ON appointments(doctor_id);
CREATE INDEX idx_queue_status          ON queue_tickets(status);
CREATE INDEX idx_queue_urgency         ON queue_tickets(urgency_level);
CREATE INDEX idx_medical_rec_patient   ON medical_records(patient_id);
