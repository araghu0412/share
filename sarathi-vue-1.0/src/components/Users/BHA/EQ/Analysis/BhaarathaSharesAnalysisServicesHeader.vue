<template>
  <div class="bhaaratha-shares-analysis-services-header">
    <div class="services-analysis-header">
      <MoneyManagerDropdown
        :resetDropdown="resetAnalysisServicesDropdown"
        :dropdownList="analysisServicesDropdownList"
        :placeholder="'Analysis Services'"
        @onDropdownSelect="onAnalysisServicesDropdownSelect($event)"
      ></MoneyManagerDropdown>
    </div>
  </div>
</template>

<script>
import MoneyManagerDropdown from "@/components/common/MoneyManagerDropdown.vue";

export default {
  components: {
    MoneyManagerDropdown
  },

  props: {
    resetAnalysisServicesDropdown: {
      type: Boolean,
      required: true
    },
    analysisServicesDropdownList: {
      type: Array,
      required: true
    }
  },

  methods: {
    onAnalysisServicesDropdownSelect(selectedAnalysisServices) {
      this.$emit("onAnalysisServicesDropdownSelect", selectedAnalysisServices);
    }
  }
}
</script>
