<template>
  <span class="input-field-error">{{ errorMessage }}</span>
</template>

<script>
export default {
  name: "InputFieldError",
  props: {
    errorMessage: {
      type: String,
      required: true
    }
  }
}
</script>
