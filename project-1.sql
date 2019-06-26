CREATE TABLE employee (
EmployeeID INTEGER NOT NULL,
Login VARCHAR(30) NOT NULL UNIQUE,
Pass VARCHAR(40) NOT NULL,
FullName VARCHAR(50) NOT NULL,
Email VARCHAR(50),
PRIMARY KEY (EmployeeID)
);

CREATE TABLE reportsTo (
EmployeeID INTEGER NOT NULL,
ManagerID INTEGER NOT NULL,
CONSTRAINT reportsto_pk PRIMARY KEY (EmployeeID, ManagerID),
CONSTRAINT employee_fk FOREIGN KEY (EmployeeID) REFERENCES employee(EmployeeID),
CONSTRAINT manager_fk FOREIGN KEY (ManagerID) REFERENCES employee(EmployeeID)
);

CREATE TABLE reimbursement (
ReimbursementID INTEGER NOT NULL,
EmployeeID INTEGER NOT NULL,
Amount INTEGER NOT NULL,
Accepted NUMBER(1,0),
ResolvedBy INTEGER,
CONSTRAINT reimb_pk PRIMARY KEY (ReimbursementID),
CONSTRAINT reimb_emp_fk FOREIGN KEY (EmployeeID) REFERENCES employee(EmployeeID),
CONSTRAINT reimb_res_fk FOREIGN KEY (ResolvedBy) REFERENCES employee(EmployeeID)
);

ALTER TABLE reimbursement ADD descript VarChar(200);

CREATE SEQUENCE employee_id_inc START WITH 1;
CREATE SEQUENCE reimb_id_inc START WITH 1;

CREATE OR REPLACE TRIGGER TG_employee_inc
BEFORE INSERT ON employee
FOR EACH ROW
BEGIN
    SELECT employee_id_inc.NEXTVAL
    INTO :NEW.EmployeeID
    FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER TG_reimb_inc
BEFORE INSERT ON reimbursement
FOR EACH ROW
BEGIN
    SELECT reimb_id_inc.NEXTVAL
    INTO :NEW.ReimbursementID
    FROM DUAL;
END;
/

INSERT INTO employee(Login, Pass, FullName, Email) VALUES ('ABC', 'PASS1', 'NAME1', 'EMAIL1@mail.com');
INSERT INTO employee(Login, Pass, FullName, Email) VALUES ('DEF', 'PASS2', 'NAME2', 'EMAIL2@mail.com');
INSERT INTO employee(Login, Pass, FullName, Email) VALUES ('GHI', 'PASS3', 'NAME3', 'EMAIL3@mail.com');
INSERT INTO employee(Login, Pass, FullName, Email) VALUES ('JKL', 'PASS4', 'NAME4', 'EMAIL4@mail.com');

INSERT INTO reportsTo(EmployeeID, ManagerID) VALUES (2, 1);
INSERT INTO reportsTo(EmployeeID, ManagerID) VALUES (3, 1);
INSERT INTO reportsTo(EmployeeID, ManagerID) VALUES (4, 1);
INSERT INTO reportsTo(EmployeeID, ManagerID) VALUES (4, 2);

INSERT INTO reimbursement(EmployeeID, Amount, Accepted, ResolvedBy) VALUES (2, 500, 1, 1);
INSERT INTO reimbursement(EmployeeID, Amount, Accepted, ResolvedBy) VALUES (3, 400, 0, 1);
INSERT INTO reimbursement(EmployeeID, Amount, Accepted, ResolvedBy) VALUES (4, 500, 0, NULL);
INSERT INTO reimbursement(EmployeeID, Amount, Accepted, ResolvedBy) VALUES (4, 500, 1, 2);

commit;
exit;

SELECT * FROM employee;
SELECT * FROM reportsTo;
SELECT * FROM reimbursement;