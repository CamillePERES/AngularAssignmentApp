import { Course } from "../course/course.type";
import { Work } from "../works/work.type";

export interface AssignmentForm{
  name: string;
  date: Date;
  description: string;
}
//ajouter id de la matiere?

export interface Assignment{
  idass: number;
  name: string;
  date: Date;
  description: string;
  course: Course;
}
