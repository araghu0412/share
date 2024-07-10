<template>
  <div class="bhaaratha-shares-analysis">
    <BhaarathaSharesAnalysisServicesHeader
      :resetAnalysisServicesDropdown="resetAnalysisServicesDropdown"
      :analysisServicesDropdownList="analysisServicesDropdownList"
      @onAnalysisServicesDropdownSelect="onAnalysisServicesDropdownSelect($event)"
    ></BhaarathaSharesAnalysisServicesHeader>
  </div>
</template>

<script>
import BhaarathaSharesAnalysisServicesHeader from "@/components/Users/BHA/EQ/Analysis/BhaarathaSharesAnalysisServicesHeader.vue";

export default {
  components: {
    BhaarathaSharesAnalysisServicesHeader
  },

  data() {
    return {
      resetAnalysisServicesDropdown: false,
      analysisServicesDropdownList: [
        {
          id: "complete-analysis",
          value: "Complete Analysis"
        },
        {
          id: "dividend-yield-analysis",
          value: "Dividend Yield Analysis"
        },
        {
          id: "investment-research",
          value: "Investment Research"
        }
      ]
    }
  },

  methods: {
    onAnalysisServicesDropdownSelect(selectedAnalysisService) {
      this.$router.push("/users/BHA/EQ/" + selectedAnalysisService.id);
    }
  }
}
</script>
