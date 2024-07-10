<template>
  <div class="mm-user-profile">
    <div class="user-profile-box">
      <h3 class="user-profile-heading">{{ userName }}'s Profile</h3>
      <div class="user-profile-container">
        <div class="user-profile-data">
          <div class="key">First Name</div>
          <div class="value label-border">{{ firstName }}</div>
        </div>
        <div class="user-profile-data">
          <div class="key">Middle Name</div>
          <div class="value label-border">{{ middleName }}</div>
        </div>
        <div class="user-profile-data">
          <div class="key">Last Name</div>
          <div class="value label-border">{{ lastName }}</div>
        </div>
        <div class="user-profile-data">
          <div class="key">Preferred Name</div>
          <div class="value label-border">{{ preferredName }}</div>
        </div>
        <div class="user-profile-data">
          <div class="key">Gender</div>
          <div class="value label-border">{{ gender }}</div>
        </div>
        <div class="user-profile-data">
          <div class="key">Email</div>
          <div class="value label-border">{{ email }}</div>
        </div>
      </div>
    </div>

    <div
      class="user-profile-collapsible"
      :class="[!showPasswordUpdateContent ? 'user-profile-collapsible-default' : '']"
    >
      <div class="user-profile-collapsible-heading" @click="onPasswordUpdateCollapsibleClick()">
        <span class="left-items">
          Password Update
        </span>
        <div class="right">
          <span v-if="!showPasswordUpdateContent" class="right-items">+</span>
          <span v-if="showPasswordUpdateContent" class="right-items">-</span>
        </div>
      </div>
      <div v-if="showPasswordUpdateContent"
        class="user-profile-collapsible-content"
      >
        <div class="user-profile-collapsible-content-data">
          <div class="key">Old Password</div>
          <div class="value">
            <input
              type="password"
              id="user-profile-old-password"
              placeholder="Old Password"
              @focus="passwordUpdateInputOnFocus('user-profile-old-password')"
              v-model="oldPassword"
            />
            <InputFieldError
              v-if="errorFlagOldPassword"
              :errorMessage="errorMessageOldPassword"
            ></InputFieldError>
          </div>
        </div>

        <div class="user-profile-collapsible-content-data">
          <div class="key">New Password</div>
          <div class="value">
            <input
              type="password"
              id="user-profile-new-password"
              placeholder="New Password"
              @focus="passwordUpdateInputOnFocus('user-profile-new-password')"
              v-model="newPassword"
            />
            <InputFieldError
              v-if="errorFlagNewPassword"
              :errorMessage="errorMessageNewPassword"
            ></InputFieldError>
          </div>
        </div>

        <div class="user-profile-collapsible-content-data">
          <div class="key">Confirm New Password</div>
          <div class="value">
            <input
              type="password"
              id="user-profile-confirm-new-password"
              placeholder="Confirm New Password"
              @focus="passwordUpdateInputOnFocus('user-profile-confirm-new-password')"
              v-model="confirmNewPassword"
            />
            <InputFieldError
              v-if="errorFlagConfirmNewPassword"
              :errorMessage="errorMessageConfirmNewPassword"
            ></InputFieldError>
          </div>
        </div>

        <div class="user-profile-collapsible-content-clear-submit">
          <span>
            <MoneyManagerButton
              :buttonTitle="'Clear'"
              :buttonClass="'btn-secondary'"
              @onButtonClick="onPasswordUpdateClear()"
            ></MoneyManagerButton>
          </span>
          <div class="user-profile-collapsible-content-submit">
            <MoneyManagerButton
              :buttonTitle="'Submit'"
              :buttonClass="'btn-primary'"
              @onButtonClick="onPasswordUpdateSubmit()"
            ></MoneyManagerButton>
          </div>
        </div>
      </div>
    </div>

    <div
      class="user-profile-collapsible"
      :class="[!showPinUpdateContent ? 'user-profile-collapsible-default' : '']"
    >
      <div class="user-profile-collapsible-heading" @click="onPinUpdateCollapsibleClick()">
        <span class="left-items">
          PIN Update
        </span>
        <div class="right">
          <span v-if="!showPinUpdateContent" class="right-items">+</span>
          <span v-if="showPinUpdateContent" class="right-items">-</span>
        </div>
      </div>
      <div v-if="showPinUpdateContent"
        class="user-profile-collapsible-content"
      >
        <div class="user-profile-collapsible-content-data">
          <div class="key">Old Pin</div>
          <div class="value">
            <input
              type="password"
              id="user-profile-old-pin"
              placeholder="Old PIN"
              @focus="pinUpdateInputOnFocus('user-profile-old-pin')"
              v-model="oldPin"
            />
            <InputFieldError
              v-if="errorFlagOldPin"
              :errorMessage="errorMessageOldPin"
            ></InputFieldError>
          </div>
        </div>

        <div class="user-profile-collapsible-content-data">
           <div class="key">New PIN</div>
           <div class="value">
            <input
              type="password"
              id="user-profile-new-pin"
              placeholder="New PIN"
              @focus="pinUpdateInputOnFocus('user-profile-new-pin')"
              v-model="newPin"
            />
            <InputFieldError
              v-if="errorFlagNewPin"
              :errorMessage="errorMessageNewPin"
            ></InputFieldError>
          </div>
        </div>

        <div class="user-profile-collapsible-content-data">
          <div class="key">Confirm New PIN</div>
          <div class="value">
            <input
              type="password"
              id="user-profile-confirm-new-pin"
              placeholder="Confirm New PIN"
              @focus="pinUpdateInputOnFocus('user-profile-confirm-new-pin')"
              v-model="confirmNewPin"
            />
            <InputFieldError
              v-if="errorFlagConfirmNewPin"
              :errorMessage="errorMessageConfirmNewPin"
            ></InputFieldError>
          </div>
        </div>

        <div class="user-profile-collapsible-content-clear-submit">
          <span>
            <MoneyManagerButton
              :buttonTitle="'Clear'"
              :buttonClass="'btn-secondary'"
              @onButtonClick="onPinUpdateClear()"
            ></MoneyManagerButton>
          </span>
          <div class="user-profile-collapsible-content-submit">
            <MoneyManagerButton
              :buttonTitle="'Submit'"
              :buttonClass="'btn-primary'"
              @onButtonClick="onPinUpdateSubmit()"
            ></MoneyManagerButton>
          </div>
        </div>
      </div>
    </div>

    <div
      class="user-profile-collapsible"
      :class="[!showCountriesAndServicesUpdateContent ? 'user-profile-collapsible-default' : '']"
    >
      <div class="user-profile-collapsible-heading" @click="onCountriesAndServicesUpdateCollapsibleClick()">
        <span class="left-items">
          Countries and Services Update
        </span>
        <div class="right">
          <span v-if="!showCountriesAndServicesUpdateContent" class="right-items">+</span>
          <span v-if="showCountriesAndServicesUpdateContent" class="right-items">-</span>
        </div>
      </div>
      <div v-if="showCountriesAndServicesUpdateContent"
        class="user-profile-collapsible-content"
      >
        <div class="user-profile-collapsible-content-data">
          <div class="key">Country</div>
          <div class="value">
            <MoneyManagerCheckBox
              :resetCheckBox="resetCountriesCheckBox"
              :checkBoxList="countriesCheckBoxList"
              :defaultCheckBoxList="defaultUserOptedCountriesCheckBoxList"
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

        <div
          v-for="[key, value] of servicesOfferedByCountryMap" :key="key"
        >
          <div class="user-profile-collapsible-content-data" v-if="userOptedCountryIdsSet.has(key)">
            <div class="key">Services ({{ value.countryCode }})</div>
            <div class="value">
              <MoneyManagerCheckBox
                :resetCheckBox="resetServicesOfferedCheckBox"
                :checkBoxList="value.servicesOfferedCheckBoxList"
                :defaultCheckBoxList="value.defaultUserOptedServicesCheckBoxList"
                @onCheckBoxSelect="onServiceCheckBoxSelect($event, value)"
              ></MoneyManagerCheckBox>
              <div>
                <InputFieldError
                  v-if="errorFlagCountry"
                  :errorMessage="errorMessageCountry"
                ></InputFieldError>
              </div>
            </div>
          </div>
        </div>

        <div v-if="errorFlagService" class="user-profile-collapsible-content-data">
          <div class="key"></div>
          <div class="value">
            <InputFieldError
              :errorMessage="errorMessageService"
            ></InputFieldError>
          </div>
        </div>

        <div class="user-profile-collapsible-content-clear-submit">
          <span>
            <MoneyManagerButton
              :buttonTitle="'Clear'"
              :buttonClass="'btn-secondary'"
              @onButtonClick="onCountriesAndServicesUpdateClear()"
            ></MoneyManagerButton>
          </span>
          <div class="user-profile-collapsible-content-submit">
            <MoneyManagerButton
              :buttonTitle="'Submit'"
              :buttonClass="'btn-primary'"
              @onButtonClick="onCountriesAndServicesUpdateSubmit()"
            ></MoneyManagerButton>
          </div>
        </div>
      </div>
    </div>

    <ErrorModal
      v-if="showError"
    >
      <template v-slot:header>
        <h3 class="mm-error-modal-title">Error!!</h3>
        <span>
          <MoneyManagerButton
            :buttonClass="'btn-close'"
            @onButtonClick="closeError()"
          >
          </MoneyManagerButton>
        </span>
      </template>
      <template v-slot:body>
        <span v-html="errorMessage"></span>
      </template>
    </ErrorModal>

    <SuccessModal
      v-if="showSuccess"
    >
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
      <template v-slot:body>
        <span v-html="successMessage"></span>
      </template>
    </SuccessModal>

    <ConfirmationModal
      v-if="showConfirmation"
      @onCloseConfirmation="onCloseConfirmation()"
      @onConfirmationContinue="onConfirmationContinue()"
    >
      <template v-slot:body>
        <span v-html="confirmationMessage"></span>
      </template>
    </ConfirmationModal>

    <MoneyManagerLoadingModal
      v-if="showLoadingModal"
      :headingDisplay="'Updating, your patience is greatly valued'"
    ></MoneyManagerLoadingModal>
  </div>
