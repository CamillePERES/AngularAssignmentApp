import { Component, Input, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';
import { Assignment } from 'src/app/core/assignments/assignment.type';
import { PaginationResult } from 'src/app/core/core.types';
import { WorkService } from 'src/app/core/works/work.service';
import { Work, WorkSearchForm, WorkUpdateForm } from 'src/app/core/works/work.type';
import { WorkItem } from '../work-item/work-item.component';

const FILTER_PAG_REGEX = /[^0-9]/g;

@Component({
  selector: 'app-work-professor',
  templateUrl: './work-professor.component.html',
  styleUrls: ['./work-professor.component.css']
})
export class WorkProfessorComponent implements OnInit {

  private _ass: Assignment | null = null;
  @Input('idass') set idass(value: Assignment | null) {
    this._ass = value;
    console.log(this._ass)
    if(this._ass){
      this.getWorks();
    }
  }

  public asseva: Array<Work> = []
  public asssub: Array<Work> = []

  constructor(
    private service: WorkService
  ) { }

  ngOnInit(): void {
  }

  public buildWork(value: Work): WorkItem {
    return { work: Object.assign({}, value), viewMode: false };
  }

  private getWorks(): void {

    if(this._ass === null)
      return;

    forkJoin([
      this.service.searchWork({ idAss: this._ass.idass, status: 'Submitted'}),
      this.service.searchWork({ idAss: this._ass.idass, status: 'Evaluated'})
    ]).pipe(map(resp => { return { sub: resp[0], eva: resp[1] } }))
    .subscribe(result => {
      this.asssub = result.sub;
      this.asseva = result.eva;
    });
  }

  public async submitEvaluation(value: WorkUpdateForm): Promise<void> {

    if(this.idass === null)
      return;

    try{
      const result = await this.service.updateWork(value);
      console.log(result)
      this.getWorks();
    }
    catch(error){
      console.log(error)
    }
  }


}
