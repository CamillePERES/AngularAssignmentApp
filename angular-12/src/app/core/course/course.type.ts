import { Assignment } from "../assignments/assignment.type";
import { User } from "../user/user.type";

export interface Course{
  idcourse: number;
  name: string;
  description: string;
  assignment: Array<Assignment>;
  user: User;
}

export interface CourseForm{
  name: string;
  description: string;
}

export interface CourseSearchForm {
  page: number;
  pageSize: number;
  courseName: string;
  userName: string;
}

export interface CourseFormUpdate{
  name: string;
  description: string;
}
