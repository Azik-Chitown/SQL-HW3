drop table zipcode;
create table zipcode (
zip varchar2(5),
city varchar2(50),
state varchar2(2),
latitude Decimal(8,6), 
longitude Decimal(8,6), 
timezone Number(1), 
dst Number(1));