import { UserService } from './../../core/user/user.service';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { IdentityService } from 'src/app/core/identity/identity.service';
import { User } from 'src/app/core/user/user.type';
import { ImageHelper } from 'src/app/core/helpers/image.helper';
//declare var $: any;

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html'
})
export class NavigationComponent implements OnInit {

  public user: User | null = null;

  @Output()
  toggleSidebar = new EventEmitter<void>();

  public showSearch = false;

  constructor(
    private userService : UserService,
    private router: Router,
    private identity: IdentityService,
    public imageHelper: ImageHelper
    ) {

  }

  ngOnInit(): void {
    this.identity.identity$.subscribe(user => this.user = user);
  }

  async logOut(){
    try {
      await this.userService.logOut();
      this.router.navigate(['/signin']);
    } catch (error) {

    }
  }
}
