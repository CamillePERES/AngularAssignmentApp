import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { WorkService } from 'src/app/core/works/work.service';


import { Work, WorkCreateForm } from 'src/app/core/works/work.type';

enum WorkItemMode {
  CREATE,
  UPDATE,
  VIEW
}

@Component({
  selector: 'app-work-item',
  templateUrl: './work-item.component.html',
  styleUrls: ['./work-item.component.css']
})
export class WorkItemComponent implements OnInit {

  @Input() viewMode: boolean = false
  @Input() work: Work | null = null
  @Output() workChange = new EventEmitter<Work | null>();
  @Output() submitcreate = new EventEmitter<WorkCreateForm>();

  public form: FormGroup
  public mode: WorkItemMode = WorkItemMode.VIEW

  get nameDisabled(){ return this.mode !== WorkItemMode.CREATE }

  constructor(
    private formBuilder: FormBuilder,
    private service: WorkService
  ) {
    this.form = this.updateForm();
  }

  ngOnInit(): void {

    this.mode = this.viewMode ? WorkItemMode.VIEW : this.work === null ? WorkItemMode.CREATE :
        this.work.status === "Submitted" ? WorkItemMode.UPDATE : WorkItemMode.VIEW;

    this.form = this.updateForm();
    console.log('WORK ITEM MODE', this.mode)
  }

  public submitCreate(){

    if(this.mode !== WorkItemMode.CREATE)
      return;

    this.form.disable();
    const raw = this.form.getRawValue();
    this.submitcreate.emit({ name: raw.name, description: raw.description });
  }

  private updateForm(): FormGroup {

    const state = this.getStateForm();

    return this.formBuilder.group({
      name: new FormControl({value:  this.work ? this.work.name : '', disabled: state[0]}, Validators.required),
      description: new FormControl({value: this.work ? this.work.description : '', disabled: state[1]}, Validators.required),
      grade: new FormControl({value: this.work ? this.work.grade : 0, disabled: state[2]}, Validators.required),
      comment: new FormControl({value: this.work ? this.work.comment : '', disabled: state[3]}, Validators.required)
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
