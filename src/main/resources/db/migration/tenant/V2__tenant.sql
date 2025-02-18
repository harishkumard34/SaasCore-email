-- 1. Create roles table
CREATE TABLE IF NOT EXISTS roles (
    role_id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    role_name VARCHAR(50) NOT NULL,
    reporting_to_id BIGINT,
    sharewithpeers BOOLEAN NOT NULL DEFAULT FALSE,
    description VARCHAR(255),
    level INT NOT NULL DEFAULT 0,
    created_by BIGINT,
    modified_by BIGINT,
    created_time BIGINT,
    modified_time BIGINT
);

-- 2. Create profiles table
CREATE TABLE IF NOT EXISTS profiles (
    profile_id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    profile_name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(50),
    created_time BIGINT NOT NULL,
    modified_time BIGINT,
    created_by BIGINT,
    modified_by BIGINT
);

-- 3. Create permissions table
CREATE TABLE IF NOT EXISTS permissions (
    permissions_id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    permission_name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    permission_type VARCHAR(50),
    resource VARCHAR(255),
    status INTEGER,
    created_time BIGINT NOT NULL,
    modified_time BIGINT,
    created_by BIGINT,
    modified_by BIGINT
);

-- 4. Create users table (dependent on roles and profiles)
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email_id VARCHAR(255) NOT NULL UNIQUE,
    role BIGINT,
    profile BIGINT,
    status VARCHAR(255),
    address TEXT,
    country VARCHAR(255),
    city VARCHAR(255),
    street VARCHAR(255),
    state VARCHAR(255),
    zip VARCHAR(255),
    dob TIMESTAMP,
    gender VARCHAR(50),
    time_zone TIMESTAMP,
    language VARCHAR(50),
    locale VARCHAR(50),
     created_time BIGINT NOT NULL,
    modified_time BIGINT,
    created_by BIGINT,
    modified_by BIGINT,
    CONSTRAINT fk_user_role FOREIGN KEY (role) REFERENCES roles(role_id) ON DELETE SET NULL,
    CONSTRAINT fk_user_profile FOREIGN KEY (profile) REFERENCES profiles(profile_id) ON DELETE SET NULL
);

-- Step 4: Add foreign keys for roles and profiles referencing users

ALTER TABLE roles
ADD CONSTRAINT fk_roles_created_by FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE SET NULL,
ADD CONSTRAINT fk_roles_modified_by FOREIGN KEY (modified_by) REFERENCES users(user_id) ON DELETE SET NULL;

ALTER TABLE profiles
ADD CONSTRAINT fk_profiles_created_by FOREIGN KEY (created_by) REFERENCES users(user_id) ON DELETE SET NULL,
ADD CONSTRAINT fk_profiles_modified_by FOREIGN KEY (modified_by) REFERENCES users(user_id) ON DELETE SET NULL;

-- 5. Create profile_permissions table (dependent on profiles and permissions)
CREATE TABLE IF NOT EXISTS profile_permissions (
    profile_permission_id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    profile_id BIGINT NOT NULL,
    permissions_id BIGINT NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_time BIGINT NOT NULL,
    modified_time BIGINT,
    modified_by BIGINT,
    FOREIGN KEY (profile_id) REFERENCES profiles(profile_id) ON DELETE CASCADE,
    FOREIGN KEY (permissions_id) REFERENCES permissions(permissions_id) ON DELETE CASCADE,
    FOREIGN KEY (modified_by) REFERENCES users(user_id) ON DELETE SET NULL
);

