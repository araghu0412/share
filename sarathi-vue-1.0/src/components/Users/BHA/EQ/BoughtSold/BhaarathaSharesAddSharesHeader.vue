<template>
  <div class="bhaaratha-shares-add-shares-header">
    <div class="services-add-scripts-header">
      <div class="header">
        <MoneyManagerButton
          :buttonClass="'navigation-button btn-sm'"
          @onButtonClick="onBackButtonClick()"
        >
          <template v-slot:title>
            <i class="fas fa-long-arrow-alt-left"></i> Back
          </template>
        </MoneyManagerButton>
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
      this.$emit("onBackButtonClicked")
    }
  }
}
</script>
