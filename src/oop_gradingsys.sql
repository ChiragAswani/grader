-- CREATE TABLE part ( 
-- p_partkey integer, 
-- p_name varchar(55),
-- PRIMARY KEY (p_partkey)
-- );



-- CREATE TABLE supplier ( 
-- s_suppkey integer, 
-- s_name char(55),
-- PRIMARY KEY (s_suppkey)
-- );



-- CREATE TABLE partsupp ( 
-- ps_pskey integer, 
-- ps_partkey integer, 
-- ps_suppkey integer, 
-- ps_availqty integer, 
-- ps_placed date, 
-- ps_ship date,
-- PRIMARY KEY (ps_pskey),
-- FOREIGN KEY (ps_partkey) REFERENCES part(p_partkey),
-- FOREIGN KEY (ps_suppkey) REFERENCES supplier(s_suppkey)
-- );

SELECT * FROM partsupp WHERE ps_availqty = 70;
SHOW PROFILES;
SHOW STATUS LIKE 'last_query_cost';

DESCRIBE part;

CREATE INDEX ps_availqty_idx ON partsupp (ps_availqty);

SELECT * FROM partsupp WHERE ps_availqty < 70;

SELECT MAX(ps_availqty) FROM partsupp;

SELECT DISTINCT (s_name) FROM supplier, partsupp WHERE s_suppkey = ps_suppkey AND ps_availqty < 40;

SELECT p_name, s_name FROM part, supplier, partsupp WHERE s_suppkey = ps_suppkey AND p_partkey = ps_partkey;




