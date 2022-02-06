import { IdentityService } from './../../../core/identity/identity.service';
import { WorkService } from './../../../core/works/work.service';
import { Component, OnInit } from '@angular/core';
import { Work } from 'src/app/core/works/work.type';
import { User } from 'src/app/core/user/user.type';

@Component({
  selector: 'app-my-work-student',
  templateUrl: './my-work-student.component.html',
  styleUrls: ['./my-work-student.component.css']
})
export class MyWorkStudentComponent implements OnInit {

  private user: User | null = null;
  public asseva: Array<Work> = []
  public asssub: Array<Work> = []

  constructor(private workService: WorkService, private identityService: IdentityService) { }

  ngOnInit(): void {
    this.identityService.identity$
    .subscribe(user => {
      this.user = user;
      this.workService.getWorkOfUserById(this.user!.iduser).subscribe(result => {
          this.asseva = result.filter(wk => wk.status === "Evaluated")
          this.asssub = result.filter(wk => wk.status === "Submitted");
      });

    });

  }

}
