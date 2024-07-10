import { Component, OnInit } from '@angular/core';
import { UserDetailsStoreService } from 'src/app/common/store/user-details/user-details-store.service';
import { LocalStorageService } from 'src/app/common/services/local-storage/local-storage.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  constructor(
    private userDetailsStoreService: UserDetailsStoreService,
    private localStorageService: LocalStorageService
  ) { }

  timer: any = null;
  tokenGeneratedTime: number = 0;
  accessTokenExpiresIn: number = 0;
  refreshTokenExpiresIn: number = 0;
  accessTokenTimer: number = 0;
  refreshTokenTimer: number = 0;
  refreshPopUpDisplayed: boolean = true;
  showRefreshToken: boolean = false;

  ngOnInit(): void {
    this.tokenTimer();
  }

  tokenTimer() {
    this.refreshPopUpDisplayed = true;

    this.tokenGeneratedTime = parseInt(this.userDetailsStoreService.getTokenDetails().tokenGeneratedTime);
    this.accessTokenExpiresIn = parseInt(this.userDetailsStoreService.getTokenDetails().accessTokenExpiresIn);
    this.refreshTokenExpiresIn = parseInt(this.userDetailsStoreService.getTokenDetails().refreshTokenExpiresIn);

    this.accessTokenTimer = parseInt(this.userDetailsStoreService.getTokenDetails().accessTokenExpiresIn);
      this.refreshTokenTimer = parseInt(this.userDetailsStoreService.getTokenDetails().refreshTokenExpiresIn);

    this.localStorageService.timer = setInterval(() => {
      // Token timer
      if (new Date().getTime() >= (this.tokenGeneratedTime + (this.accessTokenExpiresIn * 1000))) {
        clearInterval(this.timer);
        localStorage.removeItem("refreshTokenTimer");
        localStorage.removeItem("accessTokenTimer");
      } else {
        this.accessTokenTimer--;
        localStorage.removeItem("accessTokenTimer");
        localStorage.setItem("accessTokenTimer", this.accessTokenTimer.toString());
        // Check refresh token timer
        if (new Date().getTime() >= (this.tokenGeneratedTime + (this.refreshTokenExpiresIn * 1000)) && this.refreshPopUpDisplayed) {
          this.refreshPopUpDisplayed = false;
          this.showRefreshToken = true;
          localStorage.removeItem("refreshTokenTimer");
        } else if (this.refreshPopUpDisplayed) {
          this.refreshTokenTimer--;
          localStorage.removeItem("refreshTokenTimer");
          localStorage.setItem("refreshTokenTimer", this.refreshTokenTimer.toString());
        }
      }
    }, 1000);
  }

  onCloseRefreshToken() {
    this.showRefreshToken = false;
  }

  onContinueRefreshToken() {
    this.showRefreshToken = false;
    localStorage.removeItem("refreshTokenTimer");
    localStorage.removeItem("accessTokenTimer");
    this.tokenTimer();
  }
}
