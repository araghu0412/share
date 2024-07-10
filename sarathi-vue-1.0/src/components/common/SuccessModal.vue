<template>
  <div class="mm-success-modal">
    <div class="mm-success-modal-backdrop">
      <div class="mm-success-modal-dialog">
        <div class="mm-success-modal-content">
          <div class="mm-success-modal-header">
            <slot name="header"></slot>
          </div>
          <div
            class="mm-success-modal-body"
          >
            <slot name="body">
              {{ successMessage }}
            </slot>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SuccessModal",
  props: {
    successMessage: {
      type: String,
      required: false
    }
  }
}
</script>
