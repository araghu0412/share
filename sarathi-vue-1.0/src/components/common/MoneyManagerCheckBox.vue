<template>
  <span
    class="mm-checkbox"
    v-for="checkBox of checkBoxList"
    :key="checkBox.id"
  >
    <span class="mm-checkbox-custom">
      <label class="mm-checkbox-label" :class="[checkBox.isDisabled ? 'mm-checkbox-label-disabled' : '']">
        <input
          type="checkbox"
          :value="checkBox"
          :disabled="checkBox.isDisabled"
          v-model="selectedCheckBoxList"
          @change="onCheckBoxSelect(selectedCheckBoxList)"
        />
        <span>{{ " " + checkBox.value }}</span>
      </label>
    </span>
  </span>
</template>

<script>
export default {
  name: "MoneyManagerCheckBox",
  emits: ["onCheckBoxSelect"],
  data() {
    return {
      selectedCheckBoxList: []
    }
  },
  props: {
    resetCheckBox: {
      type: Boolean,
      required: true
    },
    checkBoxList: {
      type: Array,
      required: true
    },
    defaultCheckBoxList: {
      type: Array,
      default: () => []
    }
  },

  watch: {
    resetCheckBox(newValue) {
      if (newValue) {
        this.selectedCheckBoxList = this.defaultCheckBoxList;
      }
    }
  },

  created() {
    for (let checkBox of this.defaultCheckBoxList) {
      this.selectedCheckBoxList.push(checkBox);
    }
  },
  methods: {
    onCheckBoxSelect(selectedCheckBoxList) {
      this.$emit("onCheckBoxSelect", selectedCheckBoxList);
    }
  }
}
</script>
