import { Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { UserService } from './../../core/user/user.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpForm: FormGroup;

  constructor(private userService: UserService, private formBuilder: FormBuilder, protected toast: ToastrService, private router: Router) {

    this.signUpForm = this.formBuilder.group({
      firstname: ["", [Validators.required]],
      name: ["", [Validators.required]],
      username: ["", [Validators.required]],
      password: ["", [Validators.required]],
      role: ["", [Validators.required]],
    });
  }

  ngOnInit(): void {


  }

  async signUp() {

    if (this.signUpForm.invalid){
      console.log("error");
      return;
    }

    this.signUpForm.disable();

    try {

      await this.userService.createUserAsync({
        name: this.signUpForm.value.name,
        firstname:this.signUpForm.value.firstname,
        login: this.signUpForm.value.username,
        password: this.signUpForm.value.password,
        role: this.signUpForm.value.role});

      this.toast.success("Accound creation successfull");

      this.router.navigate(["/signin"]);

    } catch (error) {
      this.toast.error("Invalid account creation");
      this.signUpForm.reset();
      this.signUpForm.enable();
    }
  }

}
