import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from "./../../core/user/user.service";
import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators, NgForm } from "@angular/forms";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: "app-signin",
  templateUrl: "./signin.component.html",
  styleUrls: ["./signin.component.css"],
})

export class SigninComponent implements OnInit {

  signInForm: FormGroup;
  isEnable: boolean = true;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router,
    protected toast: ToastrService,
    private activatedRoute: ActivatedRoute,
  ) {

    //Creer le form de signIn
    this.signInForm = this.formBuilder.group({
      username: ["", Validators.required],
      password: ["", Validators.required],
    });
  }

  ngOnInit(): void {

  }

  async signIn() {

    if (this.signInForm.invalid){
      console.log("error");
      return;
    }

    this.signInForm.disable();

    try {

      await this.userService.loginAsync({ login: this.signInForm.value.username, password: this.signInForm.value.password});

      this.toast.success("Login successfull");

      const redirectURL = this.activatedRoute.snapshot.queryParamMap.get('redirectURL') || '/signed-in-redirect';
      this.router.navigateByUrl(redirectURL);

    } catch (error) {
      this.toast.error("Invalid credientials");
      this.signInForm.reset();
      this.signInForm.enable();
    }
  }
}
