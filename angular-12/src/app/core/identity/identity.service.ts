import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { User } from "../user/user.type";

@Injectable({
  providedIn: "root",
})
export class IdentityService {

  // Object to store data of identity service
  private store: {
    identity: User | null;
  } = {
    identity: null,
  };

  get identity(): User | null { return this.store.identity; }
  set identity(value: User | null) {
    //store user object and send to subscriber
    this.store.identity = value;
    this._identity.next(value);
  }

  // Subject and Observer
  private _identity = new BehaviorSubject<User | null>(this.store.identity);
  public readonly identity$ = this._identity.asObservable();

  constructor() {}

}
