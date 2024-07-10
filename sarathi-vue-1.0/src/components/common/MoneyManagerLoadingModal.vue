<template>
  <div class="mm-loading-modal">
    <div class="mm-loading-modal-backdrop">
      <div class="mm-loading-modal-dialog">
        <div class="mm-loading-modal-content">
            <div class="mm-loading-modal-header">
              ******* {{ headingDisplay }} *******
            </div>
            <div class="mm-loading-modal-body">
              <MoneyManagerLoading></MoneyManagerLoading>
            </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MoneyManagerLoading from "@/components/common/MoneyManagerLoading.vue";

export default {
  name: "MoneyManagerLoadingModal",

  components: {
    MoneyManagerLoading
  },

  props: {
    headingDisplay: {
      type: String,
      default: () => "Your patience is greatly valued"
    }
  }
}
</script>
