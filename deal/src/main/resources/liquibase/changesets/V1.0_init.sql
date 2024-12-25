-- liquibase formatted sql
-- changeset eve:1

-- Table: employment
create table if not exists employments
(
    id                      bigserial,
    employer_inn            varchar(12),
    status                  varchar(30),
    salary                  DECIMAL not null,
    position                varchar(30),
    work_experience_total   INT     not null,
    work_Experience_current INT     not null,
    primary key (id)
);

-- Table: passport
create table if not exists passports
(
    id           bigserial,
    series       varchar(16) not null,
    number       varchar(16) not null,
    issue_branch varchar(255),
    issue_date   DATE,
    primary key (id),
    unique (series, number)
);

-- Table: client
CREATE TABLE IF NOT EXISTS clients
(
    id               BIGSERIAL,
    name             VARCHAR(30) NOT NULL,
    surname          VARCHAR(30) NOT NULL,
    middle_name      VARCHAR(30),
    birthday         DATE        NOT NULL,
    email            VARCHAR(56) NOT NULL,
    gender           VARCHAR(16),
    marital_status   VARCHAR(30),
    dependent_amount INT         NOT NULL,
    passport_id      BIGINT      NOT NULL,
    employment_id    BIGINT,
    account          VARCHAR(20),
    primary key (id),
    FOREIGN KEY (passport_id) REFERENCES passports (Id),
    FOREIGN KEY (employment_id) REFERENCES employments (Id)
);

-- Table: credit
CREATE TABLE IF NOT EXISTS credits
(
    id               BIGSERIAL,
    amount           DECIMAL     NOT NULL,
    term             INT         NOT NULL,
    monthly_payment  DECIMAL     NOT NULL,
    rate             DECIMAL     NOT NULL,
    psk              DECIMAL     NOT NULL,
    payment_schedule JSONB       NOT NULL,
    insurance_enable BOOLEAN     NOT NULL,
    salary_client    BOOLEAN     NOT NULL,
    credit_status    VARCHAR(30) NOT NULL,
    primary key (id)
);

-- Table: application
CREATE TABLE IF NOT EXISTS applications
(
    id             BIGSERIAL,
    client_id      BIGINT REFERENCES clients (id) NOT NULL,
    credit_id      BIGINT REFERENCES credits (id),
    status         VARCHAR(30),
    creation_date  TIMESTAMP WITHOUT TIME ZONE    NOT NULL,
    applied_offer  JSONB,
    sign_date      TIMESTAMP WITHOUT TIME ZONE,
    ses_code       INT,
    status_history JSONB,
    primary key (id),
    FOREIGN KEY (client_id) REFERENCES clients (Id),
    FOREIGN KEY (credit_id) REFERENCES credits (Id)
);