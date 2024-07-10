<template>
  <UsersHeader></UsersHeader>
  <RefreshToken
    v-if="showRefreshToken"
    @onCloseRefreshToken="onCloseRefreshToken()"
    @onContinueRefreshToken="onContinueRefreshToken()"
  ></RefreshToken>
  <router-view></router-view>
</template>

<script>
import UsersHeader from "@/components/Users/UsersHeader.vue";
import RefreshToken from "@/components/Users/RefreshToken/RefreshToken.vue";
import localStorageObj from "@/common/utils/local-storage";

export default {
  name: "Users",
  components: {
    UsersHeader,
    RefreshToken
  },
  data() {
    return {
      timer: null,
      tokenGeneratedTime: 0,
      accessTokenExpiresIn: 0,
      refreshTokenExpiresIn: 0,
      accessTokenTimer: 0,
      refreshTokenTimer: 0,
      refreshPopUpDisplayed: true,
      showRefreshToken: false
    };
  },

  created() {
    this.tokenTimer()
  },

  methods: {
    tokenTimer() {
      this.refreshPopUpDisplayed = true;

      this.tokenGeneratedTime = parseInt(
        this.$store.getters["userDetails/GET_TOKEN_DETAILS"].tokenGeneratedTime
      );
      this.accessTokenExpiresIn = parseInt(
        this.$store.getters["userDetails/GET_TOKEN_DETAILS"].accessTokenExpiresIn
      );
      this.refreshTokenExpiresIn = parseInt(
        this.$store.getters["userDetails/GET_TOKEN_DETAILS"].refreshTokenExpiresIn
      );

      this.accessTokenTimer = parseInt(
        this.$store.getters["userDetails/GET_TOKEN_DETAILS"].accessTokenExpiresIn
      );
      this.refreshTokenTimer = parseInt(
        this.$store.getters["userDetails/GET_TOKEN_DETAILS"].refreshTokenExpiresIn
      );

      // this.timer = setInterval(() => {
      localStorageObj.localStorageTimer = setInterval(() => {
        // Token timer
        if (
          new Date().getTime() >=
          this.tokenGeneratedTime + this.accessTokenExpiresIn * 1000
        ) {
          clearInterval(localStorageObj.localStorageTimer);
          localStorage.removeItem("refreshTokenTimer");
          localStorage.removeItem("accessTokenTimer");

        } else {
          this.accessTokenTimer--;
          localStorage.removeItem("accessTokenTimer");
          localStorage.setItem("accessTokenTimer", this.accessTokenTimer);
          // Check refresh token timer
          if (
            new Date().getTime() >=
              this.tokenGeneratedTime + this.refreshTokenExpiresIn * 1000 &&
            this.refreshPopUpDisplayed
          ) {
            this.refreshPopUpDisplayed = false;
            this.showRefreshToken = true;
            localStorage.removeItem("refreshTokenTimer");
          } else if (this.refreshPopUpDisplayed) {
            this.refreshTokenTimer--;
            localStorage.removeItem("refreshTokenTimer");
            localStorage.setItem("refreshTokenTimer", this.refreshTokenTimer);
          }
        }
      }, 1000);
    },

    onCloseRefreshToken() {
      this.showRefreshToken = false;
    },

    onContinueRefreshToken() {
      this.showRefreshToken = false;
      clearInterval(localStorageObj.localStorageTimer);
      localStorage.removeItem("refreshTokenTimer");
      localStorage.removeItem("accessTokenTimer");
      this.tokenTimer();
    }
  }
};
</script>
