<template>
  <div class="united-states-of-america-shares-sold">
    <UnitedStatesOfAmericaSharesBoughtSoldHeader
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
    ></UnitedStatesOfAmericaSharesBoughtSoldHeader>
    <UnitedStatesOfAmericaSharesBoughtAndSoldData
      :type="'sold'"
      :loading="loading"
      :noRecords="noRecords"
      :unitedStatesOfAmericaSharesList="unitedStatesOfAmericaSharesList"
      :unitedStatesOfAmericaSharesTotal="unitedStatesOfAmericaSharesTotal"
    ></UnitedStatesOfAmericaSharesBoughtAndSoldData>

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
import UnitedStatesOfAmericaSharesBoughtSoldHeader from '@/components/Users/USA/EQ/BoughtSold/UnitedStatesOfAmericaSharesBoughtSoldHeader.vue';
import UnitedStatesOfAmericaSharesBoughtAndSoldData from '@/components/Users/USA/EQ/BoughtSold/UnitedStatesOfAmericaSharesBoughtAndSoldData.vue';
import services from "@/assets/services/services.json";
import axios from "axios";


export default {
  components: {
    ErrorModal,
    MoneyManagerButton,
    UnitedStatesOfAmericaSharesBoughtSoldHeader,
    UnitedStatesOfAmericaSharesBoughtAndSoldData
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
      unitedStatesOfAmericaSharesList: [],
      unitedStatesOfAmericaSharesTotal: {},
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

      axios.get(services.unitedStatesOfAmericaSharesShares + "?type=sold&searchTerm=" + this.searchText + "&isNonConsolidated=" + this.isNonConsolidated).then(response => {
        if (response.status === 204) {
          this.noRecords = true;
          this.loading = false;
          //
          this.isSearchFieldDisabled = false;
          this.isRefreshButtonDisabled = false;
        } else {
          this.unitedStatesOfAmericaSharesList = response.data.unitedStatesOfAmericaSharesList;
          this.unitedStatesOfAmericaSharesTotal = response.data.unitedStatesOfAmericaSharesTotal
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

    onSearchClick(unitedStatesOfAmericaShares) {
      this.searchText = unitedStatesOfAmericaShares.searchText;
      this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
      this.getSoldShares();
    },

    onRefreshClick(unitedStatesOfAmericaShares) {
      this.searchText = unitedStatesOfAmericaShares.searchText;
      this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
      this.getSoldShares();
    },

    onConsolidatedSelect(unitedStatesOfAmericaShares) {
      this.searchText = unitedStatesOfAmericaShares.searchText;
      this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
      this.getSoldShares();
    },

    onNonConsolidatedSelect(unitedStatesOfAmericaShares) {
      this.searchText = unitedStatesOfAmericaShares.searchText;
      this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
      this.getSoldShares();
    },

    closeError() {
      this.showError = false;
      this.errorMessage = "";
    }
  }
}
</script>
