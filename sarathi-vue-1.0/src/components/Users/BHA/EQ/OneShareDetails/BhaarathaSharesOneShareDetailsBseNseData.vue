<template>
  <div class="bhaaratha-shares-one-share-details-bse-nse-data">
    <div class="services-one-script-details-exchange-data">
      <div class="services-one-script-exchange-form">
        <div class="key">Script Name</div>
        <div class="value">{{ oneShareDetails.scriptName }}</div>
      </div>

      <div class="services-one-script-exchange-form">
        <div class="key">Script Code</div>
        <div class="value">{{ oneShareDetails.scriptCode }}</div>
      </div>

      <div class="services-one-script-exchange-form">
        <div class="key">Current Market Price</div>
        <div class="value">{{ oneShareDetails.currentMarketPrice }} [ <span v-highlight-by-numbers="'bold'">{{ oneShareDetails.currentMarketPriceChange }}</span> ( <span v-highlight-by-numbers="'bold'">{{ oneShareDetails.currentMarketPriceChangePercentage }}%</span> ) ]</div>
      </div>

      <div class="services-one-script-exchange-form">
        <div class="key">Previous Close</div>
        <div class="value">{{ oneShareDetails.previousClose }}</div>
      </div>

      <div class="services-one-script-exchange-form">
        <div class="key">Open Price</div>
        <div class="value">{{ oneShareDetails.openPrice }}</div>
      </div>

      <div
        v-if="oneShareDetails.dailyHighLowValue"
        class="services-one-script-exchange-form"
      >
        <div class="key">Today's High/Low</div>
        <div class="value">
          <MoneyManagerMinMaxBar
            :lowValue="oneShareDetails.dailyLow"
            :highValue="oneShareDetails.dailyHigh"
            :highLowValue="oneShareDetails.dailyHighLowValue"
          ></MoneyManagerMinMaxBar>
        </div>
      </div>

      <div
        v-if="oneShareDetails.fiftyTwoWeekHighLowValue"
        class="services-one-script-exchange-form"
      >
        <div class="key">52 Week High/Low</div>
        <div class="value">
          <MoneyManagerMinMaxBar
            :lowValue="oneShareDetails.fiftyTwoWeekLow"
            :highValue="oneShareDetails.fiftyTwoWeekHigh"
            :highLowValue="oneShareDetails.fiftyTwoWeekHighLowValue"
          ></MoneyManagerMinMaxBar>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MoneyManagerMinMaxBar from '@/components/common/MoneyManagerMinMaxBar.vue';

export default {
  components: {
    MoneyManagerMinMaxBar
  },

  props: {
    oneShareDetails: {
      type: Object,
      required: true
    }
  }
}
</script>
