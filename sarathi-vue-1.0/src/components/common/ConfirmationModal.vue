<template>
  <div class="mm-confirmation">
    <div class="mm-confirmation-modal">
      <div class="mm-confirmation-modal-backdrop">
        <div class="mm-confirmation-modal-dialog">
          <div class="mm-confirmation-modal-content">
            <div class="mm-confirmation-modal-header">
              <h3 class="mm-confirmation-modal-title">
                Do you want to continue?
              </h3>
            </div>
            <div
              class="mm-confirmation-modal-body"
            >
              <div class="mm-confirmation-modal-body-content">
                <slot name="body">
                  {{ confirmationMessage }}
                </slot>
              </div>
            </div>
            <div class="mm-confirmation-modal-footer">
              <div class="mm-confirmation-close">
                <span>
                  <MoneyManagerButton
                    :buttonTitle="'Close'"
                    :buttonClass="'btn btn-danger'"
                    @onButtonClick="onCloseClick()"
                  ></MoneyManagerButton>
                </span>
                <div class="mm-confirmation-continue">
                  <MoneyManagerButton
                    :buttonTitle="'Continue'"
                    :buttonClass="'btn btn-primary'"
                    @onButtonClick="onContinueClick()"
                  ></MoneyManagerButton>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";

export default {
  name: "ConfirmationModal",
  
  components: {
    MoneyManagerButton
  },

  props: {
    confirmationMessage: {
      type: String,
      required: false
    }
  },

  methods: {
    onCloseClick() {
      this.$emit("onCloseConfirmation")
    },

    onContinueClick() {
      this.$emit("onConfirmationContinue")
    }
  }
}
</script>
