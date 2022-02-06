import { AssignmentService } from 'src/app/core/assignments/assignment.service';
import { recentComments } from './../../../dashboard/dashboard-components/recent-comments/recent-comments-data';
import { CourseService } from 'src/app/core/course/course.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Assignment } from 'src/app/core/assignments/assignment.type';
import { Course } from 'src/app/core/course/course.type';
import { ToastrService } from 'ngx-toastr';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {MatTableDataSource} from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { IdentityService } from 'src/app/core/identity/identity.service';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { User } from 'src/app/core/user/user.type';

@Component({
  selector: 'app-course-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  private _unsubscribeAll: Subject<any> = new Subject<any>();
  private user: User | null = null;
  public course: Course | null = null;
  public assignments: Array<Assignment> = [];
  public editMode: boolean = false;
  public assignment: Assignment | null = null;
  public editForm: FormGroup | null = null;
  editAssignmentForm: FormGroup | null = null;
  displayedColumns: string[] = ['name', 'description', 'date','status', 'details'];
  dataSource = new MatTableDataSource<Assignment>(this.assignments);

  // creation
  public createForm: FormGroup | null = null;
  private modal : NgbModalRef | null = null;

  get isOwner() { return this.user !== null && this.course !== null && this.user.iduser === this.course.user.iduser}

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private courseService: CourseService,
    protected toast: ToastrService,
    private assignmentService: AssignmentService,
    private modalService: NgbModal,
    private router: Router,
    private identityService: IdentityService
    ) {

   }

  ngOnInit(): void {

    this.identityService.identity$
    .pipe(takeUntil(this._unsubscribeAll))
    .subscribe(user => this.user = user);

    this.course = this.route.snapshot.data.initialData.course;
    this.assignments = this.route.snapshot.data.initialData.assignments;
    this. dataSource = new MatTableDataSource<Assignment>(this.assignments);
    console.log(this.assignments)
  }

  public goToEdit(){
    this.editMode = true;

    this.editForm = this.formBuilder.group({
      name: [this.course?.name, Validators.required],
      description: [this.course?.description, Validators.required],
    });
  }

  public async saveUpdate(){
    this.editMode = false;

    try{
      this.course = await this.courseService.updateCourse({
        idCourse: this.course!.idcourse,
        name: this.editForm!.value.name,
        description: this.editForm!.value.description});
    }catch(error){
      this.toast.error("Update failed");
    }
    this.editForm = null;
  }

  public openVerticallyCentered(content: NgbModal): void {
    this.createForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      date: ['', Validators.required]
    });
    this.modal = this.modalService.open(content, { centered: true, backdrop : 'static', keyboard : false});
  }

  public closeModal(): void {
    if(this.modal != null){
      this.modal.close();
      this.modal = null;
    }
  }

  public async createAssignment(): Promise<void> {

    if(this.createForm == null || this.createForm.invalid)
      return;

    this.createForm.disable();

    const value = this.createForm.getRawValue();
    const result = await this.assignmentService.createAssignmentAsync({
      courseId: this.course!.idcourse,
      name: value.name,
      description: value.description,
      date: new Date(`${value.date.year}-${value.date.month}-${value.date.day}`)
    });

    if(result == null){
      this.createForm.enable();
      return;
    }

    this.closeModal();
    //this.details(result);
    this.refreshAssignments();
  }

  public viewDetailsOfTheSelectedAssignment(ass: Assignment){
    this.router.navigate([`/assignment/details/${ass.idass}`]);
  }

  private refreshAssignments(){
    if(this.course === null){
      return;
    }
    this.assignmentService.getAssignmentsOfCourse(this.course.idcourse)
      .subscribe(result => {
        this.assignments = result;
        this. dataSource = new MatTableDataSource<Assignment>(this.assignments);
      });
  }

}
