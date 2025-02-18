package com.techpuram.saascore;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseResetMain {

    public static void main(String[] args) {
        try {
            // Load application.properties
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/application.properties"));

            // Fetch database details
            String dbUrl = properties.getProperty("spring.datasource.url");
            String dbUser = properties.getProperty("spring.datasource.username");
            String dbPassword = properties.getProperty("spring.datasource.password");

            if (dbUrl == null || dbUser == null || dbPassword == null) {
                throw new IllegalArgumentException("Database configuration is missing in application.properties");
            }

            // SQL scripts to reset the database
            String dropSchemasSql = """
            	    DO $$ DECLARE
            	        schema_name_var TEXT;
            	    BEGIN
            	        FOR schema_name_var IN
            	            SELECT schema_name
            	            FROM information_schema.schemata
            	            WHERE schema_name NOT IN ('pg_catalog', 'information_schema', 'pg_toast', 'pg_temp_1', 'pg_toast_temp_1', 'public')
            	        LOOP
            	            EXECUTE format('DROP SCHEMA IF EXISTS %I CASCADE;', schema_name_var);
            	        END LOOP;
            	    END $$;
            	""";

            String resetPublicSchemaSql = """
                DROP SCHEMA IF EXISTS public CASCADE;
                CREATE SCHEMA public;
                GRANT ALL ON SCHEMA public TO postgres;
                GRANT ALL ON SCHEMA public TO public;
            """;

            // Load PostgreSQL driver
            Class.forName("org.postgresql.Driver");

            // Connect to the database and execute reset SQL
            try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
                connection.createStatement().execute(dropSchemasSql);
                connection.createStatement().execute(resetPublicSchemaSql);
                System.out.println("Database reset successfully.");
            }
        } catch (IOException e) {
            System.err.println("Error loading application.properties: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error resetting the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
