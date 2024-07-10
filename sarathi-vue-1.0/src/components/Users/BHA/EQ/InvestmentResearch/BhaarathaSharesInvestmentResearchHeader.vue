<template>
  <div class="bhaaratha-shares-investment-research-header">
    <div class="services-investment-research-header">
      <div class="header">
        <span class="left-items">
          <MoneyManagerButton
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
              Refresh <i class="fa fa-refresh"></i>
            </template>
          </MoneyManagerButton>
        </span>
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
    }
  },

  methods: {
    onRefreshClick() {
      this.$emit("onRefreshClick");
    },

    onBackButtonClick() {
      this.$emit("onBackButtonClick");
    }
  }
}
</script>
