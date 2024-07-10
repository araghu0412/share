<template>
  <span class="mm-radio" v-for="radio of radioList" :key="radio.id">
    <span class="mm-radio-custom">
      <label class="mm-radio-label">
        <input
          type="radio"
          :value="radio"
          @change="onRadioSelect"
          v-model="radioSelected"
        />
        <span>{{ " " + radio.value }}</span>
      </label>
    </span>
  </span>
</template>

<script>
export default {
  name: "MoneyManagerRadio",
  emits: ["onRadioSelect"],

  created() {
    if (this.radioDefault) {
      this.radioSelected = this.radioDefault;
    } else if (!this.radioDefault) {
      this.radioSelected = {};
    }
  },

  data() {
    return {
      radioSelected: {},
    };
  },
  props: {
    resetRadio: {
      type: Boolean,
      required: true
    },
    radioList: {
      type: Array,
      required: true,
    },
    radioDefault: {
      type: Object,
      required: false
    }
  },
  watch: {
    resetRadio(newValue) {
      if(newValue) {
        if (this.radioDefault) {
          this.radioSelected = this.radioDefault;
        } else {
          this.radioSelected = {};
        }
      }
    }
  },
  methods: {
    onRadioSelect() {
      this.$emit("onRadioSelect", this.radioSelected);
    },
  },
};
</script>
