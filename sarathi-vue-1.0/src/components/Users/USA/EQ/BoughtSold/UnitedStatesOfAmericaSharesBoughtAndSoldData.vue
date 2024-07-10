<template>
  <div class="united-states-of-america-shares-bought-and-sold-data">
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
                <tr v-for="unitedStatesOfAmericaShares of unitedStatesOfAmericaSharesList" :key="unitedStatesOfAmericaShares.id">
                  <td>
                    <span class="link" @click="onUnitedStatesOfAmericaSharesScriptClick($event, unitedStatesOfAmericaShares.scriptCode)">
                      {{ unitedStatesOfAmericaShares.scriptName }}
                    </span>
                  </td>
                  <td>{{ unitedStatesOfAmericaShares.purchasePrice }}</td>
                  <td>{{ unitedStatesOfAmericaShares.purchaseTotal }}</td>
                  <td>{{ unitedStatesOfAmericaShares.purchaseNetPricePerUnit }}</td>
                  <td>{{ unitedStatesOfAmericaShares.sellPrice }}</td>
                  <td>{{ unitedStatesOfAmericaShares.sellTotal }}</td>
                  <td>{{ unitedStatesOfAmericaShares.sellNetPricePerUnit }}</td>
                  <td v-highlight-by-numbers>{{ unitedStatesOfAmericaShares.totalProfitLoss }}</td>
                  <td v-highlight-by-numbers>{{ unitedStatesOfAmericaShares.totalProfitLossPercentage }}%</td>
                  <td v-highlight-by-numbers>{{ unitedStatesOfAmericaShares.netProfitLossPerUnit }}</td>
                </tr>
              </tbody>
              <tfoot>
                <tr>
                  <td>TOTAL</td>
                  <td>{{ unitedStatesOfAmericaSharesTotal.purchasePrice }}</td>
                  <td>{{ unitedStatesOfAmericaSharesTotal.purchaseTotal }}</td>
                  <td>{{ unitedStatesOfAmericaSharesTotal.purchaseNetPricePerUnit }}</td>
                  <td>{{ unitedStatesOfAmericaSharesTotal.sellPrice }}</td>
                  <td>{{ unitedStatesOfAmericaSharesTotal.sellTotal }}</td>
                  <td>{{ unitedStatesOfAmericaSharesTotal.sellNetPricePerUnit }}</td>
                  <td v-highlight-by-numbers>{{ unitedStatesOfAmericaSharesTotal.totalProfitLoss }}</td>
                  <td v-highlight-by-numbers>{{ unitedStatesOfAmericaSharesTotal.totalProfitLossPercentage }}%</td>
                  <td v-highlight-by-numbers>{{ unitedStatesOfAmericaSharesTotal.netProfitLossPerUnit }}</td>
                </tr>
              </tfoot>
            </table>
          </div>
          <div class="services-bought-sold-content">
            Your portfolio has provided a total return of <span v-highlight-by-numbers="'bold'">{{ unitedStatesOfAmericaSharesTotal.totalProfitLoss }}</span> (<span v-highlight-by-numbers="'bold'">{{ unitedStatesOfAmericaSharesTotal.totalProfitLossPercentage }}%</span>) and per unit net return of <span v-highlight-by-numbers="'bold'">{{ unitedStatesOfAmericaSharesTotal.netProfitLossPerUnit }}</span>
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
    unitedStatesOfAmericaSharesList: {
      type: Array,
      required: true
    },
    unitedStatesOfAmericaSharesTotal: {
      type: Object,
      required: true
    }
  },

  methods: {
    onUnitedStatesOfAmericaSharesScriptClick(event, scriptCode) {
      this.$router.push("/users/USA/EQ/one-share?type=" + this.type + "&scriptCode=" + scriptCode);
    }
  }
}
</script>
