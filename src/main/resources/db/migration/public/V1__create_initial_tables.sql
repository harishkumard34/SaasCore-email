
CREATE TABLE public.tenants (
    tenant_id SERIAL PRIMARY KEY,
    org_name VARCHAR(255),
    schema_name VARCHAR(255) NOT NULL UNIQUE,
    sequence_start BIGINT NOT NULL,
    sequence_end BIGINT NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_time BIGINT
);

CREATE TABLE IF NOT EXISTS public.AUTH (
    AUTH_ID SERIAL PRIMARY KEY,
    AUTH_NAME VARCHAR(150) ,
    EMAIL VARCHAR(200) UNIQUE,
    PHONE VARCHAR(20) UNIQUE,
    PASSWORD_PHRASE VARCHAR(2000),
    tenant_id INT REFERENCES tenants(tenant_id),
    user_id BIGINT,
    LAST_LOGINTIME BIGINT,
    CREATED_TIME BIGINT,
    SESSION_ID VARCHAR(200),
    MODIFIED_TIME BIGINT,
    PREVIOUS_PASSWD VARCHAR(2000),
    PROPERTIES VARCHAR(1000),
    LOGIN_IP VARCHAR(50),
    LOGIN_AGENT VARCHAR(100),
    FAILED_COUNT INTEGER DEFAULT 0,
    RESET_REQUEST BOOLEAN,
    OTP VARCHAR(6)
);

CREATE TABLE IF NOT EXISTS public.tenant_sequence_tracker (
    id SERIAL PRIMARY KEY,
    last_sequence BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.scheduler (
    task_id INTEGER PRIMARY KEY,
    task_name VARCHAR(20), 
    class_name VARCHAR(100),
    status_name VARCHAR(20) ,
    schedule_time TIMESTAMP,
    created_time BIGINT,
    created_by BIGINT,
    remarks VARCHAR(20),
    loop_type VARCHAR(20), 
    cron_expression VARCHAR(20),
    executed_time BIGINT
);

