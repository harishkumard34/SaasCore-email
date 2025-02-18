-- Create default roles
INSERT INTO roles (role_id,role_name, created_time) VALUES (nextval('${schema}.record_sequence'),'Level 1', EXTRACT(EPOCH FROM NOW()) * 1000);
INSERT INTO roles (role_id,role_name, created_time) VALUES (nextval('${schema}.record_sequence'),'Level 2', EXTRACT(EPOCH FROM NOW()) * 1000);

-- Create default profiles
INSERT INTO profiles (profile_id,profile_name, description, created_time) 
VALUES (nextval('${schema}.record_sequence'),'Administrator', 'Default admin profile', EXTRACT(EPOCH FROM NOW()) * 1000);

INSERT INTO profiles (profile_id,profile_name, description, created_time) 
VALUES (nextval('${schema}.record_sequence'),'Standard User', 'Default user profile', EXTRACT(EPOCH FROM NOW()) * 1000);
