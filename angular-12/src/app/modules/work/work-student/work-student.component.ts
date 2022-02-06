import { Component, Input, OnInit } from '@angular/core';
import { WorkService } from 'src/app/core/works/work.service';
import { Work, WorkCreateForm } from 'src/app/core/works/work.type';

@Component({
  selector: 'app-work-student',
  templateUrl: './work-student.component.html',
  styleUrls: ['./work-student.component.css']
})
export class WorkStudentComponent implements OnInit {

  @Input() idass: number | null = null;
  private _work: Work | null = null;

  get work() { return this._work; }
  set work(value: Work | null) {
    this._work = value;
  }

  constructor(
    private workService: WorkService
  ) { }

  async ngOnInit(): Promise<void> {

    if(this.idass){
      this._work = await this.workService.getWorkOfAssignmentById(this.idass);
    }


  }

  public submitCreate(value: WorkCreateForm): void {
    console.log(value)
  }

}
