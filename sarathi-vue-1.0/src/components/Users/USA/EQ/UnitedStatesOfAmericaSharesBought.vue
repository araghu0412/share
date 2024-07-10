<template>
  <div class="united-states-of-america-shares-bought">
    <UnitedStatesOfAmericaSharesBoughtSoldHeader
      :type="type"
      :isSearchFieldDisabled="isSearchFieldDisabled"
      :isSearchButtonDisabled="isSearchButtonDisabled"
      :isRefreshButtonDisabled="isRefreshButtonDisabled"
      :isHeaderDropdownDisabled="isHeaderDropdownDisabled"
      :headerDropdownList="headerDropdownList"
      :headerDropdownPlaceholder="headerDropdownPlaceholder"
      :isLongTermOnly="isLongTermOnly"
      :isSettingsButtonDisabled="isSettingsButtonDisabled"
      @onSearchClick="onSearchClick($event)"
      @onRefreshClick="onRefreshClick($event)"
      @onConsolidatedSelect="onConsolidatedSelect($event)"
      @onNonConsolidatedSelect="onNonConsolidatedSelect($event)"
      @onSettingsClick="onSettingsClick()"
    ></UnitedStatesOfAmericaSharesBoughtSoldHeader>
    <UnitedStatesOfAmericaSharesBoughtAndSoldData
      :type="'bought'"
      :loading="loading"
      :noRecords="noRecords"
      :unitedStatesOfAmericaSharesList="unitedStatesOfAmericaSharesList"
      :unitedStatesOfAmericaSharesTotal="unitedStatesOfAmericaSharesTotal"
    ></UnitedStatesOfAmericaSharesBoughtAndSoldData>

    <UnitedStatesOfAmericaSharesBoughtSettingsModal
      v-if="showSettings"
      :longTermRadioList="longTermRadioList"
      :longTermRadioDefault="longTermRadioDefault"
      @onCloseSettingsClick="onCloseSettingsClick()"
      @onConfirmSettingsClick="onConfirmSettingsClick($event)"
    ></UnitedStatesOfAmericaSharesBoughtSettingsModal>

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
import UnitedStatesOfAmericaSharesBoughtSettingsModal from "@/components/Users/USA/EQ/BoughtSold/UnitedStatesOfAmericaSharesBoughtSettingsModal.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import UnitedStatesOfAmericaSharesBoughtSoldHeader from '@/components/Users/USA/EQ/BoughtSold/UnitedStatesOfAmericaSharesBoughtSoldHeader.vue';
import UnitedStatesOfAmericaSharesBoughtAndSoldData from '@/components/Users/USA/EQ/BoughtSold/UnitedStatesOfAmericaSharesBoughtAndSoldData.vue';
import services from "@/assets/services/services.json";
import axios from "axios";

export default {
  components: {
    UnitedStatesOfAmericaSharesBoughtSettingsModal,
    ErrorModal,
    MoneyManagerButton,
    UnitedStatesOfAmericaSharesBoughtSoldHeader,
    UnitedStatesOfAmericaSharesBoughtAndSoldData
},

  data() {
    return {
      type: "bought",
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
      isLongTermOnly: false,
      searchText: "",
      loading: true,
      noRecords: false,
      unitedStatesOfAmericaSharesList: [],
      unitedStatesOfAmericaSharesTotal: {},
      isSettingsButtonDisabled: false,
      // Error/Success Modal
      showError: false,
      errorMessage: "",
      // Bought shares settings
      longTermRadioList: [
        {
          id: "Y",
          value: "Yes"
        },
        {
          id: "N",
          value: "No"
        }
      ],
      longTermRadioDefault: {},
      showSettings: false
    }
  },

  created() {
    this.longTermRadioDefault = this.longTermRadioList[1];
    this.isLongTermOnly = this.longTermRadioDefault.value === "Yes" ? true : false;
    this.getBoughtShares();
  },

  methods: {
    getBoughtShares() {
      this.loading = true;
      this.isSearchFieldDisabled = true;
      this.isRefreshButtonDisabled = true;
      this.isHeaderDropdownDisabled = true;
      this.noRecords = false;
      this.isSettingsButtonDisabled = true;

      axios.get(services.unitedStatesOfAmericaSharesShares + "?type=bought&searchTerm=" + this.searchText + "&isNonConsolidated=" + this.isNonConsolidated + "&isLongTermOnly=" + this.isLongTermOnly).then(response => {
        if (response.status === 204) {
          this.noRecords = true;
          this.loading = false;
          //
          this.isSearchFieldDisabled = false;
          this.isRefreshButtonDisabled = false;
          this.isHeaderDropdownDisabled = false;
          this.isSettingsButtonDisabled = false;
        } else {
          this.unitedStatesOfAmericaSharesList = response.data.unitedStatesOfAmericaSharesList;
          this.unitedStatesOfAmericaSharesTotal = response.data.unitedStatesOfAmericaSharesTotal
          this.loading = false;
          //
          this.isSearchFieldDisabled = false;
          this.isRefreshButtonDisabled = false;
          this.isHeaderDropdownDisabled = false;
          this.loading = false;
          this.isSettingsButtonDisabled = false;
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
      this.isLongTermOnly = unitedStatesOfAmericaShares.isLongTermOnly;
      this.getBoughtShares();
    },

    onRefreshClick(unitedStatesOfAmericaShares) {
      this.searchText = unitedStatesOfAmericaShares.searchText;
      this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
      this.isLongTermOnly = unitedStatesOfAmericaShares.isLongTermOnly;
      this.getBoughtShares();
    },

    onConsolidatedSelect(unitedStatesOfAmericaShares) {
      this.searchText = unitedStatesOfAmericaShares.searchText;
      this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
      this.isLongTermOnly = unitedStatesOfAmericaShares.isLongTermOnly;
      this.getBoughtShares();
    },

    onNonConsolidatedSelect(unitedStatesOfAmericaShares) {
      this.searchText = unitedStatesOfAmericaShares.searchText;
      this.isNonConsolidated = unitedStatesOfAmericaShares.isNonConsolidated;
      this.isLongTermOnly = unitedStatesOfAmericaShares.isLongTermOnly;
      this.getBoughtShares();
    },

    onSettingsClick() {
      this.showSettings = true;
    },

    onCloseSettingsClick() {
      this.showSettings = false;
    },

    onConfirmSettingsClick(longTermRadio) {
      this.isLongTermOnly = longTermRadio.value === "Yes" ? true : false;
      this.getBoughtShares();
      this.showSettings = false;
      this.longTermRadioDefault = longTermRadio;
    },

    closeError() {
      this.showError = false;
      this.errorMessage = "";
    }
  }
}
</script>
