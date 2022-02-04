import { Assignment } from "../assignments/assignment.type";

export interface Course{
    name: string;
    description: string;
    assignment: Array<Assignment>;
    //idUser;
}

export interface CourseForm{
  name: string;
  description: string;
  //iduser;
}
