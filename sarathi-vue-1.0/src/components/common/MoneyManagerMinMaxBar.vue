<template>
  <div class="mm-min-max-bar">
    <div class="min-max-band">
      <div class="min">
        {{ lowValue }}
      </div>

      <div class="max">
        {{ highValue }}
      </div>

      <div class="min-max">
        <div
          class="min-max-range"
          :style="{'margin-left': 'calc(' + highLowValue + '%' + ' - 0.50rem)' }"
        >
          <div class="min-max-pointer"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    lowValue: {
      type: String,
      required: true
    },
    highValue: {
      type: String,
      required: true
    },
    highLowValue: {
      type: String,
      required: true
    }
  }
}
</script>
