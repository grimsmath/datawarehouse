DROP TABLE guardian_purchase;
DROP TABLE visit_procedure;
DROP TABLE visit_prescription;
DROP TABLE visit;
DROP TABLE med_procedure;
DROP TABLE patient;
DROP TABLE guardian;
DROP TABLE doctor;
DROP TABLE employee;

DROP TABLE prescription;

DROP TABLE pet_food;


DROP TABLE store_trans;
DROP TABLE store_item;

DROP TABLE branch;

CREATE TABLE guardian
(			
guardian_id	NUMBER(5),
fname VARCHAR2(30),		
lname VARCHAR2(30),		
address	VARCHAR2(40),
city VARCHAR2(30),		
stateAbbr CHAR(2),
zip NUMBER(5),
phone VARCHAR2(10),	
PRIMARY KEY (guardian_id)	
);

CREATE TABLE patient			
(
pet_id	VARCHAR2(5),
patient_name VARCHAR2(20),		
guardian_id	NUMBER(5) NOT NULL,
species	VARCHAR2(20),		
breed	VARCHAR2(20),		
sex	CHAR(2),	
age	NUMBER(4,1),
description VARCHAR2(30),
PRIMARY KEY (pet_id),
CONSTRAINT fk_patient_guardianid FOREIGN KEY (guardian_id)
REFERENCES guardian (guardian_id)
);		


CREATE TABLE branch
(
clinic_id NUMBER(2),
clinic_name VARCHAR2(50),
address VARCHAR2(50),
city VARCHAR2(30),
stateAbbr CHAR(2),
zip NUMBER(5),
phone VARCHAR2(10),
PRIMARY KEY (clinic_id)
);


CREATE TABLE doctor		
(
vet_id	NUMBER(5),
fname	VARCHAR2(20),	
lname	VARCHAR2(20),	
address	VARCHAR2(40),
city VARCHAR2(30),		
stateAbbr CHAR(2),
zip NUMBER(5),	
phone	VARCHAR2(10),	
specialty	VARCHAR2(20),	
PRIMARY KEY (vet_id)
);




CREATE TABLE employee
(
emp_id	NUMBER(5),
fname	VARCHAR2(20),
lname	VARCHAR2(20),
address	VARCHAR2(40),
city VARCHAR2(30),		
stateAbbr CHAR(2),
zip NUMBER(5),	
phone	VARCHAR2(10),
position	VARCHAR2(10),
start_date DATE,
clinic_id	NUMBER(1),
PRIMARY KEY (emp_id),
CONSTRAINT fk_employee_clinicid FOREIGN KEY (clinic_id)
REFERENCES branch (clinic_id)
);


CREATE TABLE med_procedure 
(
procedure_id NUMBER(5),
procedure_name VARCHAR2(20),
anaesthesia_req	CHAR(1),
hospitalization_req	CHAR(1),
proc_cost NUMBER(6,2),
PRIMARY KEY (procedure_id)
);

CREATE TABLE visit		
(
visit_id NUMBER(9),
pet_id VARCHAR2(5),
visit_date DATE DEFAULT SYSDATE,
clinic_id NUMBER(1),	
PRIMARY KEY (visit_id),
CONSTRAINT fk_visit_petid FOREIGN KEY (pet_id)
REFERENCES patient (pet_id),
CONSTRAINT fk_visit_clinicid FOREIGN KEY (clinic_id)
REFERENCES branch (clinic_id)
);

CREATE TABLE visit_procedure		
(
visit_id NUMBER(9),
procedure_id NUMBER(5),
vet_id NUMBER(5),
PRIMARY KEY (visit_id, procedure_id),
CONSTRAINT fk_vp_visitid FOREIGN KEY (visit_id)
REFERENCES visit (visit_id),
CONSTRAINT fk_vp_procedureid FOREIGN KEY (procedure_id)
REFERENCES med_procedure (procedure_id),
CONSTRAINT fk_vp_vetid FOREIGN KEY (vet_id)
REFERENCES doctor (vet_id)
);

CREATE TABLE prescription	
(
script_id NUMBER(9),
drug_id	VARCHAR2(40),
comments VARCHAR2(80),
PRIMARY KEY (script_id)
);

