CREATE TABLE user(
        iduser       Int  Auto_increment  NOT NULL ,
        name      Varchar (500) NOT NULL ,
        firstname   Varchar (500) NOT NULL ,
        login    Varchar (500) NOT NULL ,
        password Varchar (500) NOT NULL ,
        role     Varchar (500) NOT NULL ,
        picture Varchar (64) NOT NULL
	,CONSTRAINT user_PK PRIMARY KEY (iduser)
)ENGINE=InnoDB;

CREATE TABLE course(
        idcourse  Int  Auto_increment  NOT NULL ,
        name Varchar (500) NOT NULL ,
        description Varchar (500) NOT  NULL ,
        iduser Int NOT NULL
	,CONSTRAINT course_PK PRIMARY KEY (idcourse)

	,CONSTRAINT course_user_FK FOREIGN KEY (iduser) REFERENCES user(iduser)
)ENGINE=InnoDB;

CREATE TABLE assignment(
        idass   Int  Auto_increment  NOT NULL ,
        name  Varchar (500) NOT NULL ,
        date Date NOT NULL ,
        description Varchar (500) NOT NULL ,
        idcourse   Int NOT NULL
	,CONSTRAINT assignment_PK PRIMARY KEY (idass)

	,CONSTRAINT assignment_course_FK FOREIGN KEY (idcourse) REFERENCES course(idcourse)
)ENGINE=InnoDB;

CREATE TABLE work(
        idwork      Int  Auto_increment  NOT NULL ,
        name     Varchar (500) NOT NULL ,
        description Varchar (500) NOT NULL,
        grade    Int NOT NULL ,
        comment Varchar (500) NOT NULL ,
        status  Varchar (500) NOT NULL ,
        iduser       Int NOT NULL ,
        idass        Int NOT NULL
	,CONSTRAINT work_PK PRIMARY KEY (idwork)

	,CONSTRAINT work_user_FK FOREIGN KEY (iduser) REFERENCES user(iduser)
	,CONSTRAINT work_assignment0_FK FOREIGN KEY (idass) REFERENCES assignment(idass)
)ENGINE=InnoDB;

create unique index work_iduser_idass_unique
  on work
  (
    iduser
    ,idass
  );

  create unique index user_login_unique
  on user 
  (
    login
  );

  create unique index nomass_idass_idmat_unique
  on assignment
  (
    idass
    ,idmat
  );