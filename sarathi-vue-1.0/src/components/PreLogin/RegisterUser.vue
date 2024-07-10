<template>
  <div class="mm-register-user">
    <div v-if="!registerUserReady && !registerUserError" class="spinner">
      <MoneyManagerLoading></MoneyManagerLoading>
    </div>
    <div v-if="registerUserReady" class="register-user-box">
      <h3 class="register-user-heading">User Registration</h3>
      <div class="register-user-form-container">
        <div class="register-user-form">
          <div class="key">First Name</div>
          <div class="value">
            <input
              type="text"
              id="register-user-first-name"
              placeholder="First Name"
              @focus="inputOnFocus('register-user-first-name')"
              v-model="firstName"
            />
            <InputFieldError
              v-if="errorFlagFirstName"
              :errorMessage="errorMessageFirstName"
            ></InputFieldError>
          </div>
        </div>
        <div class="register-user-form">
          <div class="key">Middle Name</div>
          <div class="value">
            <input
              type="text"
              id="register-user-middle-name"
              placeholder="Middle Name"
              @focus="inputOnFocus('register-user-middle-name')"
              v-model="middleName"
            />
            <InputFieldError
              v-if="errorFlagMiddleName"
              :errorMessage="errorMessageMiddleName"
            ></InputFieldError>
          </div>
        </div>
        <div class="register-user-form">
          <div class="key">Last Name</div>
          <div class="value">
            <input
              type="text"
              id="register-user-last-name"
              placeholder="Last Name"
              @focus="inputOnFocus('register-user-last-name')"
              v-model="lastName"
            />
            <InputFieldError
              v-if="errorFlagLastName"
              :errorMessage="errorMessageLastName"
            ></InputFieldError>
          </div>
        </div>
        <div class="register-user-form">
          <div class="key">Preferred Name</div>
          <div class="value">
            <input
              type="text"
              id="register-user-preferred-name"
              placeholder="Preferred Name"
              @focus="inputOnFocus('register-user-preferred-name')"
              v-model="preferredName"
            />
            <InputFieldError
              v-if="errorFlagPreferredName"
              :errorMessage="errorMessagePreferredName"
            ></InputFieldError>
          </div>
        </div>
        <div class="register-user-form">
          <div class="key">Gender</div>
          <div class="value">
            <MoneyManagerRadio
              :resetRadio="resetGenderRadio"
              :radioList="genderRadioList"
              @onRadioSelect="onGenderSelect($event)"
            ></MoneyManagerRadio>
            <div>
              <InputFieldError
                v-if="errorFlagGender"
                :errorMessage="errorMessageGender"
              ></InputFieldError>
            </div>
          </div>
        </div>
        <div class="register-user-form">
          <div class="key">E-Mail</div>
          <div class="value">
            <input
              type="text"
              id="register-user-email"
              placeholder="E-Mail"
              @focus="inputOnFocus('register-user-email')"
              @blur="userAvailability()"
              v-model="email"
            />
            <InputFieldError
              v-if="errorFlagEmail"
              :errorMessage="errorMessageEmail"
            ></InputFieldError>
          </div>
        </div>
        <div class="register-user-form">
          <div class="key">Password</div>
          <div class="value">
            <input
              type="password"
              id="register-user-password"
              placeholder="Password"
              @focus="inputOnFocus('register-user-password')"
              v-model="password"
            />
            <InputFieldError
              v-if="errorFlagPassword"
              :errorMessage="errorMessagePassword"
            ></InputFieldError>
          </div>
        </div>
        <div class="register-user-form">
          <div class="key">PIN</div>
          <div class="value">
            <input
              type="password"
              id="register-user-pin"
              placeholder="PIN"
              @focus="inputOnFocus('register-user-pin')"
              v-model="pin"
            />
            <InputFieldError
              v-if="errorFlagPin"
              :errorMessage="errorMessagePin"
            ></InputFieldError>
          </div>
        </div>
        <div class="register-user-form">
          <div class="key">Country</div>
          <div class="value">
            <MoneyManagerCheckBox
              :resetCheckBox="resetCountriesCheckBox"
              :checkBoxList="countriesCheckBoxList"
              @onCheckBoxSelect="onCountryCheckBoxSelect($event)"
            ></MoneyManagerCheckBox>
            <div>
              <InputFieldError
                v-if="errorFlagCountry"
                :errorMessage="errorMessageCountry"
              ></InputFieldError>
            </div>
          </div>
        </div>
        <div v-for="[key, value] of servicesOfferedByCountryMap" :key="key">
          <div class="register-user-form">
            <div class="key">Services ({{ value.countryCode }})</div>
            <div class="value">
              <MoneyManagerCheckBox
                :resetCheckBox="resetServicesOfferedCheckBox"
                :checkBoxList="value.servicesOfferedCheckBoxList"
                @onCheckBoxSelect="onServiceCheckBoxSelect($event, value)"
              ></MoneyManagerCheckBox>
            </div>
          </div>
        </div>
        <div v-if="errorFlagService" class="register-user-form">
          <div class="key"></div>
          <div class="value">
            <InputFieldError
              :errorMessage="errorMessageService"
            ></InputFieldError>
          </div>
        </div>
        <div class="register-user-form"></div>
        <div class="register-user-clear-submit">
          <span>
            <MoneyManagerButton
              :buttonTitle="'Clear'"
              :buttonClass="'btn-secondary'"
              @onButtonClick="onClear()"
            ></MoneyManagerButton>
          </span>
          <div class="register-user-submit">
            <MoneyManagerButton
              :buttonTitle="'Submit'"
              :buttonClass="'btn-primary'"
              @onButtonClick="onSubmit()"
            ></MoneyManagerButton>
          </div>
        </div>
        <div class="register-user-form"></div>
        <div class="register-user-form">
          <label
            >Already have an account?
            <router-link class="link" to="/login">Login</router-link>
            here</label
          >
        </div>
      </div>
    </div>
    <ErrorModal
      v-if="showErrorRegisterUserPreData"
      :errorMessage="errorMessageRegisterUserPreData"
    >
      <template v-slot:header>
        <h3 class="mm-error-modal-title">Error!!</h3>
        <span>
          <MoneyManagerButton
            :buttonClass="'btn-close'"
            @onButtonClick="closeErrorRegisterUserPreData()"
          >
          </MoneyManagerButton>
        </span>
      </template>
    </ErrorModal>

    <SuccessModal v-if="showSuccess" :successMessage="successMessage">
      <template v-slot:header>
        <h3 class="mm-success-modal-title">Success!!</h3>
        <span>
          <MoneyManagerButton
            :buttonClass="'btn-close'"
            @onButtonClick="closeSuccess()"
          >
          </MoneyManagerButton>
        </span>
      </template>
    </SuccessModal>
  </div>