-- 6. Create role_hierarchy table (dependent on roles)
CREATE TABLE IF NOT EXISTS role_hierarchy (
    id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    role_id BIGINT NOT NULL,
    parent_role_id BIGINT,
    distance INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (parent_role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- 7. Create company table (dependent on users)
CREATE TABLE IF NOT EXISTS company (
    org_id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    org_name VARCHAR(250) NOT NULL,
    phone VARCHAR(50),
    description VARCHAR(100),
    street VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    pincode INTEGER,
    currency VARCHAR(10),
    timezone VARCHAR(50),
    website VARCHAR(255),
    gdpr_compliance VARCHAR(1000),
    hipaa_compliance VARCHAR(1000),
    properties VARCHAR(1000),
    created_time BIGINT NOT NULL,
    modified_time BIGINT,
    modified_by BIGINT,
    FOREIGN KEY (modified_by) REFERENCES users(user_id) ON DELETE SET NULL
);

-- 8. Create independent tables (modules, fields, sections, layouts)
CREATE TABLE IF NOT EXISTS modules (
    module_id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    module_name VARCHAR(250),
    plural_name VARCHAR(250),
    singular_name VARCHAR(250),
    description TEXT,
    system_defined BOOLEAN,
    is_disabled BOOLEAN,
    table_name VARCHAR(250),
    created_time BIGINT,
    modified_time BIGINT,
    created_by BIGINT,
    modified_by BIGINT
);



CREATE TABLE fields (
    field_id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    field_name VARCHAR(255),
    column_name VARCHAR(255),
    type SMALLINT, -- Enum FieldsType
    max_length INTEGER,
    min_length INTEGER,
    default_value VARCHAR(255),
    validation_rule TEXT, -- Validation rules like regex or constraints
    file_type VARCHAR(255), -- Allowed file types/extensions for FILE/IMAGE
    storage_path TEXT, -- Storage location for FILE/IMAGE fields
    lookup_table VARCHAR(255), -- Reference for LOOKUP fields
    display_name VARCHAR(255),
    is_picklist BOOLEAN, -- Indicates if the field is a picklist
    created_time BIGINT,
    modified_time BIGINT,
    created_by BIGINT,
    modified_by BIGINT,

    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (modified_by) REFERENCES users(user_id)
);

CREATE TABLE picklist_options (
    id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    field_id BIGINT NOT NULL, -- Reference to the field
    module_id BIGINT NOT NULL, -- Reference to the module
    option_value VARCHAR(255) NOT NULL, -- Actual value
    display_label VARCHAR(255) NOT NULL, -- Label for the option
    is_default BOOLEAN, -- Indicates if this is the default value
    is_active BOOLEAN DEFAULT TRUE, -- Indicates if the option is active
    option_order INTEGER, -- Ordering of options
    created_time BIGINT,
    modified_time BIGINT,

    FOREIGN KEY (field_id) REFERENCES fields(field_id) ON DELETE CASCADE,
    FOREIGN KEY (module_id) REFERENCES modules(module_id) ON DELETE CASCADE
);

CREATE TABLE sections (
    section_id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    section_name VARCHAR(255),
    visibility BOOLEAN, 
    created_time BIGINT,
    modified_time BIGINT,
    created_by BIGINT,
    modified_by BIGINT,

    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (modified_by) REFERENCES users(user_id)
);


CREATE TABLE IF NOT EXISTS layouts (
    layout_id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    layout_name VARCHAR(250),
    type SMALLINT,
    system_defined BOOLEAN,
    created_time BIGINT,
    modified_time BIGINT,
    metadata TEXT,
    created_by BIGINT,
    modified_by BIGINT
   
);

-- 9. Create dependent tables (modules_layouts, sections_fields, layouts_sections)
CREATE TABLE modules_layouts (
    id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    module_id BIGINT NOT NULL,
    layout_id BIGINT NOT NULL,
    layout_order INTEGER,
    created_time BIGINT,
    modified_time BIGINT,
    created_by BIGINT,
    modified_by BIGINT,
    FOREIGN KEY (module_id) REFERENCES modules(module_id) ON DELETE CASCADE,
    FOREIGN KEY (layout_id) REFERENCES layouts(layout_id) ON DELETE CASCADE
);

CREATE TABLE sections_fields (
    id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    section_id BIGINT NOT NULL, -- Foreign key referencing sections
    field_id BIGINT NOT NULL, -- Foreign key referencing fields
    module_id BIGINT NOT NULL, -- Foreign key referencing modules
    column_name VARCHAR(255), -- The column name in the data table
    mandatory BOOLEAN, -- Indicates if the field is mandatory
    field_order INTEGER, -- Specifies the order of the field in the section
    unique_field BOOLEAN, -- Indicates if the field value must be unique
    hippa BOOLEAN, -- Indicates if the field is HIPAA compliant
    phi_field BOOLEAN, -- Indicates if the field is PHI (Protected Health Information)

    -- Foreign key constraints
    FOREIGN KEY (section_id) REFERENCES sections(section_id) ON DELETE CASCADE,
    FOREIGN KEY (field_id) REFERENCES fields(field_id) ON DELETE CASCADE,
    FOREIGN KEY (module_id) REFERENCES modules(module_id) ON DELETE CASCADE
);



CREATE TABLE layouts_sections (
    id BIGINT PRIMARY KEY DEFAULT nextval('record_sequence'),
    layout_id BIGINT NOT NULL, -- Foreign key to Layouts table
    section_id BIGINT NOT NULL, -- Foreign key to Sections table
    module_id BIGINT NOT NULL, -- Foreign key to Modules table
    column_name VARCHAR(255), -- Column name in the module data table
    section_order INTEGER, -- Order of sections in the layout
    created_time BIGINT, -- Timestamp of creation
    modified_time BIGINT, -- Timestamp of modification
    created_by BIGINT, -- Foreign key to Users table
    modified_by BIGINT, -- Foreign key to Users table

    -- Foreign key constraints
    FOREIGN KEY (layout_id) REFERENCES layouts(layout_id) ON DELETE CASCADE,
    FOREIGN KEY (section_id) REFERENCES sections(section_id) ON DELETE CASCADE,
    FOREIGN KEY (module_id) REFERENCES modules(module_id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (modified_by) REFERENCES users(user_id)
);


