<template>
  <div class="bhaaratha-shares-one-share-details-data">
    <div class="services-one-script-details-data">
      <div class="spinner" v-if="loading">
        <MoneyManagerLoading></MoneyManagerLoading>
      </div>
      <div v-if="noRecords" class="no-records">
        ******* NO RECORDS *******
      </div>
      <div v-if="!loading && !noRecords">
        <div class="services-one-script-details-table">
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
                  <td>{{ bhaarathaShares.scriptName }}</td>
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
          <div class="services-one-script-details-content">
            Your portfolio has provided a total return of <span v-highlight-by-numbers="'bold'">{{ bhaarathaSharesTotal.totalProfitLoss }}</span> (<span v-highlight-by-numbers="'bold'">{{ bhaarathaSharesTotal.totalProfitLossPercentage }}%</span>) and per unit net return of <span v-highlight-by-numbers="'bold'">{{ bhaarathaSharesTotal.netProfitLossPerUnit }}</span>
          </div>
          <hr />
          <div class="services-one-script-exchange-details">
            <div class="services-one-script-exchange-radio">
              <MoneyManagerRadio
                :resetRadio="false"
                :radioList="stockExchangeRadioList"
                :radioDefault="stockExchangeRadioDefault"
                @onRadioSelect="onStockExchangeSelect($event)"
              ></MoneyManagerRadio>
            </div>

            <div class="services-one-script-exchange-content">
              <BhaarathaSharesOneShareDetailsBseNseData
                v-if="stockExchangeSelected === 'BSE'"
                :oneShareDetails="bseOneShareDetails"
              ></BhaarathaSharesOneShareDetailsBseNseData>
              <BhaarathaSharesOneShareDetailsBseNseData
                v-if="stockExchangeSelected === 'NSE'"
                :oneShareDetails="nseOneShareDetails"
              ></BhaarathaSharesOneShareDetailsBseNseData>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MoneyManagerLoading from "@/components/common/MoneyManagerLoading.vue";
import MoneyManagerRadio from "@/components/common/MoneyManagerRadio.vue";
import BhaarathaSharesOneShareDetailsBseNseData from "@/components/Users/BHA/EQ/OneShareDetails/BhaarathaSharesOneShareDetailsBseNseData.vue"

export default {
  components: {
    MoneyManagerLoading,
    MoneyManagerRadio,
    BhaarathaSharesOneShareDetailsBseNseData
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
    pageRefreshed: {
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
    },
    bseOneShareDetails: {
      type: Object,
      required: true
    },
    nseOneShareDetails: {
      type: Object,
      required: true
    }
  },

  data () {
    return {
     stockExchangeRadioList: [],
     stockExchangeRadioDefault: {},
     stockExchangeSelected: ""
   }
  },

  created() {
    this.getStockExchangeRadioList();
    this.getStockExchangeRadioDefault();
    this.stockExchangeSelected = "BSE";
  },

  watch: {
    pageRefreshed(newValue) {
      if (newValue) {
        this.getStockExchangeRadioList();
        this.getStockExchangeRadioDefault();
        this.stockExchangeSelected = "BSE";
      }
    }
  },

  methods: {
    getStockExchangeRadioList() {
      this.stockExchangeRadioList = [
        {
          id: "bse",
          value: "BSE"
        },
        {
          id: "nse",
          value: "NSE"
        }
      ];
    },

    getStockExchangeRadioDefault() {
      this.stockExchangeRadioDefault = this.stockExchangeRadioList[0];
    },

    onStockExchangeSelect(stockExchange) {
      this.stockExchangeSelected = stockExchange.value
    }
  }
}
</script>
