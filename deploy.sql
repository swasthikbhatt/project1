DO $$
    BEGIN
        IF
            NOT EXISTS (SELECT FROM pg_database WHERE datname = 'project1')
        THEN
            CREATE DATABASE project1;
        END IF;
    END
$$;