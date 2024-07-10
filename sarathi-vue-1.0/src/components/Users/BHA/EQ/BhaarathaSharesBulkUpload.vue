<template>
  <div class="bhaaratha-shares-bulk-upload">
    <div class="services-bulk-upload">
      <div class="header">
        <span class="left-items">
          <MoneyManagerButton
            :buttonTitle="'Home'"
            :buttonClass="'btn-primary btn-sm'"
            @onButtonClick="onHomeButtonClick()"
          ></MoneyManagerButton>
        </span>

        <span class="left-items">
          <MoneyManagerButton
            :buttonTitle="'Bought'"
            :buttonClass="'btn-buy btn-sm btn-sm'"
            @onButtonClick="onBoughtButtonClick()"
          ></MoneyManagerButton>
        </span>

        <span class="left-items">
          <MoneyManagerButton
            :buttonTitle="'Sold'"
            :buttonClass="'btn-sell btn-sm'"
            @onButtonClick="onSoldButtonClick()"
          ></MoneyManagerButton>
        </span>

        <div class="right">
          <span class="right-items">
            <MoneyManagerButton
              :buttonClass="'btn-secondary btn-sm'"
              @onButtonClick="onDownloadBulkUploadDocumentClick()"
            >
              <template v-slot:title>
                <i class="fa fa-download"></i>
              </template>
            </MoneyManagerButton>
          </span>
        </div>
      </div>

      <div class="spinner" v-if="loading">
        <MoneyManagerLoading></MoneyManagerLoading>
      </div>
      <div v-if="!loading" class="services-bulk-upload-area">
        <div class="services-bulk-upload-input">
          <input type="file" id="file-upload" ref="bhaarathaSharesBulkUploadFileUpload" class="input-file-upload" @change="onInputFileUploadChange($event)">
          <MoneyManagerButton
            :buttonClass="'btn-secondary btn-sm'"
            @onButtonClick="uploadFileClick()"
          >
            <template v-slot:title>
              <i class="fa fa-upload"></i>
            </template>
          </MoneyManagerButton>
          <span class="file-name">{{ filename }}</span>
        </div>
        <div class="services-bulk-upload-clear-submit">
          <span>
            <MoneyManagerButton
              :buttonTitle="'Clear'"
              :buttonDisabled="file === null"
              :buttonClass="file === null ? 'btn-sm' : 'btn-secondary btn-sm'"
              @onButtonClick="onBulkUploadClear()"
            ></MoneyManagerButton>
          </span>
          <div class="services-bulk-upload-submit">
            <MoneyManagerButton
              :buttonTitle="'Submit'"
              :buttonDisabled="file === null"
              :buttonClass="file === null ? 'btn-sm' : 'btn-primary btn-sm'"
              @onButtonClick="onBulkUploadSubmit()"
            ></MoneyManagerButton>
          </div>
        </div>
      </div>

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
    </div>
  </div>
</template>

<script>
import MoneyManagerLoading from "@/components/common/MoneyManagerLoading.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import SuccessModal from "@/components/common/SuccessModal.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import axios from 'axios';
import services from "@/assets/services/services.json";

export default {

  components: {
    MoneyManagerLoading,
    MoneyManagerButton,
    SuccessModal,
    ErrorModal
  },

  data() {
    return {
      loading: false,
      file: null,
      filename: "",
      showSuccess: false,
      successMessage: "",
      showError: false,
      errorMessage: ""
    }
  },

  methods: {
    onHomeButtonClick() {
      this.$router.push("/users/BHA/EQ/home");
    },

    onBoughtButtonClick() {
      this.$router.push("/users/BHA/EQ/bought");
    },

    onSoldButtonClick() {
      this.$router.push("/users/BHA/EQ/sold");
    },

    onDownloadBulkUploadDocumentClick() {
      axios.get(services.bhaarathaSharesDownloadBulkUploadDocument, { responseType: 'blob' }).then(response => {
        let a = document.createElement("a");
        document.body.appendChild(a);

        let file = window.URL.createObjectURL(new Blob([response.data], { type : "application/vnd.ms-excel" }));

        a.href = file;
        a.download = response.headers.filename;
        a.click();
      }).catch(error => {
        error.response.data.error.map((e) => {
          this.errorMessage = e.errorMessage;
          this.showError = true;
        });
      });
    },

    uploadFileClick() {
      document.getElementById("file-upload").click();
    },

    onInputFileUploadChange(event) {
      this.file = event.target.files[0];
      this.filename = this.file.name;
    },

    onBulkUploadClear() {
      this.file = null;
      this.filename = "";
      this.$refs.bhaarathaSharesBulkUploadFileUpload.value="";
    },

    onBulkUploadSubmit() {
      this.loading = true;

      let formData = new FormData();
      formData.append("file", this.file);

      axios.post(services.bhaarathaSharesBulkUploadDocument, formData).then(response => {
        this.successMessage = response.data.message;
        this.showSuccess = true;
        this.file = null;
        this.filename = "";
        this.loading = false;
      }).catch(error => {
        error.response.data.error.map((e) => {
          this.errorMessage = e.errorMessage;
          this.showError = true;
        });
        this.loading = false;
      });
    },

    closeError() {
      this.errorMessage = "";
      this.showError = false;
    },

    closeSuccess() {
      this.successMessage = "";
      this.showSuccess = false;
    }
  }
}
</script>
