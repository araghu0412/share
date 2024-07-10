import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { UserDetailsStoreService } from 'src/app/common/store/user-details/user-details-store.service';
import services from 'src/assets/services/services.json';
import { dataEncryption } from 'src/app/common/utils/utils';
import { LocalStorageService } from 'src/app/common/services/local-storage/local-storage.service';

@Component({
  selector: 'app-refresh-token',
  templateUrl: './refresh-token.component.html',
  styleUrls: ['./refresh-token.component.css']
})
export class RefreshTokenComponent implements OnInit {

  @Output("onCloseRefreshToken") onCloseRefreshTokenEventEmitter = new EventEmitter();
  @Output("onContinueRefreshToken") onContinueRefreshTokenEventEmitter = new EventEmitter();

  pin: string = "";
  errorFlagPin: boolean = false;
  errorMessagePin: string = "";
  showErrorRefreshToken: boolean = false;
  errorMessageRefreshToken: string = "";
  
  constructor(
    private userDetailsStoreService: UserDetailsStoreService,
    private localStorageService: LocalStorageService,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
  }

  // Incase of the error, change the border from red to original
  inputOnFocus(id: string) {
    (document.getElementById(id) as HTMLElement).style.border = "";

    // Resetting error flags and error messages
    if (id === "refresh-token-pin") {
      this.errorFlagPin = false;
      this.errorMessagePin = "";
    }
  }

  onCloseRefreshTokenClick() {
    this.onCloseRefreshTokenEventEmitter.emit();
  }

  onContinueRefreshTokenClick() {
    let encryptedPin = dataEncryption(this.pin);

    let formData = new FormData();
    formData.append("pin", encryptedPin);
    formData.append("refreshToken", this.userDetailsStoreService.getTokenDetails().refreshToken);

    this.http.post<any>(services.refreshToken, formData).subscribe(response => {
      // Updating the new token details
      this.userDetailsStoreService.clearTokenDetails();
      clearInterval(this.localStorageService.timer);
      this.userDetailsStoreService.setTokenDetails(response);
      this.onContinueRefreshToken();
    }, error => {
      error.error.error.map((e: any) => {
        if ("RT_0002" === e.errorCode) {
          this.showErrorRefreshToken = true;
          this.errorMessageRefreshToken = "Some internal error occured. Please try to login again";
          return;
        }

        if ("refresh-token-pin" === e.uiField) {
          (document.getElementById(e.uiField) as HTMLElement).style.border = "0.1rem solid red";
          this.errorFlagPin = true;
          this.errorMessagePin = e.errorMessage;
        }
      });
    });

  }

  closeErrorRefreshToken() {
    this.showErrorRefreshToken = false;
    this.errorMessageRefreshToken = "";
    this.pin = "";
  }

  onContinueRefreshToken() {
    this.onContinueRefreshTokenEventEmitter.emit();
  }
}
