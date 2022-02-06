import { IdentityService } from './../../core/identity/identity.service';
import { Component, AfterViewInit, OnInit } from '@angular/core';
import { ROUTESSTUDENT, ROUTESTEACHER } from './menu-items';
import { RouteInfo } from './sidebar.metadata';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
//declare var $: any;

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent implements OnInit {
  showMenu = '';
  showSubMenu = '';
  public sidebarnavItems:RouteInfo[]=[];
  // this is for the open close
  addExpandClass(element: string) {
    if (element === this.showMenu) {
      this.showMenu = '0';
    } else {
      this.showMenu = element;
    }
  }

  constructor(
    private modalService: NgbModal,
    private router: Router,
    private route: ActivatedRoute,
    private identityService :IdentityService
  ) {}

  // End open close
  ngOnInit() {
    this.identityService.identity$.subscribe(user => {
      if(user!.role === "TEACHER"){
        this.sidebarnavItems = ROUTESTEACHER.filter(sidebarnavItem => sidebarnavItem);
      }
      else if (user!.role === "STUDENT"){
        this.sidebarnavItems = ROUTESSTUDENT.filter(sidebarnavItem => sidebarnavItem);
      }
    })
  }
}