CREATE TABLE visit_prescription	
(
script_id NUMBER(9),
visit_id NUMBER(9),
vet_id NUMBER(5),
PRIMARY KEY (script_id, visit_id),
CONSTRAINT fk_visproc_scriptid FOREIGN KEY (script_id)
REFERENCES prescription (script_id),
CONSTRAINT fk_visproc_visitid FOREIGN KEY (visit_id)
REFERENCES visit (visit_id),
CONSTRAINT fk_visproc_vetid FOREIGN KEY (vet_id)
REFERENCES doctor (vet_id)
);


create table store_item
(
item_id NUMBER(4),
item_name VARCHAR2(30),
description VARCHAR2(50),
item_cost NUMBER(5,2),
PRIMARY KEY (item_id)
);

create table pet_food
(
food_id NUMBER(4),
food_name VARCHAR2(30),
species VARCHAR2(20),
designation VARCHAR2(50),
PRIMARY KEY (food_id)
);

CREATE TABLE store_trans
(
trans_id NUMBER(6),
clinic_id NUMBER(2),
item_id NUMBER(4),
quantity NUMBER(3),
trans_date DATE,
PRIMARY KEY (trans_id),
CONSTRAINT fk1 FOREIGN KEY (clinic_id)
REFERENCES branch (clinic_id),
CONSTRAINT fk2 FOREIGN KEY (item_id)
REFERENCES store_item (item_id)
);

CREATE TABLE guardian_purchase
(
purch_id NUMBER(4),
visit_id NUMBER(9),
trans_id NUMBER(6),
PRIMARY KEY (purch_id),
CONSTRAINT fk_gp_visitid FOREIGN KEY (visit_id)
REFERENCES visit (visit_id),
CONSTRAINT fk_gp_transid FOREIGN KEY (trans_id)
REFERENCES store_trans (trans_id)
);



INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (100,'Doris','Hardy','P.O. Box 371, 1409 Nisl. Road','Henderson','NV','23865','0818079242','cats');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (101,'Hadley','Vasquez','5700 Convallis Av.','Lakewood','CO','79238','2542384089','dogs');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (102,'Byron','Atkins','602-9533 Turpis St.','Kansas City','MO','93756','0094628624','cats');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (103,'McKenzie','Lancaster','P.O. Box 211, 4969 Etiam St.','Fayetteville','AR','72175','1204034092','exotic');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (104,'Zenaida','Gibbs','1182 Tincidunt, Rd.','Southaven','MS','10777','2236989376','cats');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (105,'Igor','Cooper','P.O. Box 749, 8930 Luctus Rd.','Jackson','MS','47072','6674362314','exotic');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (106,'Tasha','Cruz','Ap #857-7815 Libero Avenue','Missoula','MT','73500','9554794593','cats');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (107,'Andrew','Hawkins','2766 Magna. Street','Springdale','AR','72288','9997566733','dogs');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (108,'Rhea','Pace','264-7209 Et, Road','Jackson','MS','52627','7967394518','dogs');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (109,'Zachary','Rufus','Ap #494-6017 Amet St.','Green Bay','WI','90820','3341777628','small rodents');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (110,'Griffith','Jackson','Ap #354-7653 Placerat. Av.','Hartford','CT','87303','5908394569','exotic');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (111,'Lars','Nash','P.O. Box 281, 490 Proin Street','Pittsburgh','PA','71618','0952330418','dogs');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (112,'Emerson','Lindsay','2705 Quam, Road','Fayetteville','AR','72480','0614114470','farm');
INSERT INTO doctor (vet_id,fname,lname,address,city,stateAbbr,zip,phone,specialty) VALUES (113,'Raphael','Madden','7033 Neque Road','Davenport','IA','13379','4113641495','farm');



INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1000,'Joy','Knowles','170 Penatibus Street','Fort Collins','CO','72238','8856018736');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1001,'Jena','Wilson','Ap #577-1865 Magna Road','Honolulu','HI','92987','9188266489');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1002,'Scarlet','Gutierrez','783 Id St.','Wyoming','WY','19797','8178388076');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1003,'Dacey','Young','Ap #274-5905 Nunc Road','Harrisburg','PA','90756','0206469494');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1004,'Kermit','Farrell','P.O. Box 127, 9039 Dui, Street','Columbus','GA','10835','9133895648');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1005,'Jayme','Bowers','378 Fames Ave','Tuscaloosa','AL','35127','9803573062');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1006,'Nola','Dillard','6741 Felis. Ave','Provo','UT','38638','3960682631');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1007,'Joy','Potter','437-2188 Arcu. Av.','Omaha','NE','69610','4970741544');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1008,'Chase','Mason','Ap #375-9435 Ipsum St.','Lewiston','ME','56866','9267139118');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1009,'Berk','Talley','5764 Libero. Avenue','Las Vegas','NV','33144','6591981503');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1010,'Ebony','Noel','P.O. Box 623, 623 Amet St.','Hilo','HI','33763','9746936800');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1011,'Keaton','Guerrero','P.O. Box 373, 8700 Non, Av.','Lexington','KY','54192','8347305360');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1012,'Camille','Cummings','P.O. Box 922, 4752 Elementum St.','Oklahoma City','OK','97302','5732684005');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1013,'James','Phillips','3310 Aliquet Av.','Sterling Heights','MI','39877','4265782947');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1014,'Lucius','Fuentes','453-8321 Amet, St.','Savannah','GA','22033','5600032922');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1015,'Erich','Dennis','Ap #555-7253 Risus. St.','South Burlington','VT','74666','5948862912');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1016,'Lareina','Wiley','999-9035 Est, Rd.','Bangor','ME','94593','9882911433');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1017,'Igor','Bell','P.O. Box 536, 6678 Cursus Road','Columbia','MD','72219','5853248272');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1018,'Cadman','Howard','5022 Sociis Street','Denver','CO','32694','7283656588');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1019,'Germane','Key','373-5780 Donec Rd.','Orlando','FL','37108','1840361248');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1020,'Hilary','Shelton','Ap #802-4028 Vulputate Road','Springfield','IL','63866','0349351675');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1021,'Quinlan','Leach','804 Nullam Ave','Fort Wayne','IN','62295','7908293985');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1022,'Paki','Turner','134-822 Nunc. Av.','Kailua','HI','75208','0845457591');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1023,'Barry','Yates','235 Mattis. Street','Houston','TX','26842','8419395966');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1024,'Kylee','Melendez','834-6107 Sapien. Avenue','Sterling Heights','MI','26684','7426291994');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1025,'Hu','Guthrie','P.O. Box 210, 6198 Nullam Ave','Boise','ID','76139','4084998041');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1026,'Lee','Flowers','P.O. Box 453, 4416 Tellus Rd.','Chattanooga','TN','92012','8036241856');

INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1027,'Grant','Wagner','Ap #193-336 Eu Av.','Frankfort','KY','87117','1888868565');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1028,'Courtney','Oconnor','Ap #470-6812 Cubilia Rd.','Norfolk','VA','56049','6845582381');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1029,'Alexander','Edwards','409-6851 Amet Road','Olathe','KS','17842','1603592756');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1030,'Simon','Mcpherson','161-9123 Fermentum Av.','Portland','OR','14992','8004662631');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1031,'Anne','Ford','925-9361 Amet St.','Kapolei','HI','37931','0991372411');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1032,'Alan','Levine','6151 Et, Ave','Springfield','MO','42358','2305042204');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1033,'Laura','Parsons','Ap #136-6778 Nascetur St.','Cincinnati','OH','11697','1231372931');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1034,'Galvin','Mclean','522-515 Integer Rd.','Oklahoma City','OK','41880','3614935916');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1035,'Jelani','Donovan','308-4601 Ornare, St.','Bozeman','MT','25723','6552831544');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1036,'Evangeline','Britt','579-4468 Facilisis St.','Springdale','AR','72717','0809148118');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1037,'Victor','Benson','Ap #423-3053 Vestibulum, Ave','North Las Vegas','NV','91686','6958043089');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1038,'Mallory','Cardenas','Ap #410-912 Elit, St.','Tulsa','OK','84096','5562456729');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1039,'Moses','Gibson','Ap #266-440 Massa. Ave','Grand Rapids','MI','31267','8066920967');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1040,'Vance','Riley','P.O. Box 935, 2099 Diam St.','Hillsboro','OR','67046','4134872593');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1041,'Ciara','Dillon','7346 Eget Av.','Akron','OH','63477','2942728371');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1042,'Flynn','Adkins','Ap #840-3046 Urna. Rd.','Richmond','VA','52773','8704238687');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1043,'Keiko','Leach','Ap #343-8079 Mi. Av.','Cedar Rapids','IA','52369','1180011584');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1044,'Blossom','Herrera','800-5803 Lorem St.','Norman','OK','53123','3741830952');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1045,'Natalie','Adams','P.O. Box 545, 8961 Nec, Rd.','Jacksonville','FL','82309','8364255114');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1046,'Savannah','Salinas','5240 Ornare Avenue','Iowa City','IA','14795','7670120784');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1047,'Amber','Allison','Ap #728-4556 Non St.','Jefferson City','MO','43045','3227201054');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1048,'Vielka','Faulkner','2594 Purus. Rd.','Shreveport','LA','44304','9075924519');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1049,'Griffith','Payne','P.O. Box 116, 6841 Integer Av.','Nampa','ID','26978','9753583052');
INSERT INTO guardian (guardian_id,fname,lname,address,city,stateAbbr,zip,phone) VALUES (1050,'Xandra','Dickson','9674 Nulla. Rd.','Madison','WI','67896','4986142922');

INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2000,'Edward',1016,'farm','bull','M',2,'white with spots');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2001,'Martha',1024,'dogs','chihuahua','F',5,'small and barks a lot');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2002,'Piper',1003,'exotic','turtle','M',1,'green and brown shell');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2003,'Elijah',1000,'dogs','bulldog','M',17,'very friendly');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2004,'Holmes',1046,'cats','siamese','M',14,'unfriendly');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2005,'Spotty',1017,'exotic','lizard','M',10,'eats crickets');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2006,'Hilda',1046,'farm','sheep','F',7,'drinks out of bottle');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2007,'Nadine',1021,'cats','persian','F',8,'scratches');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2008,'Emi',1050,'exotic','parrot','F',17,'has good vocabulary');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2009,'Clayton',1020,'cats','domestic','M',5,'very friendly');



INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2010,'Jorden',1022,'dogs','terrier','M',10,'white');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2011,'Lynn',1043,'dogs','chihuahua','F',15,'brown');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2012,'Jin',1019,'exotic','guinea pig','F',2,'loves carrots');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2013,'Wesley',1000,'exotic','turtle','M',23,'very fast');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2014,'Berk',1010,'cats','burmese','F',18,'lovable');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2015,'Levi',1044,'farm','sheep','F',8,'very woolly');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2016,'Nissim',1029,'exotic','snake','M',24,'likes to play with lizards');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2017,'Eden',1006,'dogs','chocolate lab','F',15,'very playful');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2018,'Hilda',1032,'cats','domestic','F',14,'declawed');
INSERT INTO patient (pet_id,patient_name,guardian_id,species,breed,sex,age,description) VALUES (2019,'Mr. Hopper',1031,'exotic','bunny','M',2,'Eats carrots');

INSERT INTO branch (clinic_id,clinic_name,address,city,stateAbbr,zip,phone) VALUES (1,'Caring Paws','Ap #904-122 Aliquam Rd.','Lawton','OK','20397','1684280617');
INSERT INTO branch (clinic_id,clinic_name,address,city,stateAbbr,zip,phone) VALUES (2,'Tails and Hearts','Ap #803-9111 Etiam Rd.','Augusta','GA','47401','5358924088');
INSERT INTO branch (clinic_id,clinic_name,address,city,stateAbbr,zip,phone) VALUES (3,'Midland Veterinary','5223 Sed St.','Anchorage','AK','99574','4516008450');
INSERT INTO branch (clinic_id,clinic_name,address,city,stateAbbr,zip,phone) VALUES (4,'Center Street Clinic','960-164 Nascetur St.','Cedar Rapids','IA','28305','8212591438');
INSERT INTO branch (clinic_id,clinic_name,address,city,stateAbbr,zip,phone) VALUES (5,'Toms Farm Animal Clinic','Ap #228-7509 Risus Road','San Jose','CA','93667','6206464449');
INSERT INTO branch (clinic_id,clinic_name,address,city,stateAbbr,zip,phone) VALUES (6,'Fluffy and Fido Veterinary Clinic','2892 Et, Rd.','West Valley City','UT','38792','7474338752');


INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3000,'Emmanuel','Shepherd','118-4048 Libero. Avenue','Kansas City','KS','99317','7197750635','01-NOV-12',1);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3001,'Lillith','Schwartz','P.O. Box 236, 139 Fames Rd.','Omaha','NE','72042','9449885960','13-AUG-11',1);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3002,'Walker','Vaughan','966-6050 Praesent St.','Tacoma','WA','99095','2362774337','21-SEP-11',1);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3003,'Reagan','Whitfield','3954 Pellentesque Avenue','Jefferson City','MO','40495','2538533895','05-OCT-11',1);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3004,'Carla','Joseph','P.O. Box 476, 8121 Vulputate Street','Evansville','IN','17879','4507936117','29-JUL-11',2);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3005,'Neville','Atkinson','P.O. Box 243, 1551 Lacinia Street','Chattanooga','TN','17049','1541919134','06-JUN-11',2);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3006,'Althea','Ramos','230-3092 Donec St.','Eugene','OR','26672','5728659067','05-MAR-10',2);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3007,'Anne','Lopez','Ap #937-2615 Ridiculus Ave','Bloomington','MN','86196','6794749477','05-MAR-09',1);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3008,'Travis','Fuentes','Ap #559-2399 Euismod Rd.','Norman','OK','76690','7520705867','15-MAY-09',3);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3009,'Jana','Daniel','P.O. Box 563, 3788 Ipsum. Road','Clarksville','TN','34040','3967676199','05-NOV-09',2);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3010,'Alexa','Farley','P.O. Box 558, 9752 Egestas Street','Newark','DE','90800','8969622243', '14-JAN-12',4);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3011,'Peter','Reynolds','821-5899 Metus. Rd.','Indianapolis','IN','71336','6159200702', '14-FEB-11',3);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3012,'Clark','Anderson','Ap #533-2346 Montes, Street','Los Angeles','CA','91158','1702402052', '05-APR-10',4);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3013,'Tana','Haynes','Ap #972-2077 Tincidunt Rd.','Little Rock','AR','72913','7221130437', '05-DEC-12',6);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3015,'Herrod','Reed','P.O. Box 550, 5015 Amet Ave','Fayetteville','AR','71578','1057487723','08-DEC-11',5);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3016,'Denton','Richards','928-8702 Ultrices, Rd.','Augusta','GA','92077','1379857561', '05-JAN-11',3);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3017,'Benedict','Garrison','Ap #203-8190 Sapien Ave','Ketchikan','AK','99797','2655871104', '03-JAN-11',6);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3018,'Amity','Riggs','P.O. Box 803, 5521 Amet, Road','Gresham','OR','48782','7923884716','18-FEB-11',1);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3019,'Knox','Gonzalez','8510 Fames Road','Bear','DE','80184','4408755754', '05-MAR-10',1);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3020,'Laurel','Marsh','P.O. Box 439, 9872 Duis Rd.','Tulsa','OK','85759','8573954371','09-NOV-11',5);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3021,'Steel','Mcleod','Ap #658-7013 Blandit Street','Lexington','KY','16228','5128464498','16-NOV-11',2);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3022,'Ann','Fuentes','Ap #680-7338 Ultrices. Rd.','Wichita','KS','57493','9041664354', '07-NOV-11',1);
INSERT INTO employee (emp_id,fname,lname,address,city,stateAbbr,zip,phone,start_date,clinic_id) VALUES (3023,'Dara','Finch','5395 Nulla Road','Bridgeport','CT','19618','9693385631', '05-NOV-09',2);


