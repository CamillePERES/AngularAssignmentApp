import { Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { UserService } from './../../core/user/user.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Picture, ProgressUpload } from 'src/app/core/core.types';
import { ImageHelper } from 'src/app/core/helpers/image.helper';
import { User } from 'src/app/core/user/user.type';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUpForm: FormGroup;
  public image: Picture | null = null;
  public progress: ProgressUpload | null = null;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    protected toast: ToastrService,
    private router: Router,
    private imageHelper: ImageHelper
    ) {

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

      const result = await this.userService.createUserAsync({
        name: this.signUpForm.value.name,
        firstname:this.signUpForm.value.firstname,
        login: this.signUpForm.value.username,
        password: this.signUpForm.value.password,
        role: this.signUpForm.value.role});

      this.toast.success("Accound creation successfull");

      this.uploadImage(result!)

    } catch (error) {
      this.toast.error("Invalid account creation");
      this.signUpForm.reset();
      this.signUpForm.enable();
    }
  }

  async selectFiles(event: any): Promise<void> {
    const selected: FileList = event.target.files;
    let file: File = selected[0];
    if (file.size > 0) {
      const mimeType = file.type;
      if (mimeType.match(/image\/(jpe?g|png|gif|bmp)/) !== null) {
        const buffer = await this.imageHelper.getBase64(file);
        this.image = { file: file, buffer: buffer };
        this.progress = { value: 0, filename: file.name };
      }
    }
  }

  private uploadImage(course: User){
    this.userService.uploadPicture(course.iduser, this.image!.file, (progress: ProgressUpload) => {
      this.progress = progress;
      if (progress.value === 100){
        this.toast.success('Image is uploaded');
        this.router.navigate(["/signin"]);
      }
    })
  }

}
