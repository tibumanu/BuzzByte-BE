This file's purpose is to guide the dev to setting up Postgres on their machine and IDE. It **skips** the installation of said software. You can download it from [here](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads) - we are using version 16.4.

The installation should be pretty straight forward - ask me or David if having trouble.
### When prompted for a password in the wizard, make sure you do not forget it!

After you are done installing it, see the screenshots below.

Here are the commands required to run inside `psql` (seen in the first screenshot)
```
; ! These have to be run on one line each

CREATE USER buzzbytedev WITH PASSWORD 'buzz' CREATEDB;
CREATE DATABASE buzzbytedb;
ALTER DATABASE buzzbytedb OWNER TO abc2024;

```

![screenshot1](/setup-postgres-1-psql.png)

### Here's the IDE setup
After opening the project (the BuzzByte folder) in IntelliJ Idea, see the screenshots below.
After adding the new database connection, make sure in the bottom right corner no errors appear. Afterwards, we'll create a new table inside the database and refresh the IDE to see that those changes are reflected.


![screenshot2](/setup-postgres-2.png)