INSERT INTO med_procedure (PROCEDURE_ID, PROCEDURE_NAME, ANAESTHESIA_REQ, HOSPITALIZATION_REQ, PROC_COST) VALUES (501, 'spay/neuter', 'y', 'y', 79.99);
INSERT INTO med_procedure (PROCEDURE_ID, PROCEDURE_NAME, ANAESTHESIA_REQ, HOSPITALIZATION_REQ, PROC_COST) VALUES (502, 'broken bone', 'y', 'n', 39.99);
INSERT INTO med_procedure (PROCEDURE_ID, PROCEDURE_NAME, ANAESTHESIA_REQ, HOSPITALIZATION_REQ, PROC_COST) VALUES (503, 'tooth extraction', 'y', 'n', 29.99);
INSERT INTO med_procedure (PROCEDURE_ID, PROCEDURE_NAME, ANAESTHESIA_REQ, HOSPITALIZATION_REQ, PROC_COST) VALUES (504, 'checkup', 'n', 'n', 49.99);
INSERT INTO med_procedure (PROCEDURE_ID, PROCEDURE_NAME, ANAESTHESIA_REQ, HOSPITALIZATION_REQ, PROC_COST) VALUES (505, 'flea bath', 'n', 'n', 29.99);
INSERT INTO med_procedure (PROCEDURE_ID, PROCEDURE_NAME, ANAESTHESIA_REQ, HOSPITALIZATION_REQ, PROC_COST) VALUES (506, 'trim nails', 'n', 'n', 19.99);
INSERT INTO med_procedure (PROCEDURE_ID, PROCEDURE_NAME, ANAESTHESIA_REQ, HOSPITALIZATION_REQ, PROC_COST) VALUES (507, 'heartworm', 'n', 'n', 49.99);
INSERT INTO med_procedure (PROCEDURE_ID, PROCEDURE_NAME, ANAESTHESIA_REQ, HOSPITALIZATION_REQ, PROC_COST) VALUES (508, 'surgery', 'y', 'y', 129.99);
INSERT INTO med_procedure (PROCEDURE_ID, PROCEDURE_NAME, ANAESTHESIA_REQ, HOSPITALIZATION_REQ, PROC_COST) VALUES (509, 'bath', 'n', 'n', 9.99);



INSERT INTO VISIT (visit_id, pet_id, visit_date, clinic_id) VALUES (100100, 2000, '25-APR-13', 1);
INSERT INTO VISIT (visit_id, pet_id, visit_date, clinic_id) VALUES (100110, 2000, '03-FEB-13', 1);
INSERT INTO VISIT (visit_id, pet_id, visit_date, clinic_id) VALUES (100150, 2000, '05-MAY-12', 1);
INSERT INTO VISIT (visit_id, pet_id, visit_date, clinic_id) VALUES (100160, 2001, '25-APR-13', 2);
INSERT INTO VISIT (visit_id, pet_id, visit_date, clinic_id) VALUES (100200, 2000, '20-MAY-13', 1);


INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10100,2011,'29-JUL-13',6);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10101,2012,'29-JUL-13',6);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10102,2000,'29-JUL-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10103,2008,'29-JUL-13',6);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10104,2012,'29-JUL-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10105,2010,'29-JUL-13',5);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10106,2001,'29-JUL-13',4);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10107,2012,'02-AUG-13',5);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10108,2009,'02-AUG-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10147,2019,'02-AUG-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10230,2019,'02-AUG-13',1);


INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10109,2011,'02-AUG-13',4);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10110,2007,'02-AUG-13',4);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10111,2009,'02-AUG-13',2);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10112,2000,'02-AUG-13',5);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10113,2011,'02-AUG-13',3);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10114,2008,'02-AUG-13',2);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10115,2011,'02-AUG-13',5);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10116,2018,'02-AUG-13',5);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10117,2015,'02-AUG-13',3);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10118,2011,'02-AUG-13',6);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10119,2014,'02-AUG-13',6);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10120,2018,'03-AUG-13',5);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10121,2005,'03-AUG-13',6);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10122,2003,'15-AUG-13',5);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10123,2006,'15-AUG-13',3);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10124,2000,'15-AUG-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10125,2005,'15-AUG-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10126,2008,'15-AUG-13',4);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10127,2007,'15-AUG-13',2);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10128,2011,'01-SEP-13',2);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10129,2007,'01-SEP-13',2);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10130,2011,'01-SEP-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10131,2008,'01-SEP-13',4);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10132,2013,'01-SEP-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10133,2014,'01-SEP-13',6);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10134,2009,'01-SEP-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10135,2007,'13-SEP-13',3);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10136,2004,'13-SEP-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10137,2010,'13-SEP-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10138,2002,'13-SEP-13',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10139,2002,'13-SEP-13',4);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10140,2009,'13-SEP-13',6);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10141,2008,'05-NOV-14',6);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10142,2006,'19-JUN-14',4);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10143,2005,'07-FEB-15',3);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10144,2017,'27-AUG-14',1);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10145,2008,'03-JUL-13',2);
INSERT INTO visit (visit_id,pet_id,visit_date,clinic_id) VALUES (10146,2004,'02-NOV-14',1);

INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10147,501, 109);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10147,506, 109);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10147,509, 109);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10230,504, 109);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10230,506, 109);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10122,503, 101);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10136,503, 100);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10129,508, 106);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10106,508, 108);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10111,505, 100);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10113,505, 108);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10120,507, 106);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10105,501, 111);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10103,505, 105);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10127,502, 104);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10118,508, 111);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10100,508, 111);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10109,505, 109);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10135,501, 106);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10116,501, 106);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10114,503, 110);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10119,506, 104);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10126,506, 110);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10135,502, 106);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10129,503, 106);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10139,501, 103);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10111,508, 100);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10111,502, 100);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10138,506, 110);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10112,505, 112);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10107,508, 110);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10122,502, 107);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10125,501, 110);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10137,508, 107);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10115,507, 111);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10139,502, 103);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10121,505, 103);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10108,504, 102);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10137,507, 101);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10102,502, 113);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10120,506, 106);
INSERT INTO visit_procedure (visit_id,procedure_id, vet_id) VALUES (10138,501, 110);


INSERT INTO prescription (script_id,drug_id,comments) VALUES (100,'Zetia','take three, four times a day');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (101,'Cyclobenzaprin HCl','take with water on empty stomach');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (102,'Fluconazole','take once a day');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (103,'Amoxicillin Trihydrate','take for three weeks');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (104,'Risperidone','take 10 a day');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (105,'Furosemide','take with dog food');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (106,'Simvastatin','take with cat food');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (107,'Tri-Sprintec','take 5 times a day');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (108,'Metoprolol Succinatee','take on an empty stomach');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (109,'Warfarin Sodium','take with food and water');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (110,'Famotidine','take with food and water');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (111,'Amlodipine Besylate','take 5 times a day');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (112,'Seroquel','take 5 times a day');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (113,'Amlodipine Besylate','take 1 a day, for three weeks');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (114,'Carisoprodol','take 3 times a day');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (115,'Zyprexa','take twice a day for two months');
INSERT INTO prescription (script_id,drug_id,comments) VALUES (116,'Amoxicillin','take on empty stomach');


INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (113,10100, 111);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (101,10103, 105);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (108,10119, 104);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (101,10106, 108);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (104,10126, 110);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (103,10135, 106);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (103,10122, 107);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (108,10138, 110);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (114,10138, 110);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (100,10136, 107);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (109,10136, 107);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (104,10125, 110);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (115,10230, 109);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (116,10230, 109);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (116,10127, 104);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (112,10127, 104);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (111,10127, 104);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (116,10137, 107);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (110,10137, 107);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (101,10137, 107);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (110,10121, 103);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (108,10121, 103);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (107,10121, 103);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (106,10120, 106);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (116,10120, 106);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (102,10135, 106);
INSERT INTO visit_prescription (script_id,visit_id, vet_id) VALUES (111,10135, 106);




INSERT INTO store_item (item_id, item_name, description, item_cost) VALUES (1,'Doggie Bone','rawhide dog bone', 7.99);
INSERT INTO store_item (item_id, item_name, description, item_cost) VALUES (2,'CCC Spray','carpet cleaning spray', 12.99);
INSERT INTO store_item (item_id, item_name, description, item_cost) VALUES (3,'Crazy Cats','cat toy', 3.99);
INSERT INTO store_item (item_id, item_name, description, item_cost) VALUES (4,'Sneeze BGone','anti-allergen spray', 11.99);
INSERT INTO store_item (item_id, item_name, description, item_cost) VALUES (5,'Cat Nips','fun treat', 4.99);
INSERT INTO store_item (item_id, item_name, description, item_cost) VALUES (6,'Turkey Toy','dog toy', 11.99);
INSERT INTO store_item (item_id, item_name, description, item_cost) VALUES (7,'Cat Bed','bed for cats', 39.99);
INSERT INTO store_item (item_id, item_name, description, item_cost) VALUES (8,'Cat Tube','anti-allergen spray', 14.99);
INSERT INTO store_item (item_id, item_name, description, item_cost) VALUES (9,'Softee Squeezee ','dog toy', 11.99);
INSERT INTO store_item (item_id, item_name, description, item_cost) VALUES (10,'Feather toy','cat toy', 2.99);


