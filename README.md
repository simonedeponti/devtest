# Get started

There is a MySQL you can turn on via docker:

    $ docker-compose up -d

After that, start the app with:

    $ ./mvnw spring-boot:run

Or via IntelliJ.

API details will be found at http://localhost:8080/swagger-ui/index.html

## Running tests

> ⚠️ The docker compose with mysql must be active

Either use your IDE or run them via shell through:

    $ ./mvnw test

## Repo structure

- `beans` → Beans used for JSON serialization
- `models` → Stuff that goes onto database
- `repositories` → Repositories
- `controllers` → Controllers

## Notes

- The application will clean DB and reload schema and sample data at each start
- Named queries can be found at `META-INF/jdbc-named-queries.properties`
- By default, it will connect at a local mysql using a DB named `test`. For changing see "Environment variables" below

## Environment Variables

- `DATABASE_HOST` → DB host to connect to. Default `127.0.0.1`
- `DATABASE_PORT` → DB port to connect to. Default `3306`
- `DATABASE_NAME` → name of schema to use. Default `test`
- `DATABASE_USER` → DB user. Default `test`
- `DATABASE_PASSWORD` → DB user password. Default `test`