</template>

<script>
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import InputFieldError from "@/components/common/InputFieldError";
import MoneyManagerCheckBox from "@/components/common/MoneyManagerCheckBox.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import SuccessModal from "@/components/common/SuccessModal.vue";
import ConfirmationModal from "@/components/common/ConfirmationModal.vue";
import MoneyManagerLoadingModal from "@/components/common/MoneyManagerLoadingModal.vue";
import axios from "axios";
import services from "@/assets/services/services.json";
import { dataEncryption } from "@/common/utils/utils.js";
  
export default {
  components: {
    MoneyManagerButton,
    InputFieldError,
    MoneyManagerCheckBox,
    ErrorModal,
    SuccessModal,
    ConfirmationModal,
    MoneyManagerLoadingModal
   },
 
   data() {
    return {
      userName: "",
      firstName: "",
      middleName: "",
      lastName: "",
      preferredName: "",
      gender: "",
      email: "",
      // Show Update
      showPasswordUpdateContent: false,
      showPinUpdateContent: false,
      showCountriesAndServicesUpdateContent: false,
      // Password update fields
      oldPassword: "",
      newPassword: "",
      confirmNewPassword: "",
      // Password update fields
      oldPin: "",
      newPin: "",
      confirmNewPin: "",
      // User Services fields
      countriesCheckBoxList: [],
      defaultUserOptedCountriesCheckBoxList: [],
      resetCountriesCheckBox: false,
      servicesOfferedByCountryMap: new Map(),
      resetServicesOfferedCheckBox: false,
      userOptedCountryIdsList: [],
      userOptedServicesList: [],
      userOptedServicesMap: new Map(),
      userOptedCountryIdsSet: new Set(),
      logoutFlag: false,
      // Error field flags
      errorFlagOldPassword: false,
      errorFlagNewPassword: false,
      errorFlagConfirmNewPassword: false,
      errorFlagOldPin: false,
      errorFlagNewPin: false,
      errorFlagConfirmNewPin: false,
      errorFlagCountry: false,
      errorFlagService: false,
      // Error field messages
      errorMessageOldPassword: "",
      errorMessageNewPassword: "",
      errorMessageConfirmNewPassword: "",
      errorMessageOldPin: "",
      errorMessageNewPin: "",
      errorMessageConfirmNewPin: "",
      errorMessageCountry: "",
      errorMessageService: "",
      // Modal
      showError: false,
      errorMessage: "",
      successMessage: "",
      showSuccess: false,
      showConfirmation: false,
      confirmationMessage: "",
      showLoadingModal: false
    }
  },

  created() {
    this.getUserDetails();
    this.getDefaultUserOptedCountriesAndServices();
  },

  methods: {
    getUserDetails() {
      let userObject = this.$store.getters["userDetails/GET_USER_DETAILS"];

      this.userName = userObject.preferredName ? userObject.preferredName : userObject.firstName + " " + userObject.lastName;
    
      this.firstName = userObject.firstName;
      this.middleName = userObject.middleName;
      this.lastName = userObject.lastName;
      this.preferredName = userObject.preferredName;
      this.gender = this.$store.getters["preData/GET_GENDER_DETAILS_MAP"].get(userObject.gender).genderName;
      this.email = userObject.email;
    },

    getDefaultUserOptedCountriesAndServices() {

      this.countriesCheckBoxList = [];
      this.defaultUserOptedCountriesCheckBoxList = [];
      this.userOptedCountryIdsList = [];
      this.userOptedServicesList = [];

      this.userOptedCountryIdsSet = new Set();
      for (let userOptedCountryId of this.$store.getters["userDetails/GET_USER_DETAILS"].userOptedCountryIdsList) {
        this.userOptedCountryIdsSet.add(userOptedCountryId);
      }

      let userOptedServiceIdsByCountryMap = new Map();
      for (let userOptedServices of this.$store.getters["userDetails/GET_USER_DETAILS"].userOptedServicesList) {
        let userOptedServicesIdsSet = new Set();
        for (let userOptedServiceId of userOptedServices.userOptedServiceIdsList) {
            userOptedServicesIdsSet.add(userOptedServiceId);
        }
        userOptedServiceIdsByCountryMap.set(userOptedServices.countryId, userOptedServicesIdsSet);
      }

      for(let [key, val] of this.$store.getters["preData/GET_COUNTRY_DETAILS_MAP"]) {

        if (this.userOptedCountryIdsSet.has(key)) {
          this.defaultUserOptedCountriesCheckBoxList.push({
            id: key,
            value: val.countryName
          });

          this.userOptedCountryIdsList.push(key);
        }

        this.countriesCheckBoxList.push({
          id: key,
          value: val.countryName
        });

        let servicesOfferedDetailsIdsByCountryList = this.$store.getters["preData/GET_COUNTRIES_AND_SERVICES_OFFERED_DETAILS_MAP"].get(key).servicesOfferedDetailsIdsList;

        let userOptedServiceIdsList = [];
        let servicesOfferedByCountryCheckBoxList = [];
        let defaultUserOptedServicesByCountryCheckBoxList = [];

        servicesOfferedDetailsIdsByCountryList.map(serviceId => {
          if (userOptedServiceIdsByCountryMap.get(key) && userOptedServiceIdsByCountryMap.get(key).has(serviceId)) {
            defaultUserOptedServicesByCountryCheckBoxList.push({
              id: serviceId,
              value: this.$store.getters["preData/GET_SERVICES_OFFERED_DETAILS_MAP"].get(serviceId).serviceName
            });

            userOptedServiceIdsList.push(serviceId);
          }

          servicesOfferedByCountryCheckBoxList.push({
            id: serviceId,
            value: this.$store.getters["preData/GET_SERVICES_OFFERED_DETAILS_MAP"].get(serviceId).serviceName
          });
        });

        if (userOptedServiceIdsList.length !== 0) {
          this.userOptedServicesMap.set(key, {
            countryId: key,
            userOptedServiceIdsList: userOptedServiceIdsList,
          });
        }

        this.servicesOfferedByCountryMap.set(key, {
          countryId: val.countryId,
          countryName: val.countryName,
          countryCode: val.countryCode,
          servicesOfferedCheckBoxList: servicesOfferedByCountryCheckBoxList,
          defaultUserOptedServicesCheckBoxList: defaultUserOptedServicesByCountryCheckBoxList
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

    onPasswordUpdateCollapsibleClick() {
      this.onPasswordUpdateClear();
      this.onPinUpdateClear();
      this.onCountriesAndServicesUpdateClear();

      this.showPinUpdateContent = false;
      this.showCountriesAndServicesUpdateContent = false;
      this.showPasswordUpdateContent = !this.showPasswordUpdateContent;
    },

    onPinUpdateCollapsibleClick() {
      this.onPasswordUpdateClear();
      this.onPinUpdateClear();
      this.onCountriesAndServicesUpdateClear();

      this.showPasswordUpdateContent = false;
      this.showCountriesAndServicesUpdateContent = false;
      this.showPinUpdateContent = !this.showPinUpdateContent;
    },

    onCountriesAndServicesUpdateCollapsibleClick() {
      this.onPasswordUpdateClear();
      this.onPinUpdateClear();
      this.onCountriesAndServicesUpdateClear();

      this.showPasswordUpdateContent = false;
      this.showPinUpdateContent = false;
      this.showCountriesAndServicesUpdateContent = !this.showCountriesAndServicesUpdateContent
    },

    onPasswordUpdateClear() {
      this.oldPassword = "";
      this.newPassword = "";
      this.confirmNewPassword = "";
      this.errorFlagOldPassword = false;
      this.errorFlagNewPassword = false;
      this.errorFlagConfirmNewPassword = false;
      this.errorMessageOldPassword = "";
      this.errorMessageNewPassword = "";
      this.errorMessageConfirmNewPassword = "";

      // In case of error, change input border color
      if (document.getElementById("user-profile-old-password")) {
        document.getElementById("user-profile-old-password").style.border = "";
      }
      if (document.getElementById("user-profile-new-password")) {
        document.getElementById("user-profile-new-password").style.border = "";
      }
      if (document.getElementById("user-profile-confirm-new-password")) {
        document.getElementById("user-profile-confirm-new-password").style.border = "";
      }
    },

    onPinUpdateClear() {
      this.oldPin = "";
      this.newPin = "";
      this.confirmNewPin = "";
      this.errorFlagOldPin = false;
      this.errorFlagNewPin = false;
      this.errorFlagConfirmNewPin = false;
      this.errorMessageOldPin = "";
      this.errorMessageNewPin = "";
      this.errorMessageConfirmNewPin = "";

      // In case of error, change input border color
      if (document.getElementById("user-profile-old-pin")) {
        document.getElementById("user-profile-old-pin").style.border = "";
      }
      if (document.getElementById("user-profile-new-pin")) {
        document.getElementById("user-profile-new-pin").style.border = "";
      }
      if (document.getElementById("user-profile-confirm-new-pin")) {
        document.getElementById("user-profile-confirm-new-pin").style.border = "";
      }
    },

    onCountriesAndServicesUpdateClear() {
      this.errorFlagCountry = false;
      this.errorFlagService = false;
      this.errorMessageCountry = "";
      this.errorMessageService = "";
      this.resetCountriesCheckBox = true;
      this.resetServicesOfferedCheckBox = true;
      this.getDefaultUserOptedCountriesAndServices();
    },

    onCountryCheckBoxSelect(countries) {
      // Resetting the error
      this.errorFlagCountry = false;
      this.errorMessageCountry = "";
      this.errorFlagService = false;
      this.errorMessageService = "";

      this.resetServicesOfferedCheckBox = false;
      this.resetCountriesCheckBox = false;
      this.userOptedCountryIdsList = [];
      this.userOptedCountryIdsSet = new Set();

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
      
      // Required to display services offered when country is selected
      this.servicesOfferedByCountryMap = new Map();

      countries.map((country) => {
        this.userOptedCountryIdsList.push(country.id);
        this.userOptedCountryIdsSet.add(country.id);
        let countryDetail = this.$store.getters["preData/GET_COUNTRY_DETAILS_MAP"].get(country.id);
        let serviceOfferedIdsByCountryList = this.$store.getters["preData/GET_COUNTRIES_AND_SERVICES_OFFERED_DETAILS_MAP"].get(country.id).servicesOfferedDetailsIdsList;
        let serviceOfferedDetailsByCountryList = [];
        let servicesOfferedByCountryCheckBoxList = [];

        // Fetching service offered by country
        serviceOfferedIdsByCountryList.map((serviceId) => {
          serviceOfferedDetailsByCountryList.push(
            this.$store.getters["preData/GET_SERVICES_OFFERED_DETAILS_MAP"].get(serviceId)
          );
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

    onServiceCheckBoxSelect(serviceSelected, country) {
      // Resetting error flags and message
      this.errorFlagService = false;
      this.errorMessageService = "";

      this.resetServicesOfferedCheckBox = false;

      // Deleting to set the values newly
      this.userOptedServicesMap.delete(country.countryId);
      this.userOptedServicesList = [];

      let servicesSelectedList = [];

      // Create the country newly and add the services selected
      serviceSelected.map((service) => {
        servicesSelectedList.push(service.id);
      });

      if (servicesSelectedList.length !== 0) {
        this.userOptedServicesMap.set(country.countryId, {
          countryId: country.countryId,
          userOptedServiceIdsList: servicesSelectedList,
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

    onPasswordUpdateSubmit () {
      this.showLoadingModal = true;
      let encryptedOldPassword = dataEncryption(this.oldPassword);
      let encryptedNewPassword = dataEncryption(this.newPassword);
      let encryptedConfirmNewPassword = dataEncryption(this.confirmNewPassword);

      const formData = new FormData();
      formData.append("oldPassword", encryptedOldPassword);
      formData.append("newPassword", encryptedNewPassword);
      formData.append("confirmNewPassword", encryptedConfirmNewPassword);

      axios.post(services.updatePassword, formData).then(response => {
        this.successMessage = response.data.message;
        this.showSuccess = response.data.status;
        this.showLoadingModal = false;
      }).catch(error => {
        error.response.data.error.map((e) => {
          if (e.uiField === "user-profile-old-password") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagOldPassword = true;
            this.errorMessageOldPassword = e.errorMessage;
          } else if (e.uiField === "user-profile-new-password") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagNewPassword = true;
            this.errorMessageNewPassword = e.errorMessage;
          } else if (e.uiField === "user-profile-confirm-new-password") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagConfirmNewPassword = true;
            this.errorMessageConfirmNewPassword = e.errorMessage;
          } else {
            this.errorMessage = e.errorMessage;
            this.showError = true;
          }
        });
      });

      this.showLoadingModal = false;
    },

    onPinUpdateSubmit() {
      this.showLoadingModal = true;
      let encryptedOldPin = dataEncryption(this.oldPin);
      let encryptedNewPin = dataEncryption(this.newPin);
      let encryptedConfirmNewPin = dataEncryption(this.confirmNewPin);

      const formData = new FormData();
      formData.append("oldPin", encryptedOldPin);
      formData.append("newPin", encryptedNewPin);
      formData.append("confirmNewPin", encryptedConfirmNewPin);

      axios.post(services.updatePin, formData).then(response => {
        this.successMessage = response.data.message;
        this.showSuccess = response.data.status;
        this.showLoadingModal = false;
      }).catch(error => {
        error.response.data.error.map((e) => {
          if (e.uiField === "user-profile-old-pin") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagOldPin = true;
            this.errorMessageOldPin = e.errorMessage;
          } else if (e.uiField === "user-profile-new-pin") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagNewPin = true;
            this.errorMessageNewPin = e.errorMessage;
          } else if (e.uiField === "user-profile-confirm-new-pin") {
            document.getElementById(e.uiField).style.border = "0.1rem solid red";
            this.errorFlagConfirmNewPin = true;
            this.errorMessageConfirmNewPin = e.errorMessage;
          } else {
            this.errorMessage = e.errorMessage;
            this.showError = true;
          }
        });

        this.showLoadingModal = false;
      });
    },

    onCountriesAndServicesUpdateSubmit() {
      this.showLoadingModal = true;

      const formData = {
        userOptedCountryIdsList: this.userOptedCountryIdsList,
        userOptedServicesList: this.userOptedServicesList,
        confirmed: false
      };

      axios.post(services.updateCountriesAndServices, formData).then(response => {
        if (response.data.confirmationRequired) {
          this.confirmationMessage = response.data.message;
          this.showConfirmation = response.data.confirmationRequired;
          this.showLoadingModal = false;
        } else if (response.data.sameData) {
          this.successMessage = response.data.message;
          this.showSuccess = response.data.sameData;
          this.showLoadingModal = false;
        } else if (response.data.dataAdded) {
          this.successMessage = response.data.message;
          this.showSuccess = response.data.dataAdded;
          this.$store.commit("userDetails/SET_USER_DETAILS", response.data.userDetails);
          this.logoutFlag = true;
          this.showLoadingModal = false;
        }
      }).catch(error => {
        // For displaying errors
        error.response.data.error.map((e) => {
          if (e.uiField === "user-profile-opt-countries") {
            this.errorFlagCountry = true;
            this.errorMessageCountry = e.errorMessage;
          } else if (e.uiField === "user-profile-opt-services") {
            this.errorFlagService = true;
            this.errorMessageService = e.errorMessage;
          }
        });

        this.showLoadingModal = false;
      });
    },

    passwordUpdateInputOnFocus(id) {
      document.getElementById(id).style.border = "";
      // Resetting error flags and error messages
      if (id === "user-profile-old-password") {
        this.errorFlagOldPassword = false;
        this.errorMessageOldPassword = "";
      } else if (id === "user-profile-new-password") {
        this.errorFlagNewPassword = false;
        this.errorMessageNewPassword = "";
      } else if (id === "user-profile-confirm-new-password") {
        this.errorFlagConfirmNewPassword = false;
        this.errorMessageConfirmNewPassword = "";
      }
    },

    pinUpdateInputOnFocus(id) {
      document.getElementById(id).style.border = "";
      // Resetting error flags and error messages
      if (id === "user-profile-old-pin") {
        this.errorFlagOldPin = false;
        this.errorMessageOldPin = "";
      } else if (id === "user-profile-new-pin") {
        this.errorFlagNewPin = false;
        this.errorMessageNewPin = "";
      } else if (id === "user-profile-confirm-new-pin") {
        this.errorFlagConfirmNewPin = false;
        this.errorMessageConfirmNewPin = "";
      }
    },

    closeError() {
      this.errorMessage = "";
      this.showError = false;
    },

    closeSuccess() {
      this.onPasswordUpdateClear();
      this.onPinUpdateClear();
      this.onCountriesAndServicesUpdateClear();

      this.showPasswordUpdateContent = false;
      this.showPinUpdateContent = false;
      this.showCountriesAndServicesUpdateContent = false;

      this.successMessage = "";
      this.showSuccess = false;
      if (this.logoutFlag) {
        axios.get(services.logout, {});
        this.$store.dispatch("logout/clearStoreAndLogout");
      }
    },

    onCloseConfirmation() {  
      this.confirmationMessage = "";
      this.showConfirmation = false;
    },

    onConfirmationContinue() {
      this.showLoadingModal = true;
      this.confirmationMessage = "";
      this.showConfirmation = false;

      const formData = {
        userOptedCountryIdsList: this.userOptedCountryIdsList,
        userOptedServicesList: this.userOptedServicesList,
        confirmed: true
      };

      axios.post(services.updateCountriesAndServices, formData).then(response => {
        this.successMessage = response.data.message;
        this.showSuccess = response.data.dataAdded;
        this.$store.commit("userDetails/SET_USER_DETAILS", response.data.userDetails);
        this.logoutFlag = true;
        this.showLoadingModal = false;
      }).catch(error => {
        console.log(error);
        this.showLoadingModal = false;
      });
    }
  }
}
</script>
