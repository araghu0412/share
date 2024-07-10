<template>
  <div class="bhaaratha-shares-sector-category-analysis-header">
    <div class="services-analysis-sector-category-header">
      <div class="header">
        <span class="left-items">
          <MoneyManagerButton
            :buttonClass="'navigation-button btn-sm'"
            @onButtonClick="onBackButtonClick()"
          >
            <template v-slot:title>
              <i class="fas fa-long-arrow-alt-left"></i> Back
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
              Refresh <i class="fa fa-refresh"></i>
            </template>
          </MoneyManagerButton>
        </span>

        <span class="left-items">
          <MoneyManagerDropdown
            :resetDropdown="resetTypeDropdown"
            :disableDropdown="isTypeDropdownDisabled"
            :dropdownList="typeDropdownList"
            :placeholder="typeDropdownPlaceholder"
            @onDropdownSelect="onTypeDropdownSelect($event)"
          ></MoneyManagerDropdown>
        </span>

        <span class="left-items">
          <MoneyManagerDropdown
            v-if="showOneAnalysis"
            :resetDropdown="resetOneAnalysisDropdown"
            :disableDropdown="isTypeDropdownDisabled"
            :dropdownList="oneAnalysisDropdownList"
            :placeholder="'One Analysis'"
            @onDropdownSelect="onOneAnalysisDropdownSelect($event)"
          ></MoneyManagerDropdown>
        </span>

        <div class="right">
          <span class="right-items">
            <MoneyManagerButton
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
    isRefreshButtonDisabled: {
      type: Boolean,
      required: true
    },
    isTypeDropdownDisabled: {
      type: Boolean,
      required: true
    },
    typeDropdownList: {
      type: Array,
      required: true
    },
    typeDropdownPlaceholder: {
      type: String,
      required: true
    },
    isSettingsButtonDisabled: {
      type: Boolean,
      required: true
    },
    oneAnalysisDropdownList: {
      type: Array,
      required: true
    },
    showOneAnalysis: {
      type: Boolean,
      required: true
    }
  },

  data() {
    return {
      resetTypeDropdown: false,
      resetOneAnalysisDropdown: false,
      resetAnalysisServiceDropdown: false,
    }
  },

  methods: {
    onRefreshClick() {
      this.$emit("onRefreshClick");
    },

    onTypeDropdownSelect(dropdownSelected) {
      this.resetTypeDropdown = false;
      switch (dropdownSelected.value) {
        case "Sector":
          this.$emit("onSectorAnalysisSelect");
          break;
        case "Category":
          this.$emit("onCategoryAnalysisSelect");
          break;
      }
    },

    onSettingsClick() {
      this.$emit("onSettingsClick");
    },

    onOneAnalysisDropdownSelect(selectedDropdown) {
      this.$emit("onOneAnalysisDropdownSelect", selectedDropdown);
    },

    onAnalysisServiceDropdownSelect(selectedDropdown) {
      this.resetAnalysisServiceDropdown = false;
      this.$emit("onAnalysisServiceSelect", selectedDropdown);
    },

    onBackButtonClick() {
      this.$emit("onBackButtonClick");
    }
  }
}
</script>
