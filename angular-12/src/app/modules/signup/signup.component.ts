import { filter } from 'rxjs/operators';
import { UserService } from './../../core/user/user.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  //@ViewChild('signUpNgForm') signUpNgForm: NgForm;

  signUpForm: FormGroup;

  constructor(private userService: UserService, private formBuilder: FormBuilder) {

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

  async signUp(){
    try {
      //iduser faux, a enlever
      await this.userService.signupAsync({
        iduser:0,
        firstname: this.signUpForm.value.firstname,
        name: this.signUpForm.value.name,
        login: this.signUpForm.value.username,
        //password:this.signUpForm.value.password,
        role:this.signUpForm.value.role
      });

      //bouton disable
    } catch (error) {

    }

  }

}
