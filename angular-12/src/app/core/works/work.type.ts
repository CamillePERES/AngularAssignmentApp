import { Assignment } from 'src/app/core/assignments/assignment.type';
import { User } from '../user/user.type';
export interface Work{
  name: string;
  description: string;
  grade: number;
  comment: string;
  status: string;
  user: User;
  assignment: Assignment
}
