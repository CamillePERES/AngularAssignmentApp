import { Work } from './../../../../core/works/work.type';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-work-student-item',
  templateUrl: './my-work-student-item.component.html',
  styleUrls: ['./my-work-student-item.component.css']
})
export class MyWorkStudentItemComponent implements OnInit {

  constructor(private work: Work) { }

  ngOnInit(): void {
   
  }

}
