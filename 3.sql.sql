drop table Top5Restaurants;
create table Top5Restaurants(rID int);

CREATE OR REPLACE PROCEDURE AddNewRatings(RestaurantName IN varchar2,UserName IN varchar2, Rating IN INT, RatingDate IN varchar2 ) 
AS
res_id int;
last_user_id int:=2009;
user_id int;
cc int;
Begin
  select rid into res_id 
    from restaurant 
   where name = RestaurantName;
  
  select count(name) into cc 
    from reviewer 
   where name = UserName;
  
  IF cc = 0 then
    insert into reviewer values (last_user_id,UserName );
    user_id := last_user_id;
    last_user_id := last_user_id+1;
    DBMS_OUTPUT.PUT_LINE('New reviewer' ||UserName|| ' has been added');
  else
      select vID into user_id 
        from reviewer 
       where name = UserName;  
  end if;
  insert into rating 
  values (res_id, user_id, Rating, TO_DATE( RatingDate) );

End;

create or replace trigger insert_trig
after insert on rating 
begin
  delete from Top5Restaurants;
  insert into Top5Restaurants
  (rID)
  select rID from
  (
  select * from
  (
  select rID,SUM(stars)/COUNT(STARs) as midStars 
  from rating 
  group by rID
  order by rID
  )  
  order by midStars DESC
  )
  where ROWNUM < 6;
end; 


begin 
AddNewRatings('Jade Court','Sarah M.', 4, '08/17/2017');
AddNewRatings('Shanghai Terrace','Cameron J.', 5, '08/17/2017');
AddNewRatings('Rangoli','Vivek T.',3,'09/17/2017');
AddNewRatings('Shanghai Inn','Audrey M.',2,'07/08/2017');
AddNewRatings('Cumin','Cameron J.', 2, '09/17/2017');

end; 

select * from top5restaurants

