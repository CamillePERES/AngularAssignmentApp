import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { environment } from "src/environments/environment";
import { LoginResult, LoginForm, User } from "./user.type";

@Injectable({
  providedIn: 'root'
})
export class UserService
{

  key: string = "token";

  constructor(private http: HttpClient, protected toast: ToastrService)
  {

  }

  public async loginAsync(form: LoginForm) : Promise<void>{
    try{
      const result = await this.http.post<LoginResult>(`${environment.apiBaseUrl}/users/signin`, form).toPromise();

      if(result === undefined){
        console.log("nul");
        return;
      }

      this.setLocalStorage('loginresult', result);
      this.toast.success("Login successfull");
    }
    catch(error: any){
        this.toast.error("Invalid credentials","Login");
    }
  }


  public async signupAsync(user: User) : Promise<void>{
    //Promise<User>, pq on ne renvoie pas un user mais une void?
    try{
      const result = this.http.post<User>(`${environment.apiBaseUrl}/users/signup`, user).toPromise();
      this.setLocalStorage('user', result);
    }
    catch(error:any){
      this.toast.error("Account not created","Register");
    }

  }

  public async logOut(): Promise<void>{
    this.removeFromLocalStorage(this.key);
  }

  public login(form: LoginForm) : void{
      this.http.post<LoginResult>(`${environment.apiBaseUrl}/users/signin`, form)
        .subscribe(
          (result: LoginResult) => {
            this.setLocalStorage('loginresult', result);
          },
          (error: any) => {
            this.toast.error("Invalid credentials","Login");
          }
        );
  }

  public getAuthentication(): LoginResult | null{
    return this.getLocalStorage<LoginResult>(this.key);
  }

  private getLocalStorage<T>(key: string): T | null{
    const json = localStorage.getItem(key);

    if(json === null){
      return null;
    }

    try {
      //try parse json
      const parse = JSON.parse(json);
      //try cast json object to object T
      return parse as T;
    } catch (error) {
      //on fail parse or cast
      console.error('Error when try to parse or cast local storage object');
      return null;
    }
  }

  private setLocalStorage<T>(key: string, obj: T): void {
    localStorage.setItem(key, JSON.stringify(obj));
  }

  private removeFromLocalStorage(key: string): void {
    localStorage.removeItem(key)
  }
}
