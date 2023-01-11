-- Connect to remote DB command.
psql --"--conects by default database."
psql --host=example-database.cy6kcah5hexg.us-east-1.rds.amazonaws.com --port=5432 --username=postgres --password --dbname=example
psql --help

--------------------------inside psql mode-----------------
\l --list of databases.
\c "dbName" --connect to a db.
\d --list all tables.
\dt --just tables.
\d "tableName" --list columns.
-------------------------DDL------------------
create database "name";

create table "name"(
  id int not null primary key,
  id bigserial not null,
  name varchar(50),
  date DATE
);

alter table cars add column price numeric(19, 2);

drop database "db name";

drop table deparments cascade; -- cascade will delete reference tables as well.

------------------------------------------------------DML----------------------------------------------------
insert into "tableName" values(default, 'name', '05-03-2022');

select * from "nameTable" order by "column" asc;
select * from "nameTable" order by "column" desc;
select distinct "column" from "nameTable" order by "column"; -- remove duplicates.
select * from "nameTable" where "column" = "value";
select * from "nameTable" where "column" = "value" and "column" = "value";
select * from cars where brand = 'mazda' and (model = 'mazda2' or year = '2000-09-08') and "column" = "value";

update cars set price = 25000 where id in (4, 8, 9, 10);
update cars set price = 150000 where id = 2;

select * from "nameTable" where "column" in ("value", "value" ...); --records with values given.
select * from "nameTable" where "column" between date '2000-01-01' and '2011-01-01'; --records between range given.
select * from cars where brand like 'volk%';
select * from cars where brand ilike 'V%'; --no case sensitive.
-- you can use comparison with any type. filter down data in the WHERE clause.
select 1 = 1;
select 1 < 1;
select 1 <= 1;
select 1 > 1;
select 1 >= 1;
select 1 <> 2; -- not equal.
----------------------LIMIT, OFFSET------------------
select * from "nameTable" limit 3; -- will show only 3 records.
select * from cars offset 3 limit 3; --will show 3 records, starting from the 4th record.
select * from cars offset 3 fetch first 3 row only; --will do the same from above. sql standard.

--------------------------Using aggregation functions-----------------------------
select "column", count(*) from "nameTable" group by "column"; --count how many records have the group column.
select brand, count(*) from cars group by brand order by count;
select brand, count(*) from cars group by brand having count(*) > 1 order by count; -- returns counts > 1.
----------------------------------------
select max("column") from "nameTable";
select min(price) from cars;
select avg(price) from cars;
select round(avg(price)) from cars;

select brand, model, min(price) from cars group by brand, model; --get the minimum price for each brand.
select brand, min(price) from cars group by brand;

