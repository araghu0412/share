<template>
  <div class="united-states-of-america-shares-short-term-investment-data">
    <div class="services-short-term-investment-data">
      <div v-if="loading" class="spinner">
        <MoneyManagerLoading></MoneyManagerLoading>
      </div>
      <div v-else-if="noRecords" class="no-records">
        <div>
          ******* NO RECORDS *******
        </div>
      </div>
      <div v-else>
        <div class="services-short-term-investment-table">
          <div class="mm-table">
            <table class="mm-table-data">
              <thead>
                <tr>
                  <th>SCRIPT NAME</th>
                  <th v-if="!oneShareShortTermInvestment"></th>
                  <th>PURCHASE PRICE PER UNIT</th>
                  <th>PURCHASE TOTAL</th>
                  <th>SELL PRICE PER UNIT</th>
                  <th>SELL TOTAL</th>
                  <th>P/L</th>
                  <th>P/L (%)</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="shortTermInvestment of shortTermInvestmentDataList" :key="shortTermInvestment.id">
                  <td>
                    <template v-if="!oneShareShortTermInvestment">
                      <span class="link" @click="onShortTermInvestmentScriptClick(shortTermInvestment.yahooCode)">
                        {{ shortTermInvestment.scriptName }}
                      </span>
                    </template>
                    <template v-else>
                      <span>
                        {{ shortTermInvestment.scriptName }}
                      </span>
                    </template>
                  </td>
                  <td v-if="!oneShareShortTermInvestment">
                    <span
                      class="money-manager-delete-icon"
                      @click="onDeleteShortTermInvestmentClick(shortTermInvestment.yahooCode, shortTermInvestment.scriptName)"
                    >
                      <i class="fa fa-trash"></i>
                    </span>
                  </td>
                  <td>{{ shortTermInvestment.purchasePrice }}</td>
                  <td>{{ shortTermInvestment.purchaseTotal }}</td>
                  <td>{{ shortTermInvestment.sellPrice }}</td>
                  <td>{{ shortTermInvestment.sellTotal }}</td>
                  <td v-highlight-by-numbers>{{ shortTermInvestment.profitLoss }}</td>
                  <td v-highlight-by-numbers>{{ shortTermInvestment.profitLossPercentage }}%</td>
                </tr>
              </tbody>
              <tfoot>
                <tr>
                  <td>TOTAL</td>
                  <td v-if="!oneShareShortTermInvestment"></td>
                  <td>{{ shortTermInvestmentTotal.purchasePrice }}</td>
                  <td>{{ shortTermInvestmentTotal.purchaseTotal }}</td>
                  <td>{{ shortTermInvestmentTotal.sellPrice }}</td>
                  <td>{{ shortTermInvestmentTotal.sellTotal }}</td>
                  <td v-highlight-by-numbers>{{ shortTermInvestmentTotal.profitLoss }}</td>
                  <td v-highlight-by-numbers>{{ shortTermInvestmentTotal.profitLossPercentage }}%</td>
                </tr>
              </tfoot>
            </table>
          </div>
        </div>
        <div class="services-short-term-investment-returns-content">
          Your short term investment has provided a total return of <span v-highlight-by-numbers="'bold'">{{ shortTermInvestmentTotal.profitLoss }}</span> (<span v-highlight-by-numbers="'bold'">{{ shortTermInvestmentTotal.profitLossPercentage }}%</span>)
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MoneyManagerLoading from "@/components/common/MoneyManagerLoading.vue";

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
    oneShareShortTermInvestment: {
      type: Boolean,
      default: false
    },
    shortTermInvestmentDataList: {
      type: Array,
      required: true
    },
    shortTermInvestmentTotal: {
      Type: Object,
      required: true
    }
  },

  methods: {
    onShortTermInvestmentScriptClick(yahooCode) {
      this.$emit("onShortTermInvestmentScriptClick", yahooCode);
    },

    onDeleteShortTermInvestmentClick(yahooCode, scriptName) {
      this.$emit("onDeleteShortTermInvestmentClick", { yahooCode: yahooCode, scriptName: scriptName });
    }
  }
}
</script>
