import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: "root",
})
export class ImageHelper {

  public getBase64(file: File): Promise<string | ArrayBuffer | null> {
    const reader = new FileReader();
    return new Promise((resolve) => {
      reader.onload = (ev: ProgressEvent<FileReader>): void => ev != null && ev.target != null ? resolve(ev.target.result) : resolve(null);
      reader.readAsDataURL(file);
    });
  }

  public sourceImageCourse(idpicture: number | null) {
    return idpicture ? `${environment.apiBaseUrl}/courses/pic/${idpicture}` : 'assets/images/big/img1.jpg';
  }

  public sourceImageUser(idpicture: number | null) {
    return idpicture ? `${environment.apiBaseUrl}/users/pic/${idpicture}` : 'assets/images/users/1.jpg';
  }
}
