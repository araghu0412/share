<template>
  <div class="bhaaratha-shares-one-share">
    <BhaarathaSharesOneShareDetailsHeader
      :type="type"
      :isRefreshButtonDisabled="isRefreshButtonDisabled"
      @onRefreshClick="onRefreshClick()"
    ></BhaarathaSharesOneShareDetailsHeader>

    <BhaarathaSharesOneShareDetailsData
      :loading="loading"
      :noRecords="noRecords"
      :pageRefreshed="pageRefreshed"
      :bhaarathaSharesList="bhaarathaSharesList"
      :bhaarathaSharesTotal="bhaarathaSharesTotal"
      :bseOneShareDetails="bseOneShareDetails"
      :nseOneShareDetails="nseOneShareDetails"
    ></BhaarathaSharesOneShareDetailsData>

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
</template>

<script>
import BhaarathaSharesOneShareDetailsHeader from "@/components/Users/BHA/EQ/OneShareDetails/BhaarathaSharesOneShareDetailsHeader.vue";
import BhaarathaSharesOneShareDetailsData from "@/components/Users/BHA/EQ/OneShareDetails/BhaarathaSharesOneShareDetailsData.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import services from "@/assets/services/services.json";
import axios from "axios";

export default {
  components: {
    BhaarathaSharesOneShareDetailsHeader,
    BhaarathaSharesOneShareDetailsData,
    MoneyManagerButton,
    ErrorModal
  },

  data() {
    return {
      type: "",
      bseCode: "",
      nseCode: "",
      moneycontrolCode: "",
      yahooBseCode: "",
      yahooNseCode: "",
      loading: false,
      noRecords: false,
      isRefreshButtonDisabled: false,
      pageRefreshed: false,
      bhaarathaSharesList: [],
      bhaarathaSharesTotal: {},
      bseOneShareDetails: {},
      nseOneShareDetails: {},
      // Error/Success Modal
      showError: false,
      errorMessage: "",
    }
  },

  created() {
    this.type = this.$route.query.type;
    this.bseCode = this.$route.query.bseCode;
    this.nseCode = this.$route.query.nseCode;
    this.moneycontrolCode = this.$route.query.moneycontrolCode;
    this.yahooBseCode = this.$route.query.yahooBseCode;
    this.yahooNseCode = this.$route.query.yahooNseCode;
    this.getOneShareDetails();
  },

  methods: {
    getOneShareDetails() {
      this.loading = true;
      this.noRecords = false;
      this.isRefreshButtonDisabled = true;

      axios.get(services.bhaarathaSharesOneShareDetails + "?type=" + this.type + "&bseCode=" + this.bseCode + "&nseCode=" + this.nseCode + "&moneycontrolCode=" + this.moneycontrolCode + "&yahooBseCode=" + this.yahooBseCode + "&yahooNseCode=" + this.yahooNseCode).then(response => {
        if (response.status === 204) {
          this.noRecords = true;
          this.loading = false;
        } else {
          this.bhaarathaSharesList = response.data.bhaarathaSharesResponse.bhaarathaSharesList;
          this.bhaarathaSharesTotal = response.data.bhaarathaSharesResponse.bhaarathaSharesTotal;
          this.bseOneShareDetails = response.data.bseOneShareDetails;
          this.nseOneShareDetails = response.data.nseOneShareDetails;
          this.isRefreshButtonDisabled = false;
          this.loading = false;
          this.pageRefreshed = false;
        }
      }).catch(error => {
        this.errorMessage = "";
        error.response.data.error.map((e) => {
          this.errorMessage = this.errorMessage + e.errorMessage +"<br>";
          this.showError = true;
        });
      });
    },

    onRefreshClick() {
      this.pageRefreshed = true;
      this.getOneShareDetails();
    },

    closeError() {
      this.showError = false;
      this.errorMessage = "";
    }
  }
}
</script>
