-- liquibase formatted sql
-- changeset eve:1

-- Table: loan_properties
create table if not exists loan_properties
(
    id                             BIGSERIAL,
    update_time                    TIMESTAMP,
    global_rate                    INT,
    insurance_price                DECIMAL(10, 2),
    insurance_rate                 INT,
    salary_rate                    INT,
    min_client_age                 BIGINT,
    max_client_age                 BIGINT,
    min_total_work_experience      INT,
    min_current_work_experience    INT,
    salary_quantity                INT,
    min_man_age_for_special_rate   BIGINT,
    max_man_age_for_special_rate   BIGINT,
    min_woman_age_for_special_rate BIGINT,
    max_woman_age_for_special_rate BIGINT,
    monthly_payment_start_day      INT
);