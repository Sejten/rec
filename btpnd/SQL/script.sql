DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS countries;
CREATE TABLE countries(id INTEGER, name TEXT, iso TEXT, PRIMARY KEY (id));
CREATE Table users (id integer, email TEXT, citizenship_country_id INTEGER, PRIMARY KEY (id),FOREIGN KEY (citizenship_country_id) REFERENCES countries(id));


SELECT countries.name, COUNT(countries.name)
FROM users
INNER JOIN countries ON users.citizenship_country_id=countries.id
GROUP BY countries.name
HAVING COUNT(countries.name) > 1
ORDER BY COUNT(countries.name) DESC