CREATE TABLE task_user
( 
  USERID nvarchar2(100) NOT NULL,
  NAME nvarchar2(300) NOT NULL
);

CREATE TABLE task
( ID nvarchar2(50) NOT NULL,
  TASKNAME nvarchar2(1000) NOT NULL,
  TARGET_DATE date,
  BUIZRESOURCE nvarchar2(1000),
  STATUS INT,
  CREATED_DATE date
);


CREATE TABLE task_assign
(
  TASKID nvarchar2(100) NOT NULL,
  USERID nvarchar2(100) NOT NULL
);