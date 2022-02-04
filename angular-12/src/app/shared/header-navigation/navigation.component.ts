import { UserService } from './../../core/user/user.service';
import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
//declare var $: any;

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html'
})
export class NavigationComponent {
  @Output()
  toggleSidebar = new EventEmitter<void>();

  public showSearch = false;

  constructor(private userService : UserService, private router: Router) {}

  async logOut(){
    try {
      await this.userService.logOut();
      this.router.navigate(['/signin']);
    } catch (error) {

    }
  }
}
