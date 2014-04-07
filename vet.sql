
DROP TABLE visit_procedure;
DROP TABLE visit_prescription;
DROP TABLE visit;
DROP TABLE procedures;
DROP TABLE patient;
DROP TABLE guardian;
DROP TABLE doctor;
DROP TABLE employee;
DROP TABLE branch;
DROP TABLE prescription;

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


CREATE TABLE branch	
(	
clinic_id NUMBER(1),
clinic_name	VARCHAR2(30),
clinic_address VARCHAR2(20),	
clinic_phone VARCHAR2(10),	
PRIMARY KEY (clinic_id)
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
start_date	DATE DEFAULT SYSDATE,
clinic_id	NUMBER(1),
PRIMARY KEY (emp_id),
CONSTRAINT fk_employee_clinicid FOREIGN KEY (clinic_id)
REFERENCES branch (clinic_id)
);


CREATE TABLE procedures 
(
procedure_id NUMBER(5),
procedure_name VARCHAR2(20),
anaesthesia_req	CHAR(1),
hospitalization_req	CHAR(1),
cost NUMBER(6,2),
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
REFERENCES procedures (procedure_id),
CONSTRAINT fk_vp_vetid FOREIGN KEY (vet_id)
REFERENCES doctor (vet_id)
);

CREATE TABLE prescription	
(
script_id NUMBER(9),
drug_id	VARCHAR2(20),
comments VARCHAR2(20),
PRIMARY KEY (script_id)
);

CREATE TABLE visit_prescription	
(
script_id NUMBER(9),
visit_id NUMBER(9),
PRIMARY KEY (script_id, visit_id),
CONSTRAINT fk_visproc_scriptid FOREIGN KEY (script_id)
REFERENCES prescription (script_id),
CONSTRAINT fk_visproc_visitid FOREIGN KEY (visit_id)
REFERENCES visit (visit_id)
);