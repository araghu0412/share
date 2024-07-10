import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsStoreService {

  userDetailsBehaviorSubject = new BehaviorSubject<any>({});

  tokenDetailsBehaviorSubject = new BehaviorSubject<any>({});

  setUserDetails(userDetailsResponse: any) {
    this.clearUserDetails();

    this.userDetailsBehaviorSubject.next(userDetailsResponse);
  }

  setTokenDetails(tokenDetailsResponse: any) {
    this.clearTokenDetails();

    this.tokenDetailsBehaviorSubject.next(tokenDetailsResponse);
  }

  getUserDetails(): any {
    let userDetails = {};

    this.userDetailsBehaviorSubject.subscribe(userDetailsBehaviorSubject => {
      userDetails = userDetailsBehaviorSubject;
    });

    return userDetails;
  }

  getTokenDetails(): any {
    let tokenDetails = {};

    this.tokenDetailsBehaviorSubject.subscribe(tokenDetailsBehaviorSubject => {
      tokenDetails = tokenDetailsBehaviorSubject;
    })

    return tokenDetails;
  }

  clearUserDetails() {
    this.userDetailsBehaviorSubject = new BehaviorSubject<any>({});
  }

  clearTokenDetails() {
    this.tokenDetailsBehaviorSubject = new BehaviorSubject<any>({});
  }

  constructor() { }
}
