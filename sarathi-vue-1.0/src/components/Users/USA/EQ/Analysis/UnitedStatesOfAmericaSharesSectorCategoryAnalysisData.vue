<template>
  <div class="united-states-of-america-shares-sector-category-analysis-data">
    <div class="services-analysis-sector-category-data">
      <div class="spinner" v-if="loading">
        <MoneyManagerLoading></MoneyManagerLoading>
      </div>
      <div v-if="noRecords" class="no-records">
        ******* NO RECORDS *******
      </div>
      <div v-if="!loading && !noRecords">
        <div class="services-analysis-chart">
          <apexchart height="500" :options="chartOptions" :series="chartSeries"></apexchart>
        </div>
        <div class="services-analysis-content">
          With <b>{{ total.numberOfScripts }}</b> number of script/s, the invested value is <b>{{ total.investedValue }}</b> and the current value is <b>{{ total.currentValue }}</b>. Your portfolio has provided a total return of <span v-highlight-by-numbers="'bold'">{{ total.profitLoss }}</span> (<span v-highlight-by-numbers="'bold'">{{ total.profitLossPercentage }}%</span>)
          </div>
      </div>
    </div>
  </div>
</template>

<script>
import MoneyManagerLoading from '@/components/common/MoneyManagerLoading.vue';

export default {
  components: {
    MoneyManagerLoading
  },

  props: {
    loading: {
      type: Boolean,
      required: true
    },
    noRecords: {
      type: Boolean,
      required: true
    },
    chartSeries: {
      type: Array,
      required: true
    },
    chartOptions: {
      type: Object,
      required: true
    },
    total: {
      type: Object,
      required: true
    }
  }
}
</script>
