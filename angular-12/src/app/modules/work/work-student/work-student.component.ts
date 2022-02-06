import { Component, Input, OnInit } from '@angular/core';
import { Assignment } from 'src/app/core/assignments/assignment.type';
import { WorkService } from 'src/app/core/works/work.service';

import { Work, WorkCreateForm } from 'src/app/core/works/work.type';
import { WorkItem } from '../work-item/work-item.component';

@Component({
  selector: 'app-work-student',
  templateUrl: './work-student.component.html',
  styleUrls: ['./work-student.component.css']
})
export class WorkStudentComponent implements OnInit {

  @Input() idass: Assignment | null = null;
  public work: WorkItem | null = null;

  constructor(
    private workService: WorkService
  ) { }

  ngOnInit(): void {
    if(this.idass){
      this.workService.getWorkOfAssignmentById(this.idass.idass)
        .subscribe(work => {
          this.work = { work: work, viewMode: work !== null };
            console.log(this.work)

        })
      //console.log('GET WORK STUDENT', this.work)
    }
  }

  public async submitCreate(value: WorkCreateForm): Promise<void> {
    console.log(value)

    if(this.idass === null || this.work === null)
      return;

    try{
      value.idAss = this.idass.idass;
      this.work.work = await this.workService.createWork(value);
      console.log(this.work)
    }
    catch(error){
      console.log(error)
    }
  }

}
