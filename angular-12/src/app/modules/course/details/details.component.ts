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

@Component({
  selector: 'app-course-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

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


  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private courseService: CourseService,
    protected toast: ToastrService,
    private assignmentService: AssignmentService,
    private modalService: NgbModal,
    private router: Router
    ) {

   }

  ngOnInit(): void {
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
      this.course = await this.courseService.updateCourse({idCourse: this.course!.idcourse, name: this.editForm!.value.name, description: this.editForm!.value.description});
    }catch(error){
      this.toast.error("Update failed");
    }
    this.editForm = null;
  }

  public editAssignment(){
      this.editMode = true;

      this.editAssignmentForm = this.formBuilder.group({
        name:[this.assignment?.name, Validators.required],
        description:[this.assignment?.description, Validators.required],
        date:[this.assignment?.date, Validators.required]
      })
  }

  public async saveAssignmentUpdate(){
      this.editMode = false;
      try{
        this.assignment = await this.assignmentService.updateAssignment({
          idAss: this.assignment!.idass,
          name:this.editAssignmentForm!.value.name,
          description: this.editAssignmentForm?.value.description,
          date: this.editAssignmentForm?.value.date
        })

      }catch(error){
        this.toast.error("Update failed");
      }
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
    const result = await this.assignmentService.createAssignmentAsync(value);

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
