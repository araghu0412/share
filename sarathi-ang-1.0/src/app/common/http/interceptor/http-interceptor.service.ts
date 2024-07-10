import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { LogoutStoreService } from '../../store/logout/logout-store.service';
import { UserDetailsStoreService } from '../../store/user-details/user-details-store.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor(
    private userDetailsStoreService: UserDetailsStoreService,
    private logoutStoreService: LogoutStoreService
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let loggedInUserEmailId: string = "";
    let authorizationToken: string = "";
    let request;
    if (req.url.includes("post-login")) {
      loggedInUserEmailId = this.userDetailsStoreService.getUserDetails().email;

      authorizationToken = this.userDetailsStoreService.getTokenDetails().tokenType + " " + this.userDetailsStoreService.getTokenDetails().accessToken;

      let headers = req.headers.set("Authorization", authorizationToken).append("loggedInUserEmailId", loggedInUserEmailId);

      request = req.clone({ url: environment.SECRET_URL + req.url, headers });
    } else {
      request = req.clone({ url: environment.SECRET_URL + req.url});
    }

    return next.handle(request).pipe(tap(event => {
      return event;
    }), catchError(error => {
      if (error.status === 401) {
        this.logoutStoreService.clearStoreAndLogout();
      }
      return throwError(error);
    }));
  }
}
