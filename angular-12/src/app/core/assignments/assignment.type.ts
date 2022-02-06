import { Course } from "../course/course.type";
import { Work } from "../works/work.type";

export interface AssignmentForm{
  courseId: number;
  name: string;
  date: Date;
  description: string;
}

export interface Assignment{
  idass:number;
  name: string;
  date: Date;
  description: string;
  isclosed: boolean;
  course: Course;
}

export interface AssignmentFormUpdate{
  idAss: number;
  name: string;
  date: Date;
  description: string;
}
