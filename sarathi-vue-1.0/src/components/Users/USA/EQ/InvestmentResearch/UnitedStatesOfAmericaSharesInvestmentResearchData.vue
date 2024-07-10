<template>
  <div class="united-states-of-america-shares-investment-research-data">
    <div class="services-investment-research-data">
      <div class="spinner" v-if="loading">
        <MoneyManagerLoading></MoneyManagerLoading>
      </div>
      <div v-if="noRecords" class="no-records">
        ******* NO RECORDS *******
      </div>
      <div v-if="!loading && !noRecords">
        <div class="services-investment-research-table">
          <div class="mm-table">
            <table class="mm-table-data">
              <thead>
                <tr>
                  <th
                    class="mm-table-sort"
                    @click="sortInvestmentResearch('scriptName')"
                  >
                    SCRIPT NAME
                    <span v-if="sortingColumn === 'scriptName' && sortingType === 'ASC'">
                      <i class="fas fa-long-arrow-alt-up"></i>
                    </span>
                    <span v-if="sortingColumn === 'scriptName' && sortingType === 'DESC'">
                      <i class="fas fa-long-arrow-alt-down"></i>
                    </span>
                    <span v-if="sortingColumn !== 'scriptName'" class="transparent-sort-symbol">
                      <i class="fas fa-long-arrow-alt-up"></i>
                    </span>
                  </th>
                  <th
                    class="mm-table-sort"
                    @click="sortInvestmentResearch('dailyRange')"
                  >
                    DAILY RANGE
                    <span v-if="sortingColumn === 'dailyRange' && sortingType === 'ASC'">
                      <i class="fas fa-long-arrow-alt-up"></i>
                    </span>
                    <span v-if="sortingColumn === 'dailyRange' && sortingType === 'DESC'">
                      <i class="fas fa-long-arrow-alt-down"></i>
                    </span>
                    <span v-if="sortingColumn !== 'dailyRange'" class="transparent-sort-symbol">
                      <i class="fas fa-long-arrow-alt-up"></i>
                    </span>
                  </th>
                  <th
                    class="mm-table-sort"
                    @click="sortInvestmentResearch('52WRange')"
                  >
                    52W RANGE
                    <span v-if="sortingColumn === '52WRange' && sortingType === 'ASC'">
                      <i class="fas fa-long-arrow-alt-up"></i>
                    </span>
                    <span v-if="sortingColumn === '52WRange' && sortingType === 'DESC'">
                      <i class="fas fa-long-arrow-alt-down"></i>
                    </span>
                    <span v-if="sortingColumn !== '52WRange'" class="transparent-sort-symbol">
                      <i class="fas fa-long-arrow-alt-up"></i>
                    </span>
                  </th>
                  <th
                    class="mm-table-sort"
                    @click="sortInvestmentResearch('ttmDividendYield')"
                  >
                    TTM Dividend Yield
                    <span v-if="sortingColumn === 'ttmDividendYield' && sortingType === 'ASC'">
                      <i class="fas fa-long-arrow-alt-up"></i>
                    </span>
                    <span v-if="sortingColumn === 'ttmDividendYield' && sortingType === 'DESC'">
                      <i class="fas fa-long-arrow-alt-down"></i>
                    </span>
                    <span v-if="sortingColumn !== 'ttmDividendYield'" class="transparent-sort-symbol">
                      <i class="fas fa-long-arrow-alt-up"></i>
                    </span>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="investmentResearch of investmentResearchList" :key="investmentResearch.bseCode">
                  <td>{{ investmentResearch.scriptName }}</td>
                  <td>{{ investmentResearch.dailyRange }}%</td>
                  <td>{{ investmentResearch.fiftyTwoWeekRange }}%</td>
                  <td>{{ investmentResearch.ttmDividendYield }}%</td>
                </tr>
              </tbody>
            </table>
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
    loading: {
      type: Boolean,
      required: true
    },
    noRecords: {
      type: Boolean,
      required: true
    },
    investmentResearchList: {
      type: Array,
      required: true
    },
    sortingColumn: {
      type: String,
      required: false
    },
    sortingType: {
      type: String,
      required: false
    }
  },

  methods: {
    sortInvestmentResearch(column) {
      this.$emit("sortInvestmentResearch", column);
    }
  }
}
</script>
