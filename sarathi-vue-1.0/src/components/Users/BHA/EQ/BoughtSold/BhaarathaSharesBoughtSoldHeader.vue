<template>
  <div class="bhaaratha-shares-bought-sold-header">
    <div class="services-bought-sold-header">
      <div class="header">
        <span class="left-items">
          <input
            class="search-term-text"
            type="text"
            placeholder="Search"
            :disabled="isSearchFieldDisabled"
            v-model="searchText"
          />
          <MoneyManagerButton
            :buttonClass="isSearchButtonDisabled || searchText === '' ? 'btn-sm' : 'btn-primary btn-sm'"
            :buttonDisabled="isSearchButtonDisabled || searchText === ''"
            @onButtonClick="onSearchClick()"
          >
            <template v-slot:title>
              <i class="fa fa-search"></i>
            </template>
          </MoneyManagerButton>
        </span>
        <span class="left-items">
          <MoneyManagerButton
            :buttonClass="isRefreshButtonDisabled ? 'btn-sm' : 'btn-primary btn-sm'"
            :buttonDisabled="isRefreshButtonDisabled"
            @onButtonClick="onRefreshClick()"
          >
            <template v-slot:title>
              <i class="fa fa-refresh"></i>
            </template>
          </MoneyManagerButton>
        </span>

        <span class="left-items">
          <MoneyManagerDropdown
            :resetDropdown="resetHeaderDropdown"
            :disableDropdown="isHeaderDropdownDisabled"
            :dropdownList="headerDropdownList"
            :placeholder="headerDropdownPlaceholder"
            @onDropdownSelect="onHeaderDropdownSelect($event)"
          ></MoneyManagerDropdown>
        </span>

        <div class="right">
          <span class="right-items">
            <MoneyManagerButton v-if="type === 'bought'"
              :buttonTitle="'Bought?'"
              :buttonClass="'btn-buy btn-sm'"
              @onButtonClick="onSharesBoughtClick()"
            ></MoneyManagerButton>
            <MoneyManagerButton v-else-if="type === 'sold'"
              :buttonTitle="'Sold?'"
              :buttonClass="'btn-sell btn-sm'"
              @onButtonClick="onSharesSoldClick()"
            ></MoneyManagerButton>
          </span>

          <span class="right-items">
            <MoneyManagerButton v-if="type === 'bought'"
              :buttonClass="isSettingsButtonDisabled ? 'btn-sm' : 'btn-secondary btn-sm'"
              :buttonDisabled="isSettingsButtonDisabled"
              @onButtonClick="onSettingsClick()"
            >
              <template v-slot:title>
                <i class="fa fa-cog"></i>
              </template>
            </MoneyManagerButton>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import MoneyManagerDropdown from "@/components/common/MoneyManagerDropdown.vue";

export default {
  components: {
    MoneyManagerButton,
    MoneyManagerDropdown
  },

  props: {
    type: {
      type: String,
      required: true
    },
    isSearchFieldDisabled: {
      type: Boolean,
      required: true
    },
    isSearchButtonDisabled: {
      type: Boolean,
      required: true
    },
    isRefreshButtonDisabled: {
      type: Boolean,
      required: true
    },
    isHeaderDropdownDisabled: {
      type: Boolean,
      required: true
    },
    headerDropdownList: {
      type: Array,
      required: true
    },
    headerDropdownPlaceholder: {
      type: String,
      required: true
    },
    isLongTermOnly: {
      type: Boolean,
      default: false
    },
    isSettingsButtonDisabled: {
      type: Boolean,
      default: true
    }
  },

  data() {
    return {
      searchText: "",
      isNonConsolidated: false,
      resetHeaderDropdown: false
    }
  },

  methods: {
    onSearchClick() {
      this.$emit("onSearchClick", { searchText: this.searchText, isNonConsolidated: this.isNonConsolidated, isLongTermOnly: this.isLongTermOnly });
    },

    onRefreshClick() {
      this.$emit("onRefreshClick", { searchText: this.searchText, isNonConsolidated: this.isNonConsolidated, isLongTermOnly: this.isLongTermOnly });
    },

    onHeaderDropdownSelect(dropdownSelected) {
      this.resetHeaderDropdown = false;
      switch (dropdownSelected.value) {
        case "Consolidated":
          this.isNonConsolidated = false;
          this.$emit("onConsolidatedSelect", { searchText: this.searchText, isNonConsolidated: this.isNonConsolidated, isLongTermOnly: this.isLongTermOnly });
          break;
        case "Non-Consolidated":
          this.isNonConsolidated = true;
          this.$emit("onNonConsolidatedSelect", { searchText: this.searchText, isNonConsolidated: this.isNonConsolidated, isLongTermOnly: this.isLongTermOnly });
          break;
      }
    },

    onLongTermClick() {
      this.$emit("onLongTermClick", { searchText: this.searchText, isNonConsolidated: this.isNonConsolidated, isLongTermOnly: this.isLongTermOnly })
    },

    onSharesBoughtClick() {
      this.$router.push("/users/BHA/EQ/bought-shares");
    },

    onSharesSoldClick() {
      this.$router.push("/users/BHA/EQ/sold-shares");
    },

    onSettingsClick() {
      this.$emit("onSettingsClick");
    }
  }
}
</script>
