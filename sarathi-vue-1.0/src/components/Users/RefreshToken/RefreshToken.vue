<template>
  <div class="mm-refresh-token">
    <div class="mm-refresh-token-modal">
      <div class="mm-refresh-token-modal-backdrop">
        <div class="mm-refresh-token-modal-dialog">
          <div class="mm-refresh-token-modal-content">
            <div class="mm-refresh-token-modal-header">
              <h3 class="mm-refresh-token-modal-title">Continue Session</h3>
            </div>
            <div
              class="mm-refresh-token-modal-body"
            >
              <div class="mm-refresh-token-modal-body-content">
                Your session is about to expire. Please enter PIN and click on Continue if you wish to continue the session.
              </div>
              <div class="mm-refresh-token-modal-body-content">
                <div class="key">PIN</div>
                <div class="value">
                  <input
                    type="password"
                    id="refresh-token-pin"
                    placeholder="PIN"
                    @focus="inputOnFocus('refresh-token-pin')"
                    v-model="pin"
                  />
                  <InputFieldError
                    v-if="errorFlagPin"
                    :errorMessage="errorMessagePin"
                  ></InputFieldError>
                </div>
              </div>
            </div>
            <div class="mm-refresh-token-modal-footer">
              <div class="mm-refresh-token-close">
                <span>
                  <MoneyManagerButton
                    :buttonTitle="'Close'"
                    :buttonClass="'btn btn-danger'"
                    @onButtonClick="onCloseRefreshTokenClick()"
                  ></MoneyManagerButton>
                </span>
                <div class="mm-refresh-token-continue">
                  <MoneyManagerButton
                    :buttonTitle="'Continue'"
                    :buttonClass="'btn btn-primary'"
                    @onButtonClick="onContinueRefreshTokenClick()"
                  ></MoneyManagerButton>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <ErrorModal
      v-if="showErrorRefreshToken"
      :errorMessage="errorMessageRefreshToken"
    >
      <template v-slot:header>
        <h3 class="mm-error-modal-title">Error!!</h3>
        <span>
          <MoneyManagerButton
            :buttonClass="'btn-close'"
            @onButtonClick="closeErrorRefreshToken()"
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
import InputFieldError from "@/components/common/InputFieldError.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import { dataEncryption } from "@/common/utils/utils.js";

export default {
  name: "RefreshTokenModal",
  
  components: {
    InputFieldError,
    MoneyManagerButton,
    ErrorModal
  },

  data () {
    return {
      pin: "",
      errorFlagPin: false,
      errorMessagePin: "",
      showErrorRefreshToken: false,
      errorMessageRefreshToken: ""
    }
  },

  methods: {
    // Incase of the error, change the border from red to original
    inputOnFocus(id) {
      document.getElementById(id).style.border = "";

      // Resetting error flags and error messages
      if (id === "refresh-token-pin") {
        this.errorFlagPin = false;
        this.errorMessagePin = "";
      }
    },

    onCloseRefreshTokenClick() {
      this.$emit("onCloseRefreshToken");
    },

    onContinueRefreshTokenClick() {
      let encryptedPin = dataEncryption(this.pin);

      let formData = new FormData();
      formData.append("pin", encryptedPin);
      formData.append("refreshToken", this.$store.getters["userDetails/GET_TOKEN_DETAILS"].refreshToken);
      
      axios.post(services.refreshToken, formData).then(response => {
        this.$store.commit("userDetails/SET_TOKEN_DETAILS", response.data);
        this.$store.dispatch("userDetails/setHttpHeaders");

        this.onContinueRefreshToken();
      }, error => {
        error.response.data.error.map((e) => {
          if ("RT_0002" === e.errorCode) {
            this.showErrorRefreshToken = true;
            this.errorMessageRefreshToken = "Some internal error occured. Please try to login again";
            return;
          }

          if ("refresh-token-pin" === e.uiField) {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagPin = true;
            this.errorMessagePin = e.errorMessage;
          }
        });
      })
    },

    closeErrorRefreshToken() {
      this.showErrorRefreshToken = false;
      this.errorMessageRefreshToken = "";
      this.pin = "";
    },

    onContinueRefreshToken() {
      this.$emit("onContinueRefreshToken");
    }
  }
}
</script>

