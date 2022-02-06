import { NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { AssignmentService } from 'src/app/core/assignments/assignment.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Assignment } from 'src/app/core/assignments/assignment.type';
import { IdentityService } from 'src/app/core/identity/identity.service';

import { User } from 'src/app/core/user/user.type';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-assignments-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit, OnDestroy {

  private _unsubscribeAll: Subject<any> = new Subject<any>();
  public editAssignmentForm: FormGroup | null = null;
  public editMode: boolean = false;
  public isChecked = true;

  public user: User | null = null;
  public assignment: Assignment | null = null;

  get isStudent() { return this.user != null && this.user.role === 'STUDENT' }
  get isTeacher() { return this.user != null && this.user.role === 'TEACHER' }

  constructor(
    private route: ActivatedRoute,
    private identityService: IdentityService,
    private toast: ToastrService,
    private formBuilder: FormBuilder,
    private assignmnentService: AssignmentService
  ) {
    this.editAssignmentForm = this.formBuilder.group({
      name:['', Validators.required],
      description:['', Validators.required],
      date:['', Validators.required]
    });
   }

  ngOnDestroy(): void {
    this._unsubscribeAll.next(null);
    this._unsubscribeAll.complete();
  }

  ngOnInit(): void {
    this.assignment = this.route.snapshot.data.initialData;
    console.log(this.assignment)
    this.identityService.identity$
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(user => this.user = user);
  }

   public editAssignment(){

    if(this.assignment === null){
      return;
    }
      this.editMode = true;

      const date = new Date(this.assignment.date);
      console.log(date)
      let datef = {  year: date.getFullYear(),
        month: date.getMonth() + 1,
        day: date.getDate()};

      this.editAssignmentForm = this.formBuilder.group({
        name:[this.assignment.name, Validators.required],
        description:[this.assignment.description, Validators.required],
        date:[datef, Validators.required],
        isclosed: [this.assignment.isclosed, Validators.required]
      });
  }

  public async saveAssignmentUpdate(){
      this.editMode = false;
      try{
        const value = this.editAssignmentForm!.getRawValue();
        this.assignment = await this.assignmnentService.updateAssignment({
          idAss: this.assignment!.idass,
          name:this.editAssignmentForm!.value.name,
          description: this.editAssignmentForm!.value.description,
          date: new Date(`${value.date.year}-${value.date.month}-${value.date.day}`),
          isclosed : this.assignment!.isclosed
        })

      }catch(error){
        this.toast.error("Update failed");
      }
      this.editAssignmentForm = null;
  }

  public onChange(){
    console.log(this.assignment!.isclosed)
    this.assignment!.isclosed = !this.assignment!.isclosed;
  }


}
