<template>
  <div class="bhaaratha-shares-bought-and-sold-data">
    <div class="services-bought-sold-data">
      <div class="spinner" v-if="loading">
        <MoneyManagerLoading></MoneyManagerLoading>
      </div>
      <div v-if="noRecords" class="no-records">
        ******* NO RECORDS *******
      </div>
      <div v-if="!loading && !noRecords">
        <div class="services-bought-sold-table">
          <div class="mm-table">
            <table class="mm-table-data">
              <thead>
                <tr>
                  <th>SCRIPT NAME</th>
                  <th>PURCHASE PRICE</th>
                  <th>PURCHASE TOTAL</th>
                  <th>PURCHASE NET</th>
                  <th>SELL PRICE</th>
                  <th>SELL TOTAL</th>
                  <th>SELL NET</th>
                  <th>P/L</th>
                  <th>P/L (%)</th>
                  <th>NET P/L</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="bhaarathaShares of bhaarathaSharesList" :key="bhaarathaShares.id">
                  <td>
                    <span class="link" @click="onBhaarathaSharesScriptClick($event, bhaarathaShares.bseScriptCode, bhaarathaShares.nseScriptCode, bhaarathaShares.moneycontrolCode, bhaarathaShares.yahooBseScriptCode, bhaarathaShares.yahooNseScriptCode)">
                      {{ bhaarathaShares.scriptName }}
                    </span>
                  </td>
                  <td>{{ bhaarathaShares.purchasePrice }}</td>
                  <td>{{ bhaarathaShares.purchaseTotal }}</td>
                  <td>{{ bhaarathaShares.purchaseNetPricePerUnit }}</td>
                  <td>{{ bhaarathaShares.sellPrice }}</td>
                  <td>{{ bhaarathaShares.sellTotal }}</td>
                  <td>{{ bhaarathaShares.sellNetPricePerUnit }}</td>
                  <td v-highlight-by-numbers>{{ bhaarathaShares.totalProfitLoss }}</td>
                  <td v-highlight-by-numbers>{{ bhaarathaShares.totalProfitLossPercentage }}%</td>
                  <td v-highlight-by-numbers>{{ bhaarathaShares.netProfitLossPerUnit }}</td>
                </tr>
              </tbody>
              <tfoot>
                <tr>
                  <td>TOTAL</td>
                  <td>{{ bhaarathaSharesTotal.purchasePrice }}</td>
                  <td>{{ bhaarathaSharesTotal.purchaseTotal }}</td>
                  <td>{{ bhaarathaSharesTotal.purchaseNetPricePerUnit }}</td>
                  <td>{{ bhaarathaSharesTotal.sellPrice }}</td>
                  <td>{{ bhaarathaSharesTotal.sellTotal }}</td>
                  <td>{{ bhaarathaSharesTotal.sellNetPricePerUnit }}</td>
                  <td v-highlight-by-numbers>{{ bhaarathaSharesTotal.totalProfitLoss }}</td>
                  <td v-highlight-by-numbers>{{ bhaarathaSharesTotal.totalProfitLossPercentage }}%</td>
                  <td v-highlight-by-numbers>{{ bhaarathaSharesTotal.netProfitLossPerUnit }}</td>
                </tr>
              </tfoot>
            </table>
          </div>
          <div class="services-bought-sold-content">
            Your portfolio has provided a total return of <span v-highlight-by-numbers="'bold'">{{ bhaarathaSharesTotal.totalProfitLoss }}</span> (<span v-highlight-by-numbers="'bold'">{{ bhaarathaSharesTotal.totalProfitLossPercentage }}%</span>) and per unit net return of <span v-highlight-by-numbers="'bold'">{{ bhaarathaSharesTotal.netProfitLossPerUnit }}</span>
          </div>
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
    type: {
      type: String,
      required: true
    },
    loading: {
      type: Boolean,
      required: true
    },
    noRecords: {
      type: Boolean,
      required: true
    },
    bhaarathaSharesList: {
      type: Array,
      required: true
    },
    bhaarathaSharesTotal: {
      type: Object,
      required: true
    }
  },

  methods: {
    onBhaarathaSharesScriptClick(event, bseCode, nseCode, moneycontrolCode, yahooBseCode, yahooNseCode) {
      this.$router.push({ path: "/users/BHA/EQ/one-share", query: { type: this.type, bseCode: bseCode, nseCode: encodeURIComponent(nseCode), moneycontrolCode: moneycontrolCode,  yahooBseCode: encodeURIComponent(yahooBseCode), yahooNseCode: encodeURIComponent(yahooNseCode) } });
    }
  }
}
</script>
