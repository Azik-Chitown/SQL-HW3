# SQL-HW4
CSC453 Homework 4



Q1. Restaurant Address Cleanup (20 points)

Use the Restaurants.sql file from HW2, which creates three tables Restaurant, Reviewer, and Rating. In this problem, we are concerned with the Restaurant table, which has a single attribute 'Address' of type 'varchar2(100)'. We would like address to be searchable. So we would like to create another table Restaurant_Locations with the following attributes:

rID, name, street_address, city, state, zipcode, cuisine

a. Create Restaurant_Locations table. Use the source dataset to determine the data types (and sizes) to use for each of the attributes.

b. Write a cursor (using SQL and PL/SQL) to process each row from the original Restaurant table, extracting information as necessary to populate the new Restaurant_Locations table. The original address field must be split up and parsed into the new street_address, city, state, and zipcode fields.

Submit 1.sql file.


Q2. Restaurant JDBC (15 points)

Create ZipCode table based on attributes in zipcode.csv file (zip, city, state, latitude, longitude, timezone, dst)

Then download the CHIzipcode.csv file provided on D2L.

Write a Java program (similar to the one showed in class to connect to your Oracle database using JDBC driver) to convert the zipcode.csv file into a series of SQL insert statements for ZipCode table.

c. Use the program to join zipcodes table with restaurant_locations table and obtain latitude and longitude of all restaurants in the Restaurants table.

Print out the name, zipcode, latitude, longitude using your program.

Output should look like this:

Shanghai Inn, 60625, "41.971614","-87.70256"

Submit your zipcode.sql, zipcode.java and zipcode.class files


Q3. PL/SQL Functions (25 points)

The restaurants in the database continue to be visited and reviewed. Information about new restaurant reviews is made available as:

(RestaurantName, UserName, Rating, RatingDate)

Restaurant names and user names are assumed to be unique.

Write a PL/SQL stored procedure that accepts the above input string and inserts new restaurant rating information into the Rating table. If a new user appears, it inserts into the Reviewer table.

Also, create a table ‘Top5Restaurants’ restaurants in the database as:

Create table Top5Restaurants(rID int)

Top5Restaurants holds the rIDs of top 5 restaurants in Chicago. Write a row-level trigger on the Rating table that computes top 5 restaurants and populates the Top5Restaurants table. This trigger is fired every time a restaurant receives a new rating.

Test your procedure and trigger in SQL Developer to insert the following four strings:

(‘Jade Court’,`Sarah M.’, 4, ‘08/17/2017’)

(‘Shanghai Terrace’,`Cameron J.’, 5, ‘08/17/2017’)

(‘Rangoli’,`Vivek T.’,3,`09/17/2017’)

(‘Shanghai Inn’,`Audrey M.’,2,`07/08/2017’);

(‘Cumin’,`Cameron J.’, 2, ‘09/17/2017’)


Submit your 3.sql file and screenshot of data in Top5Restaurant table after the last inserted string in (c).


Q4. Triggers. (15 points)

Consider the TASK and CONTRACT tables defined by the following script, which also populates the JOB table: 

DROP TABLE CONTRACT CASCADE CONSTRAINTS;

DROP TABLE TASK CASCADE CONSTRAINTS;


CREATE TABLE TASK (

TaskID CHAR(3),

TaskName VARCHAR(20),

ContractCount NUMERIC(1,0) DEFAULT 0,

CONSTRAINT PK_TASK PRIMARY KEY (TaskID)

);


CREATE TABLE CONTRACT

(

TaskID CHAR(3),

WorkerID CHAR(7),

Payment NUMERIC(6,2),

CONSTRAINT PK_CONTRACT PRIMARY KEY (TaskID, WorkerID),

CONSTRAINT FK_CONTRACTTASK FOREIGN KEY (TaskID) REFERENCES TASK (TaskID)

);


INSERT INTO TASK (TaskID, TaskName) VALUES ('333', 'Security' );

INSERT INTO TASK (TaskID, TaskName) VALUES ('322', 'Infrastructure');

INSERT INTO TASK (TaskID, TaskName) VALUES ('896', 'Compliance' );


SELECT * FROM TASK;

COMMIT;



The ContractCount attribute of TASK should store a count of how many workers have signed contracts to work on that task, that is, the number of records in CONTRACT with that TaskID, and its value should never exceed 3. Your task is to write three triggers that will maintain the

value of the ContractCount attribute in TASK as changes are made to the CONTRACT table. Write a script file Problem2.sql containing definitions of the following three triggers:

1. The first trigger, named NewContract, will fire when a user attempts to INSERT a row into CONTRACT. This trigger will check the value of ContractCount for the corresponding task. If ContractCount is less than 3, then there is still room in the task for another worker, so it will allow the INSERT to occur and will increase the value of ContractCount by one. If ContractCount is equal to 3, then the task is full, so it will cancel the INSERT and display an error message stating that the task is full.

2. The second trigger, named EndContract, will fire when a user attempts to DELETE one or more rows from CONTRACT. This trigger will update the values of ContractCount for any affected tasks to make sure they are accurate after the rows are deleted, by decreasing the value of ContractCount by one each time a worker is removed from a task.

3. The third trigger, named NoChanges, will fire when a user attempts to UPDATE one or more rows of CONTRACT. The trigger will cancel the UPDATE and display an error message stating that no updates are permitted to existing rows of CONTRACT.

NewContract and EndContract should be row-level triggers; NoChanges should be a statement-level trigger.

Run the script to define your triggers and test them to make sure they work by doing a few INSERTS, DELETES, and UPDATES on the CONTRACT table.

Submit one 4.sql file with the trigger code and your insert, delete and update statements.

Q5. Transactions (7 points)


Consider the following transactions:

S: [X := X + 10; Y := Y - 10]

T: [X := X * 2; Y := Y * 2]

U: [Y := Y + 10; X := X - 10]

Assuming initial values of X = 15 and Y = 25, concurrent execution of these three transactions can leave the database in various states. Determine the state of the database (values of X and Y) assuming isolation level serializable for each of S, T, and U.


Q6. Transactions (8 points)

Consider two tables R(A,B) and S(C). Below are pairs of transactions. For each pair, decide whether it is possible for nonserializable behavior to be exhibited when executing the transactions concurrently, while respecting their specified isolation levels. Assume individual statements are executed atomically, and each transaction executes to completion.

(a) Transaction 1:

Set Transaction Isolation Level Read Uncommitted;

Select count(*) From R;

Select count(*) From S;

Commit;

    Transaction 2:

Set Transaction Isolation Level Serializable;

Insert Into R Values (1,2);

Insert Into S Values (3);

Commit;


(b) Transaction 1:

Set Transaction Isolation Level Read Committed;

Select count(*) From R;

Select count(*) From S;

Commit;

    Transaction 2:

Set Transaction Isolation Level Serializable;

Insert Into R Values (1,2);

Insert Into R Values (3,4);

Commit;


(c) Transaction 1:

Set Transaction Isolation Level Repeatable Read;

Select count(*) From R;

Select count(*) From S;

Select count(*) From R;

Commit;

    Transaction 2:

Set Transaction Isolation Level Serializable;

Insert Into R Values (1,2);

Commit;

