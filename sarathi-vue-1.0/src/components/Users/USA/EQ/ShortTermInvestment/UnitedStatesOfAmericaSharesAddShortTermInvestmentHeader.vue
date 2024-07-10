<template>
  <div class="united-states-of-america-shares-add-short-term-investment-header">
    <div class="services-add-short-term-investment-header">
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

  methods: {
    onBackButtonClick() {
      this.$emit("onBackButtonClick");
    }
  }
}
</script>
