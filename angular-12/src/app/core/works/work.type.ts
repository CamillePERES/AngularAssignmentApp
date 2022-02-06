import { Assignment } from 'src/app/core/assignments/assignment.type';
import { User } from '../user/user.type';

export interface Work{
  idwork: number;
  name: string;
  description: string;
  grade: number;
  comment: string;
  status: string;
  user: User;
  deliverydate : Date;
  assignment: Assignment
}

export interface WorkCreateForm
{
  idAss: number;
  name: string;
  description: string;
}

export interface WorkUpdateForm
{
  idWork: number;
  grade: number;
  comment: string;
}

export interface WorkSearchForm{
  idAss: number;
  status: string;
}
