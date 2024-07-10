<template>
  <div class="bhaaratha-shares-bought">
    <BhaarathaSharesBoughtSoldHeader
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
    ></BhaarathaSharesBoughtSoldHeader>
    <BhaarathaSharesBoughtAndSoldData
      :type="'bought'"
      :loading="loading"
      :noRecords="noRecords"
      :bhaarathaSharesList="bhaarathaSharesList"
      :bhaarathaSharesTotal="bhaarathaSharesTotal"
    ></BhaarathaSharesBoughtAndSoldData>

    <BhaarathaSharesBoughtSettingsModal
      v-if="showSettings"
      :longTermRadioList="longTermRadioList"
      :longTermRadioDefault="longTermRadioDefault"
      @onCloseSettingsClick="onCloseSettingsClick()"
      @onConfirmSettingsClick="onConfirmSettingsClick($event)"
    ></BhaarathaSharesBoughtSettingsModal>

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
import BhaarathaSharesBoughtSettingsModal from "@/components/Users/BHA/EQ/BoughtSold/BhaarathaSharesBoughtSettingsModal.vue";
import ErrorModal from "@/components/common/ErrorModal.vue";
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import BhaarathaSharesBoughtSoldHeader from '@/components/Users/BHA/EQ/BoughtSold/BhaarathaSharesBoughtSoldHeader.vue';
import BhaarathaSharesBoughtAndSoldData from '@/components/Users/BHA/EQ/BoughtSold/BhaarathaSharesBoughtAndSoldData.vue';
import services from "@/assets/services/services.json";
import axios from "axios";


export default {
  components: {
    BhaarathaSharesBoughtSettingsModal,
    ErrorModal,
    MoneyManagerButton,
    BhaarathaSharesBoughtSoldHeader,
    BhaarathaSharesBoughtAndSoldData
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
      bhaarathaSharesList: [],
      bhaarathaSharesTotal: {},
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

      axios.get(services.bhaarathaSharesShares + "?type=bought&searchTerm=" + this.searchText + "&isNonConsolidated=" + this.isNonConsolidated + "&isLongTermOnly=" + this.isLongTermOnly).then(response => {
        if (response.status === 204) {
          this.noRecords = true;
          this.loading = false;
          //
          this.isSearchFieldDisabled = false;
          this.isRefreshButtonDisabled = false;
          this.isHeaderDropdownDisabled = false;
          this.isSettingsButtonDisabled = false;
        } else {
          this.bhaarathaSharesList = response.data.bhaarathaSharesList;
          this.bhaarathaSharesTotal = response.data.bhaarathaSharesTotal
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

    onSearchClick(bhaarathaShares) {
      this.searchText = bhaarathaShares.searchText;
      this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
      this.isLongTermOnly = bhaarathaShares.isLongTermOnly;
      this.getBoughtShares();
    },

    onRefreshClick(bhaarathaShares) {
      this.searchText = bhaarathaShares.searchText;
      this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
      this.isLongTermOnly = bhaarathaShares.isLongTermOnly;
      this.getBoughtShares();
    },

    onConsolidatedSelect(bhaarathaShares) {
      this.searchText = bhaarathaShares.searchText;
      this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
      this.isLongTermOnly = bhaarathaShares.isLongTermOnly;
      this.getBoughtShares();
    },

    onNonConsolidatedSelect(bhaarathaShares) {
      this.searchText = bhaarathaShares.searchText;
      this.isNonConsolidated = bhaarathaShares.isNonConsolidated;
      this.isLongTermOnly = bhaarathaShares.isLongTermOnly;
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
