<template>
  <button
    class="btn"
    :class="[buttonDisabled ? 'btn-disabled' : '', buttonClass]"
    :disabled="buttonDisabled"
    @click="onButtonClick()"
  >
    <slot name="title">
      {{ buttonTitle }}
    </slot>
  </button>
</template>

<script>
export default {
  props: {
    buttonTitle: {
      type: String,
      required: false,
    },
    buttonDisabled: {
      type: Boolean,
      default: false,
    },
    buttonClass: {
      type: String,
      default: "",
    },
  },
  methods: {
    onButtonClick() {
      this.$emit("onButtonClick");
    },
  },
};
</script>