INSERT INTO pet_food (food_id, food_name, species, designation) VALUES (1000,'Xtra Health','dogs', 'for older dogs');
INSERT INTO pet_food (food_id, food_name, species, designation) VALUES (2000,'Diet Cat Food','cats', 'for fat cats');
INSERT INTO pet_food (food_id, food_name, species, designation) VALUES (4000,'Krunchy Treats','rabbits', 'rabbit pellets');
INSERT INTO pet_food (food_id, food_name, species, designation) VALUES (4010,'Farm Hay','farm', 'hay for farm animals');
INSERT INTO pet_food (food_id, food_name, species, designation) VALUES (1010,'Growing Strong','dogs', 'for puppies');
INSERT INTO pet_food (food_id, food_name, species, designation) VALUES (1020,'Joint Health','dogs', 'for older dogs');
INSERT INTO pet_food (food_id, food_name, species, designation) VALUES (2001,'Kitten Food','cats', 'for kittens');



INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (1,5,10,1,'18-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (2,2,1,2,'24-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (3,1,1,1,'15-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (4,2,8,4,'23-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (5,6,6,3,'27-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (6,4,2,3,'02-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (7,3,2,2,'19-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (8,2,9,2,'22-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (9,5,1,1,'28-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (10,3,9,2,'04-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (11,5,10,4,'05-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (12,5,8,2,'25-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (13,3,9,3,'26-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (14,1,3,1,'20-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (15,4,7,3,'02-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (16,2,1,1,'03-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (17,6,1,1,'21-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (18,3,4,4,'03-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (19,4,1,2,'24-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (20,6,6,4,'06-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (21,6,7,3,'29-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (22,6,4,2,'01-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (23,6,7,1,'25-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (24,6,1,1,'27-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (25,6,1,1,'04-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (26,3,2,1,'02-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (27,5,9,4,'22-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (28,5,10,3,'25-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (29,1,3,1,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (30,6,5,3,'20-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (31,6,6,3,'01-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (32,1,5,3,'26-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (33,3,7,3,'15-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (34,1,4,4,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (35,6,4,1,'22-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (36,3,5,2,'06-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (37,3,9,3,'07-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (38,2,9,2,'01-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (39,4,5,4,'11-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (40,6,6,1,'30-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (41,6,8,4,'25-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (42,3,6,4,'28-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (43,5,1,1,'05-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (44,4,3,4,'27-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (45,4,6,1,'02-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (46,3,9,3,'14-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (47,1,4,1,'20-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (48,6,4,3,'14-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (49,5,10,2,'27-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (50,2,5,1,'24-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (51,5,4,1,'24-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (52,1,2,3,'24-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (53,4,8,4,'24-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (54,3,1,2,'24-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (55,6,2,3,'24-AUG-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (56,3,2,1,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (57,4,10,4,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (58,5,6,1,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (59,6,5,4,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (60,4,2,1,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (61,1,10,2,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (62,4,2,1,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (63,5,7,3,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (64,4,7,4,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (65,5,5,3,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (66,3,7,2,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (67,6,5,3,'01-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (68,4,3,3,'09-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (69,6,8,2,'09-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (70,4,6,1,'09-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (71,2,2,4,'09-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (72,1,7,1,'09-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (73,4,6,2,'09-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (74,5,10,2,'09-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (75,2,3,3,'09-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (76,2,6,3,'9-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (77,1,7,3,'13-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (78,3,4,2,'13-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (79,4,8,4,'13-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (80,5,8,2,'13-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (81,4,2,4,'13-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (82,4,8,4,'13-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (83,5,8,2,'13-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (84,4,2,4,'13-SEP-13');

INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (85,3,1,2,'25-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (86,4,2,4,'25-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (87,5,1,2,'25-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (88,4,1,4,'25-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (89,4,2,4,'25-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (90,5,1,2,'25-SEP-13');
INSERT INTO store_trans (trans_id,clinic_id,item_id,quantity,trans_date) VALUES (91,4,2,4,'25-SEP-13');



INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (51,10131,56);
INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (52,10131,57);
INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (53,10131,58);

INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (54,10132,61);
INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (55,10132,62);
INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (56,10132,63);


INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (57,10133,64);
INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (58,10133,65);
INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (59,10133,66);

INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (60,10128,29);
INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (61,10129,34);
INSERT INTO guardian_purchase (purch_id, visit_id,trans_id) VALUES (62,10134,67);
