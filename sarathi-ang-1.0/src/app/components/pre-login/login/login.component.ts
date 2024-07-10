import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import services from 'src/assets/services/services.json';
import { dataEncryption } from 'src/app/common/utils/utils';
import { UserDetailsStoreService } from 'src/app/common/store/user-details/user-details-store.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email: string = "";
  password: string = "";

  // Error field flags
  errorFlagEmail: boolean = false;
  errorFlagPassword: boolean = false;

  // Error field messages
  errorMessageEmail: string = "";
  errorMessagePassword: string = "";

  // Error Modal
  showErrorLogin: boolean = false;
  errorMessageLogin: string = "";

  constructor(
    private http: HttpClient,
    private router: Router,
    private userDetailsStoreService: UserDetailsStoreService
  ) { }

  ngOnInit(): void {
  }

  // Incase of the error, change the border from red to original
  inputOnFocus(id: string) {
    (document.getElementById(id) as HTMLElement).style.border = "";

    // Resetting error flags and error messages
    if (id === "login-user-email") {
      this.errorFlagEmail = false;
      this.errorMessageEmail = "";
    } else if (id === "login-user-password") {
      this.errorFlagPassword = false;
      this.errorMessagePassword = "";
    }
  }

  onClear() {
    this.email = "";
    this.password = "";
    // Resetting input error flags
    this.errorFlagEmail = false;
    this.errorFlagPassword = false;
    // Resetting input error messages
    this.errorMessagePassword = "";
    this.errorMessagePassword = "";
    // Resetting Error Modal
    this.showErrorLogin = false;
    this.errorMessageLogin = "";

    // In case of error, change input border color
    (document.getElementById("login-user-email") as HTMLElement).style.border = "";
    (document.getElementById("login-user-password") as HTMLElement).style.border = "";
  }

  onSubmit() {
    let encryptedPassword = dataEncryption(this.password);

    const formData = new FormData();
    formData.append("userEmailId", this.email)
    formData.append("password", encryptedPassword);

    this.http.post<any>(services.login, formData).subscribe(response => {
      this.userDetailsStoreService.setUserDetails(response.userDetails);
      this.userDetailsStoreService.setTokenDetails(response.tokenDetails);

      this.email = "";
      this.password = "";

      this.router.navigate(["/users"]);
    }, error => {
      error.error.error.map((e: any) => {
        if (e.uiField === "login-user-email") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagEmail = true;
          this.errorMessageEmail = e.errorMessage;
        } else if (e.uiField === "login-user-password") {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagPassword = true;
          this.errorMessagePassword = e.errorMessage;
        } else {
          this.errorMessageLogin = e.errorMessage;
          this.showErrorLogin = true;
        }
      });
    });
  }

  closeErrorLogin() {
    this.showErrorLogin = false;
    this.errorMessageLogin = "";
  }
}
