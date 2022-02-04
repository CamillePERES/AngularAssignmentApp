import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { environment } from "src/environments/environment";
import { BaseApiService } from "../base/baseapi.service";
import { IdentityService } from "../identity/identity.service";
import { LoginResult, LoginForm, User } from "./user.type";

@Injectable({
  providedIn: 'root'
})
export class UserService extends BaseApiService
{

  private key: string = "token";

  constructor(
    http: HttpClient,
    toast: ToastrService,
    private identityService: IdentityService
    )
  {
    super(http, toast);
  }

  public async loginAsync(form: LoginForm) : Promise<void>{

    const result = await this.tryPost<LoginForm, LoginResult>(`/users/signin`, form).toPromise();

    if(result === undefined){
      return;
    }

    this.setLocalStorage(this.key, result);
    await this.getIdentityAsync();
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
    localStorage.removeItem(key);
    this.identityService.identity = null;
  }

  public async isAuthenticatedAsync(): Promise<boolean> {

    const auth = this.getAuthentication();

    console.log(auth)

    //if no authentication object store, not login
    if (auth === null) {
      return false;
    }

    const currentTime = Date.now();

    //if jwt token exist and expiration is valid
    if (auth.expireAt > currentTime) {
      const identity = this.identityService.identity;
      //if no identity set and is login, get identity from api
      if (identity === null) {
        await this.getIdentityAsync();
      }
      return true;
    }

    //no valid tokens (jwt and refresh) no auth found
    this.removeFromLocalStorage(this.key);
    return false;
  }

  private async getIdentityAsync(): Promise<void> {
    this.identityService.identity = await this.http.get<User>(`${environment.apiBaseUrl}/users/me`).toPromise();
  }
}
