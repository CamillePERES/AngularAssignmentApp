import { ChangeDetectorRef, Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { WorkService } from 'src/app/core/works/work.service';


import { Work, WorkCreateForm, WorkUpdateForm } from 'src/app/core/works/work.type';

enum WorkItemMode {
  CREATE,
  UPDATE,
  VIEW
}

export interface WorkItem {
  work: Work | null;
  viewMode: boolean;
}

@Component({
  selector: 'app-work-item',
  templateUrl: './work-item.component.html',
  styleUrls: ['./work-item.component.css']
})
export class WorkItemComponent implements OnInit {

  @Input('work') work: WorkItem | null = null;

  get workStudent() {
    return this.work !== null && this.work.work ? `${this.work.work.user.firstname} ${this.work.work.user.name}` : '';
  }

  get workStatus() {
    return this.work !== null && this.work.work ? this.work.work.status : '';
  }

  get workDelivery() {
    return this.work !== null && this.work.work ? this.work.work.deliverydate : '';
  }

  //@Output() workChange = new EventEmitter<Work | null>();
  @Output() submitcreate = new EventEmitter<WorkCreateForm>();
  @Output() submitevaluation = new EventEmitter<WorkUpdateForm>();

  public form: FormGroup | null = null
  public mode: WorkItemMode = WorkItemMode.VIEW

  get nameDisabled(){ return this.mode !== WorkItemMode.CREATE }

  constructor(
    private formBuilder: FormBuilder,
  ) {

  }

  ngOnInit(): void {
    this.refreshState();
  }

  public submitCreate(){

    if(this.form === null || this.mode !== WorkItemMode.CREATE)
      return;

    this.form.disable();
    const raw = this.form.getRawValue();
    this.submitcreate.emit({ name: raw.name, description: raw.description, idAss: 0});
  }

  public submitUpdate(){

    if(this.form === null || this.mode !== WorkItemMode.UPDATE)
      return;

    this.form.disable();
    const raw = this.form.getRawValue();
    this.submitevaluation.emit({ grade: raw.grade, comment: raw.comment, idWork: raw.id});
  }

  private refreshState(){

    if(this.work === null){
      this.mode = WorkItemMode.CREATE;
      this.form = this.updateForm(null);
      return;
    }

    if(this.work.viewMode){
      this.mode = WorkItemMode.VIEW;
    }
    else if(this.work.work === null){
      this.mode = WorkItemMode.CREATE;
    }
    else if(this.work.work.status === 'Submitted') {
      this.mode = WorkItemMode.UPDATE;
    }
    else {
      this.mode = WorkItemMode.VIEW;
    }

    this.form = this.updateForm(this.work.work);
    console.log(this.form)
  }

  private updateForm(work: Work | null): FormGroup {
    const state = this.getStateForm();
    console.log(state)

    let id = work ? work.idwork : 0;
    let name = work ? work.name : '';
    let description = work ?work.description : '';
    let grade = work ? work.grade : 0;
    let comment = work ? work.comment : '';

    return this.formBuilder.group({
      id: [{value: id, disabled: true}, Validators.required],
      name: [{value: name, disabled: state[0]}, Validators.required],
      description: [{value: description, disabled: state[1]}, Validators.required],
      grade: [{value: grade, disabled: state[2]}, Validators.required],
      comment: [{value: comment, disabled: state[3]}, Validators.required]
    });
  }

  private getStateForm(): Array<boolean>{
    if (this.mode === WorkItemMode.CREATE){
      return [false, false, true, true];
    }
    else if (this.mode === WorkItemMode.UPDATE) {
      return [true, true, false, false];
    }
    else {
      return [true, true, true, true];
    }
  }

}
