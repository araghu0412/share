<template>
  <div class="bhaaratha-shares-short-term-investment-header">
    <div class="services-short-term-investment-header">
      <div class="header">
        <span class="left-items">
          <MoneyManagerButton v-if="oneShareShortTermInvestment"
            :buttonClass="'navigation-button btn-sm'"
            @onButtonClick="onBackButtonClick()"
          >
            <template v-slot:title>
              <i class="fas fa-long-arrow-alt-left"></i> Back
            </template>
          </MoneyManagerButton>
        </span>

        <span class="left-items">
          <MoneyManagerButton
            :buttonClass="isRefreshButtonDisabled ? 'btn-sm' : 'btn-primary btn-sm'"
            :buttonDisabled="isRefreshButtonDisabled"
            @onButtonClick="onRefreshClick()"
          >
            <template v-slot:title>
              <i class="fa fa-refresh"></i> Refresh
            </template>
          </MoneyManagerButton>
        </span>

        <div class="right">
          <span class="right-items">
            <MoneyManagerButton
              :buttonClass="'btn-buy btn-sm'"
              @onButtonClick="onAddShortTermInvestmentClick()"
            >
              <template v-slot:title>
                Add
              </template>
            </MoneyManagerButton>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";

export default {
  components: {
    MoneyManagerButton
  },

  props: {
    isRefreshButtonDisabled: {
      type: Boolean,
      required: true
    },

    oneShareShortTermInvestment: {
      type: Boolean,
      default: false
    }
  },

  methods: {
    onBackButtonClick() {
      this.$emit("onBackButtonClick");
    },

    onRefreshClick() {
      this.$emit("onRefreshClick");
    },

    onAddShortTermInvestmentClick() {
      this.$emit("onAddShortTermInvestmentClick");
    }
  }
}
</script>
