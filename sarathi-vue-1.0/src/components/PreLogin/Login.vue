<template>
  <div class="mm-login-user">
    <div class="login-user-box">
      <h3 class="login-user-heading">Login Details</h3>
      <div class="login-user-form-container">
        <div class="login-user-form">
          <div class="key">Email</div>
          <div class="value">
            <input
              type="text"
              id="login-user-email"
              placeholder="Email"
              @focus="inputOnFocus('login-user-email')"
              v-model="email"
            />
            <InputFieldError
              v-if="errorFlagEmail"
              :errorMessage="errorMessageEmail"
            ></InputFieldError>
          </div>
        </div>
        <div class="login-user-form">
          <div class="key">Password</div>
          <div class="value">
            <input
              type="password"
              id="login-user-password"
              placeholder="Password"
              @focus="inputOnFocus('login-user-password')"
              v-model="password"
            />
            <InputFieldError
              v-if="errorFlagPassword"
              :errorMessage="errorMessagePassword"
            ></InputFieldError>
          </div>
        </div>
        <div class="login-user-form"></div>
        <div class="login-user-clear-submit">
          <span>
            <MoneyManagerButton
              :buttonTitle="'Clear'"
              :buttonClass="'btn-secondary'"
              @onButtonClick="onClear()"
            ></MoneyManagerButton>
          </span>
          <div class="login-user-submit">
            <MoneyManagerButton
              :buttonTitle="'Submit'"
              :buttonClass="'btn-primary'"
              @onButtonClick="onSubmit()"
            ></MoneyManagerButton>
          </div>
        </div>
        <div class="login-user-form"></div>
        <div class="login-user-form">
          <label>
            New User? <router-link class="link" to="/register-user">Register</router-link> here
          </label>
        </div>
      </div>
    </div>
    <ErrorModal
      v-if="showErrorLogin"
      :errorMessage="errorMessageLogin"
    >
      <template v-slot:header>
        <h3 class="mm-error-modal-title">Error!!</h3>
        <span>
          <MoneyManagerButton
            :buttonClass="'btn-close'"
            @onButtonClick="closeErrorLogin()"
          >
          </MoneyManagerButton>
        </span>
      </template>
    </ErrorModal>
  </div>
</template>

<script>
import axios from "axios";
import services from "@/assets/services/services.json";
import InputFieldError from "@/components/common/InputFieldError";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import { dataEncryption } from "@/common/utils/utils.js";

export default {
  name: "Login",
  components: {
    InputFieldError,
    MoneyManagerButton,
    ErrorModal
  },
  data() {
    return {
      email: "",
      password: "",

      // Error field flags
      errorFlagEmail: "",
      errorFlagPassword: "",

      // Error field messages
      errorMessageEmail: "",
      errorMessagePassword: "",

      // Error Modal
      showErrorLogin: false,
      errorMessageLogin: ""
    }
  },
  methods: {
    // Incase of the error, change the border from red to original
    inputOnFocus(id) {
      document.getElementById(id).style.border = "";

      // Resetting error flags and error messages
      if (id === "login-user-email") {
        this.errorFlagEmail = false;
        this.errorMessageEmail = "";
      } else if (id === "login-user-password") {
        this.errorFlagPassword = false;
        this.errorMessagePassword = "";
      }
    },

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
      document.getElementById("login-user-email").style.border = "";
      document.getElementById("login-user-password").style.border = "";
    },

    onSubmit() {
      let encryptedPassword = dataEncryption(this.password);

      const formData = new FormData();
      formData.append("userEmailId", this.email)
      formData.append("password", encryptedPassword);

      axios.post(services.login, formData).then(response => {
        this.$store.commit("userDetails/SET_USER_DETAILS", response.data.userDetails);
        this.$store.commit("userDetails/SET_TOKEN_DETAILS", response.data.tokenDetails);
        this.$store.dispatch("userDetails/setHttpHeaders");

        this.email = "";
        this.password = "";

        this.$router.push("/users")
      }).catch(error => {
        error.response.data.error.map((e) => {
          if (e.uiField === "login-user-email") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagEmail = true;
            this.errorMessageEmail = e.errorMessage;
          } else if (e.uiField === "login-user-password") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagPassword = true;
            this.errorMessagePassword = e.errorMessage;
          } else {
            this.errorMessageLogin = e.errorMessage;
            this.showErrorLogin = true;
          }
        })
      })
    },

    closeErrorLogin() {
      this.showErrorLogin = false;
      this.errorMessageLogin = "";
    }
  }
}
</script>
