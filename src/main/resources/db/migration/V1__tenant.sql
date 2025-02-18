-- Create default roles
INSERT INTO roles (role_name, created_time) VALUES ('Level 1', EXTRACT(EPOCH FROM NOW()) * 1000);
INSERT INTO roles (role_name, created_time) VALUES ('Level 2', EXTRACT(EPOCH FROM NOW()) * 1000);

-- Create default profiles
INSERT INTO profile (profile_name, description, created_time) 
VALUES ('Administrator', 'Default admin profile', EXTRACT(EPOCH FROM NOW()) * 1000);

INSERT INTO profile (profile_name, description, created_time) 
VALUES ('Standard User', 'Default user profile', EXTRACT(EPOCH FROM NOW()) * 1000);