</template>

<script>
import axios from "axios";
import services from "@/assets/services/services.json";
import MoneyManagerCheckBox from "@/components/common/MoneyManagerCheckBox.vue";
import MoneyManagerRadio from "@/components/common/MoneyManagerRadio.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import InputFieldError from "@/components/common/InputFieldError";
import ErrorModal from "@/components/common/ErrorModal.vue";
import SuccessModal from "@/components/common/SuccessModal.vue";
import MoneyManagerLoading from "@/components/common/MoneyManagerLoading.vue";
import { dataEncryption } from "@/common/utils/utils.js";

export default {
  components: {
    MoneyManagerCheckBox,
    MoneyManagerRadio,
    MoneyManagerButton,
    InputFieldError,
    ErrorModal,
    SuccessModal,
    MoneyManagerLoading,
  },
  data() {
    return {
      // loading
      registerUserReady: false,
      registerUserError: false,

      genderMap: new Map(),
      servicesOfferedByCountryMap: new Map(),
      userOptedServicesMap: new Map(),
      genderRadioList: [],
      resetGenderRadio: false,
      countriesCheckBoxList: [],
      resetCountriesCheckBox: false,
      countriesAndServicesOfferedList: [],
      firstName: "",
      middleName: "",
      lastName: "",
      preferredName: "",
      gender: "",
      email: "",
      password: "",
      pin: "",
      userOptedCountryIdsList: [],
      userOptedServicesList: [],
      resetServicesOfferedCheckBox: false,

      // Error field flags
      errorFlagFirstName: false,
      errorFlagMiddleName: false,
      errorFlagLastName: false,
      errorFlagPreferredName: false,
      errorFlagGender: false,
      errorFlagEmail: false,
      errorFlagPassword: false,
      errorFlagPin: false,
      errorFlagCountry: false,
      errorFlagService: false,
      // Error field messages
      errorMessageFirstName: "",
      errorMessageMiddleName: "",
      errorMessageLastName: "",
      errorMessagePreferredName: "",
      errorMessageGender: "",
      errorMessageEmail: "",
      errorMessagePassword: "",
      errorMessagePin: "",
      errorMessageCountry: "",
      errorMessageService: "",

      // Error/Success Modal
      showErrorRegisterUserPreData: false,
      showSuccess: false,
      errorMessageRegisterUserPreData: "",
      successMessage: "",
    };
  },
  created() {
    for(let [key, val] of this.$store.getters["preData/GET_COUNTRY_DETAILS_MAP"]) {
      this.countriesCheckBoxList.push({
        id: key,
        value: val.countryName
      });
    }

    for (let [key, val] of this.$store.getters["preData/GET_GENDER_DETAILS_MAP"]) {
      this.genderRadioList.push({
        id: key,
        value: val.genderName
      });
    }

    this.registerUserReady = true;
    this.registerUserError = false;
  },
  methods: {
    onCountryCheckBoxSelect(countries) {
      // Resetting the error
      this.errorFlagCountry = false;
      this.errorMessageCountry = "";
      this.errorFlagService = false;
      this.errorMessageService = "";

      this.resetCountriesCheckBox = false;
      this.userOptedCountryIdsList = [];
      // Required to display services offered when country is selected
      this.servicesOfferedByCountryMap = new Map();

      // Adding logic to reset userOptedServicesList
      if (countries.length === 0) {
        this.userOptedServicesList = [];
      } else {
        let updatedUserOptedServicesList = [];
        this.userOptedServicesList.forEach(userOptedServices => {
          countries.forEach(country => {
            if (country.id === userOptedServices.countryId) {
              updatedUserOptedServicesList.push(userOptedServices);
            }
          });
        });
        this.userOptedServicesList = updatedUserOptedServicesList;
      }

      countries.map((country) => {
        this.userOptedCountryIdsList.push(country.id);
        let countryDetail = this.$store.getters["preData/GET_COUNTRY_DETAILS_MAP"].get(country.id);
        let serviceOfferedIdsByCountryList = this.$store.getters["preData/GET_COUNTRIES_AND_SERVICES_OFFERED_DETAILS_MAP"].get(country.id).servicesOfferedDetailsIdsList;
        let servicesOfferedByCountryCheckBoxList = [];

        // Fetching service offered by country
        serviceOfferedIdsByCountryList.map((serviceId) => {
          servicesOfferedByCountryCheckBoxList.push({
            id: serviceId,
            value: this.$store.getters["preData/GET_SERVICES_OFFERED_DETAILS_MAP"].get(serviceId).serviceName
          });
        });

        // For services by country
        this.servicesOfferedByCountryMap.set(country.id, {
          countryId: countryDetail.countryId,
          countryName: countryDetail.countryName,
          countryCode: countryDetail.countryCode,
          servicesOfferedCheckBoxList: servicesOfferedByCountryCheckBoxList,
        });
      });
    },

    onGenderSelect(selectedGender) {
      // Resetting error flag and messages
      this.errorFlagGender = false;
      this.errorMessageGender = "";
      this.resetGenderRadio = false;
      this.gender = selectedGender.id;
    },

    onServiceCheckBoxSelect(serviceSelected, country) {
      // Resetting error flags and message
      this.errorFlagService = false;
      this.errorMessageService = "";

      // Deleting to set the values newly
      this.userOptedServicesMap.delete(country.countryId);
      this.userOptedServicesList = [];

      let serviceIdsSelectedList = [];

      // Create the country newly and add the services selected
      serviceSelected.map((service) => {
        serviceIdsSelectedList.push(service.id);
      });

      if (serviceIdsSelectedList.length !== 0) {
        this.userOptedServicesMap.set(country.countryId, {
          countryId: country.countryId,
          userOptedServiceIdsList: serviceIdsSelectedList,
        });
      }

      // Adding userOptedServicesList to field
      for (let [key, value] of this.userOptedServicesMap) {
        this.userOptedServicesList.push({
          countryId: key,
          userOptedServiceIdsList: value.userOptedServiceIdsList,
        });
      }
    },

    onClear() {
      this.firstName = "";
      this.middleName = "";
      this.lastName = "";
      this.preferredName = "";
      this.gender = "";
      this.resetGenderRadio = true;
      this.email = "";
      this.password = "";
      this.pin = "",
      this.userOptedCountryIdsList = [];
      this.userOptedServicesList = [];
      this.userOptedServicesMap = new Map();
      this.servicesOfferedByCountryMap = new Map();
      this.resetCountriesCheckBox = true;
      this.resetServicesOfferedCheckBox = true;
      // Error field flags
      this.errorFlagFirstName = false;
      this.errorFlagMiddleName = false;
      this.errorFlagLastName = false;
      this.errorFlagPreferredName = false;
      this.errorFlagGender = false;
      this.errorFlagEmail = false;
      this.errorFlagPassword = false;
      this.errorFlagPin = false;
      this.errorFlagCountry = false;
      this.errorFlagService = false;
      // Error field messages
      this.errorMessageFirstName = "";
      this.errorMessageMiddleName = "";
      this.errorMessageLastName = "";
      this.errorMessagePreferredName = "";
      this.errorMessageGender = "";
      this.errorMessageEmail = "";
      this.errorMessagePassword = "";
      this.errorMessagePin = "";
      this.errorMessageCountry = "";
      this.errorMessageService = "";

      // In case of error, change input border color
      document.getElementById("register-user-first-name").style.border = "";
      document.getElementById("register-user-middle-name").style.border = "";
      document.getElementById("register-user-last-name").style.border = "";
      document.getElementById("register-user-preferred-name").style.border = "";
      document.getElementById("register-user-email").style.border = "";
      document.getElementById("register-user-password").style.border = "";
      document.getElementById("register-user-pin").style.border = "";
    },

    onSubmit() {
      let encryptedPassword = dataEncryption(this.password);
      let encryptedPin = dataEncryption(this.pin);

      const formData = {
        firstName: this.firstName,
        middleName: this.middleName,
        lastName: this.lastName,
        preferredName: this.preferredName,
        gender: this.gender,
        email: this.email,
        password: encryptedPassword,
        pin: encryptedPin,
        userOptedCountryIdsList: this.userOptedCountryIdsList,
        userOptedServicesList: this.userOptedServicesList,
      };
      axios
        .post(services.registerUser, formData)
        .then((response) => {
          this.successMessage = response.data.message;
          this.showSuccess = true;

          this.firstName = "";
          this.middleName = "";
          this.lastName = "";
          this.preferredName = "";
          this.gender = "";
          this.resetGenderRadio = true;
          this.email = "";
          this.password = "";
          this.pin = "";
          this.userOptedCountryIdsList = [];
          this.userOptedServicesList = [];
          this.userOptedServicesMap = new Map();
          this.servicesOfferedByCountryMap = new Map();
          this.resetCountriesCheckBox = true;
          this.resetServicesOfferedCheckBox = true;
        })
        .catch((error) => {
          // For displaying errors
          error.response.data.error.map((e) => {
            if (e.uiField === "register-user-email") {
              document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
              this.errorFlagEmail = true;
              this.errorMessageEmail = e.errorMessage;
            } else if (e.uiField === "register-user-password") {
              document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
              this.errorFlagPassword = true;
              this.errorMessagePassword = e.errorMessage;
            } else if (e.uiField === "register-user-pin") {
              document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
              this.errorFlagPin = true;
              this.errorMessagePin = e.errorMessage;
            } else if (e.uiField === "register-user-first-name") {
              document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
              this.errorFlagFirstName = true;
              this.errorMessageFirstName = e.errorMessage;
            } else if (e.uiField === "register-user-middle-name") {
              document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
              this.errorFlagMiddleName = true;
              this.errorMessageMiddleName = e.errorMessage;
            } else if (e.uiField === "register-user-last-name") {
              document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
              this.errorFlagLastName = true;
              this.errorMessageLastName = e.errorMessage;
            } else if (e.uiField === "register-user-preferred-name") {
              document.getElementById(e.uiField).style.border =
                "0.1rem solid red";
              this.errorFlagPreferredName = true;
              this.errorMessagePreferredName = e.errorMessage;
            } else if (e.uiField === "register-user-gender") {
              this.errorFlagGender = true;
              this.errorMessageGender = e.errorMessage;
            } else if (e.uiField === "register-user-opt-countries") {
              this.errorFlagCountry = true;
              this.errorMessageCountry = e.errorMessage;
            } else if (e.uiField === "register-user-opt-services") {
              this.errorFlagService = true;
              this.errorMessageService = e.errorMessage;
            }
          });
        });
    },

    // Incase of the error, change the border from red to original
    inputOnFocus(id) {
      document.getElementById(id).style.border = "";

      // Resetting error flags and error messages
      if (id === "register-user-first-name") {
        this.errorFlagFirstName = false;
        this.errorMessageFirstName = "";
      } else if (id === "register-user-middle-name") {
        this.errorFlagMiddleName = false;
        this.errorMessageMiddleName = "";
      } else if (id === "register-user-last-name") {
        this.errorFlagLastName = false;
        this.errorMessageLastName = "";
      } else if (id === "register-user-preferred-name") {
        this.errorFlagPreferredName = false;
        this.errorMessagePreferredName = "";
      } else if (id === "register-user-email") {
        this.errorFlagEmail = false;
        this.errorMessageEmail = "";
      } else if (id === "register-user-password") {
        this.errorFlagPassword = false;
        this.errorMessagePassword = "";
      } else if (id === "register-user-pin") {
        this.errorFlagPin = false;
        this.errorMessagePin = "";
      }
    },

    userAvailability() {
      axios.get(services.userAvailability + "/" + this.email).catch((error) => {
        error.response.data.error.map((e) => {
          document.getElementById(e.uiField).style.border = "0.1rem solid red";
          this.errorFlagEmail = true;
          this.errorMessageEmail = e.errorMessage;
        });
      });
    },

    closeErrorRegisterUserPreData() {
      this.showErrorRegisterUserPreData = false;
      this.errorMessageRegisterUserPreData = "";
      this.$router.push("/login");
    },

    closeSuccess() {
      this.showSuccess = false;
      this.successMessage = "";
      this.$router.push("/login");
    },
  },
};
</script>
