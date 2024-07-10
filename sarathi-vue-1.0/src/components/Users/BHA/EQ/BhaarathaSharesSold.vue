<template>
  <div class="bhaaratha-shares-sold">
    <BhaarathaSharesBoughtSoldHeader
      :type="type"
      :isSearchFieldDisabled="isSearchFieldDisabled"
      :isSearchButtonDisabled="isSearchButtonDisabled"
      :isRefreshButtonDisabled="isRefreshButtonDisabled"
      :isHeaderDropdownDisabled="isHeaderDropdownDisabled"
      :headerDropdownList="headerDropdownList"
      :headerDropdownPlaceholder="headerDropdownPlaceholder"
      @onSearchClick="onSearchClick($event)"
      @onRefreshClick="onRefreshClick($event)"
      @onConsolidatedSelect="onConsolidatedSelect($event)"
      @onNonConsolidatedSelect="onNonConsolidatedSelect($event)"
    ></BhaarathaSharesBoughtSoldHeader>
    <BhaarathaSharesBoughtAndSoldData
      :type="'sold'"
      :loading="loading"
      :noRecords="noRecords"
      :bhaarathaSharesList="bhaarathaSharesList"
      :bhaarathaSharesTotal="bhaarathaSharesTotal"
    ></BhaarathaSharesBoughtAndSoldData>

    <ErrorModal
      v-if="showError"
      :errorMessage="errorMessage"
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
    </ErrorModal>

  </div>
</template>

<script>
import ErrorModal from "@/components/common/ErrorModal.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import BhaarathaSharesBoughtSoldHeader from '@/components/Users/BHA/EQ/BoughtSold/BhaarathaSharesBoughtSoldHeader.vue';
import BhaarathaSharesBoughtAndSoldData from '@/components/Users/BHA/EQ/BoughtSold/BhaarathaSharesBoughtAndSoldData.vue';
import services from "@/assets/services/services.json";
import axios from "axios";


export default {
  components: {
    ErrorModal,
    MoneyManagerButton,
    BhaarathaSharesBoughtSoldHeader,
    BhaarathaSharesBoughtAndSoldData
},

  data() {
    return {
      type: "sold",
      isSearchFieldDisabled: false,
      isSearchButtonDisabled: false,
      isRefreshButtonDisabled: false,
      isHeaderDropdownDisabled: false,
      headerDropdownList: [
        {
          id: "1",
          value: "Consolidated"
        },
        {
          id: "2",
          value: "Non-Consolidated"
        }
      ],
      headerDropdownPlaceholder: "Consolidated",
      isNonConsolidated: false,
      searchText: "",
      loading: true,
      noRecords: false,
      bhaarathaSharesList: [],
      bhaarathaSharesTotal: {},
      // Error/Success Modal
      showError: false,
      errorMessage: ""
    }
  },

  created() {
    this.getSoldShares();
  },

  methods: {
    getSoldShares() {
      this.loading = true;
      this.isSearchFieldDisabled = true;
      this.isRefreshButtonDisabled = true;
      this.noRecords = false;

      axios.get(services.bhaarathaSharesShares + "?type=sold&searchTerm=" + this.searchText + "&isNonConsolidated=" + this.isNonConsolidated).then(response => {
        if (response.status === 204) {
          this.noRecords = true;
          this.loading = false;
          //
          this.isSearchFieldDisabled = false;
          this.isRefreshButtonDisabled = false;
        } else {
          this.bhaarathaSharesList = response.data.bhaarathaSharesList;
          this.bhaarathaSharesTotal = response.data.bhaarathaSharesTotal
          this.loading = false;
          //
          this.isSearchFieldDisabled = false;
          this.isRefreshButtonDisabled = false;
          this.loading = false;
        }
      }).catch(error => {
        error.response.data.error.map((e) => {
          this.errorMessage = e.errorMessage;
          this.showError = true;
        });
      });
    },

    onSearchClick(bhaarathaShares) {
      this.searchText = bhaarathaShares.searchText;
      this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
      this.getSoldShares();
    },

    onRefreshClick(bhaarathaShares) {
      this.searchText = bhaarathaShares.searchText;
      this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
      this.getSoldShares();
    },

    onConsolidatedSelect(bhaarathaShares) {
      this.searchText = bhaarathaShares.searchText;
      this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
      this.getSoldShares();
    },

    onNonConsolidatedSelect(bhaarathaShares) {
      this.searchText = bhaarathaShares.searchText;
      this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
      this.getSoldShares();
    },

    closeError() {
      this.showError = false;
      this.errorMessage = "";
    }
  }
}
</script>
