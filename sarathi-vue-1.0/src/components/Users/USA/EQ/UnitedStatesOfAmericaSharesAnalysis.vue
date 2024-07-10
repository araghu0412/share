<template>
  <div class="united-states-of-america-shares-analysis">
    <UnitedStatesOfAmericaSharesAnalysisServicesHeader
      :resetAnalysisServicesDropdown="resetAnalysisServicesDropdown"
      :analysisServicesDropdownList="analysisServicesDropdownList"
      @onAnalysisServicesDropdownSelect="onAnalysisServicesDropdownSelect($event)"
    ></UnitedStatesOfAmericaSharesAnalysisServicesHeader>
  </div>
</template>

<script>
import UnitedStatesOfAmericaSharesAnalysisServicesHeader from "@/components/Users/USA/EQ/Analysis/UnitedStatesOfAmericaSharesAnalysisServicesHeader.vue";

export default {
  components: {
    UnitedStatesOfAmericaSharesAnalysisServicesHeader
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
      this.$router.push("/users/USA/EQ/" + selectedAnalysisService.id);
    }
  }
}
</script>
