INSERT INTO modules (module_name, plural_name, singular_name, system_defined, is_disabled, table_name, created_time, modified_time)
VALUES ('Contact', 'Contacts', 'Contact', TRUE, FALSE, '1', 
        (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
        (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT);

-- Capture the generated module_id
DO $$ 
DECLARE
    module_id_var BIGINT;
    layout_id_var BIGINT;
    basic_info_section_id_var BIGINT;
    address_section_id_var BIGINT;
    -- Basic Information Fields
    first_name_id BIGINT;
    last_name_id BIGINT;
    mail_id BIGINT;
    phone_id BIGINT;
    mobile_id BIGINT;
    -- Address Fields
    street_id BIGINT;
    city_id BIGINT;
    district_id BIGINT;
    state_id BIGINT;
    country_id BIGINT;
BEGIN
    -- Step 1: Fetch the module_id
    SELECT m.module_id INTO module_id_var FROM modules m WHERE m.module_name = 'Contact';

    -- Step 2: Insert the Layout
    INSERT INTO layouts (layout_name, created_time, modified_time, system_defined, type)
    VALUES ('Standard', 
            (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
            (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
            TRUE, 1);

    -- Step 3: Fetch the layout_id
    SELECT l.layout_id INTO layout_id_var FROM layouts l WHERE l.layout_name = 'Standard';

    -- Step 4: Map the Module to the Layout
    INSERT INTO modules_layouts (module_id, layout_id, layout_order, created_time, modified_time)
    VALUES (module_id_var, layout_id_var, 1, 
            (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
            (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT);

    -- Step 5: Insert Sections
    INSERT INTO sections (section_name, created_time, modified_time)
    VALUES 
    ('Basic Information', 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT),
    ('Address', 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT);

    -- Fetch Section IDs
    SELECT s.section_id INTO basic_info_section_id_var FROM sections s WHERE s.section_name = 'Basic Information';
    SELECT s.section_id INTO address_section_id_var FROM sections s WHERE s.section_name = 'Address';

INSERT INTO layouts_sections (layout_id, section_id, module_id, section_order, created_time, modified_time)
VALUES 
(layout_id_var, basic_info_section_id_var, module_id_var, 1, 
 (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
 (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT),
(layout_id_var, address_section_id_var, module_id_var, 2, 
 (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
 (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT);
    -- Step 7: Insert Fields for Basic Information
    INSERT INTO fields (field_name, column_name, type, max_length, min_length, display_name, is_picklist, created_time, modified_time)
    VALUES 
    ('First Name', 'first_name', 1, 255, 1, 'First Name', FALSE, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT),
    ('Last Name', 'last_name', 1, 255, 1, 'Last Name', FALSE, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT),
    ('Mail', 'mail', 4, 255, 5, 'Mail', FALSE, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT),
    ('Phone', 'phone', 3, 15, 10, 'Phone', FALSE, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT),
    ('Mobile', 'mobile', 3, 15, 10, 'Mobile', FALSE, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT);

    -- Fetch Field IDs for Basic Information
    SELECT f.field_id INTO first_name_id FROM fields f WHERE f.field_name = 'First Name';
    SELECT f.field_id INTO last_name_id FROM fields f WHERE f.field_name = 'Last Name';
    SELECT f.field_id INTO mail_id FROM fields f WHERE f.field_name = 'Mail';
    SELECT f.field_id INTO phone_id FROM fields f WHERE f.field_name = 'Phone';
    SELECT f.field_id INTO mobile_id FROM fields f WHERE f.field_name = 'Mobile';


    -- Step 8: Insert Fields for Address Section
    INSERT INTO fields (field_name, column_name, type, max_length, min_length, display_name, is_picklist, created_time, modified_time)
    VALUES 
    ('Street', 'street', 2, 255, 1, 'Street', FALSE, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT),
    ('City', 'city', 1, 100, 1, 'City', FALSE, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT),
    ('District', 'district', 1, 100, 1, 'District', FALSE, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT),
    ('State', 'state', 1, 100, 1, 'State', FALSE, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT),
    ('Country', 'country', 1, 100, 1, 'Country', FALSE, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT, 
     (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)::BIGINT);

    -- Fetch Field IDs for Address Section
    SELECT f.field_id INTO street_id FROM fields f WHERE f.field_name = 'Street';
    SELECT f.field_id INTO city_id FROM fields f WHERE f.field_name = 'City';
    SELECT f.field_id INTO district_id FROM fields f WHERE f.field_name = 'District';
    SELECT f.field_id INTO state_id FROM fields f WHERE f.field_name = 'State';
    SELECT f.field_id INTO country_id FROM fields f WHERE f.field_name = 'Country';
-- Map Basic Information Fields to the Section
INSERT INTO sections_fields (section_id, field_id, module_id, mandatory, field_order, column_name, unique_field, hippa, phi_field)
VALUES 
(basic_info_section_id_var, first_name_id, module_id_var, TRUE, 1, 'first_name', FALSE, FALSE, FALSE),
(basic_info_section_id_var, last_name_id, module_id_var, TRUE, 2, 'last_name', FALSE, FALSE, FALSE),
(basic_info_section_id_var, mail_id, module_id_var, FALSE, 3, 'mail', FALSE, FALSE, FALSE),
(basic_info_section_id_var, phone_id, module_id_var, FALSE, 4, 'phone', FALSE, FALSE, FALSE),
(basic_info_section_id_var, mobile_id, module_id_var, FALSE, 5, 'mobile', FALSE, FALSE, FALSE);

-- Map Address Fields to the Section
INSERT INTO sections_fields (section_id, field_id, module_id, mandatory, field_order, column_name, unique_field, hippa, phi_field)
VALUES 
(address_section_id_var, street_id, module_id_var, TRUE, 1, 'street', FALSE, FALSE, FALSE),
(address_section_id_var, city_id, module_id_var, TRUE, 2, 'city', FALSE, FALSE, FALSE),
(address_section_id_var, district_id, module_id_var, FALSE, 3, 'district', FALSE, FALSE, FALSE),
(address_section_id_var, state_id, module_id_var, FALSE, 4, 'state', FALSE, FALSE, FALSE),
(address_section_id_var, country_id, module_id_var, FALSE, 5, 'country', FALSE, FALSE, FALSE);


END $$;

