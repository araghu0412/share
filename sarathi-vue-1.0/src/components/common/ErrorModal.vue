<template>
  <div class="mm-error-modal">
    <div class="mm-error-modal-backdrop">
      <div class="mm-error-modal-dialog">
        <div class="mm-error-modal-content">
          <div class="mm-error-modal-header">
            <slot name="header"></slot>
          </div>
          <div
            class="mm-error-modal-body"
          >
            <slot name="body">
              {{ errorMessage }}
            </slot>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ErrorModal",
  props: {
    errorMessage: {
      type: String,
      required: false
    }
  }
}
</script>
