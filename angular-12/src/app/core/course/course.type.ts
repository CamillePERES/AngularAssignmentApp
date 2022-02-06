import { Assignment } from "../assignments/assignment.type";
import { User } from "../user/user.type";

export interface Course{
  idcourse: number;
  name: string;
  description: string;
  assignment: Array<Assignment>;
  user: User;
    //idUser;
}

export interface CourseForm{
  name: string;
  description: string;
  //iduser;
}

export interface CourseSearchForm {
  page: number;
  pageSize: number;
  courseName: string;
  userName: string;
}
