import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Assignment } from 'src/app/core/assignments/assignment.type';
import { IdentityService } from 'src/app/core/identity/identity.service';

import { User } from 'src/app/core/user/user.type';

@Component({
  selector: 'app-assignments-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit, OnDestroy {

  private _unsubscribeAll: Subject<any> = new Subject<any>();

  public user: User | null = null;
  public assignment: Assignment | null = null;

  get isStudent() { return this.user != null && this.user.role === 'STUDENT' }
  get isTeacher() { return this.user != null && this.user.role === 'TEACHER' }

  constructor(
    private route: ActivatedRoute,
    private identityService: IdentityService
  ) { }

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

}
