<template>
  <div
    class="mm-dropdown"
    :tabindex="tabindex"
    @blur="onDropdownBlur()"
  >
    <span class="dropdown-class" @click="onDropdownClick()">
      <label
        class="dropdown-label"
        :class="[disableDropdown ? 'dropdown-disabled' : '']"
      >
        {{ valueSelected }}
        <span class="arrow">
          <span v-if="showDropdown" class="arrow-up"></span>
          <span v-if="!showDropdown" class="arrow-down"></span>
        </span>
      </label>
    </span>
    <div class="dropdown-value-position" v-if="showDropdown">
      <div v-for="dropdown of dropdownList" :key="dropdown.id">
        <div class="dropdown-value" @click="onDropdownValueClick(dropdown)">{{ dropdown.value }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MoneyManagerDropdown",
  data() {
    return {
      showDropdown: false,
      selectedValue: ""
    }
  },
  props: {
    disableDropdown: {
      type: Boolean,
      default: false
    },
    resetDropdown: {
      type: Boolean,
      required: true
    },
    placeholder: {
      type: String,
      default: "Select here..."
    },
    dropdownList: {
      type: Array,
      required: true
    },
    tabindex:{
      type: Number,
      required: false,
      default: 0
    }
  },
  computed: {
    valueSelected() {
      if (this.selectedValue.length > 15) {
        return this.selectedValue.substring(0, 15) + "...";
      }
      return this.selectedValue;
    }
  },
  watch: {
    resetDropdown(newValue) {
      if (newValue) {
        this.selectedValue = this.placeholder;
      }
    }
  },
  created() {
    this.selectedValue = this.placeholder;
  },
  methods: {
    onDropdownClick() {
      if (!this.disableDropdown) {
        this.showDropdown = !this.showDropdown;
      }
    },
    onDropdownValueClick(dropdown) {
      this.selectedValue = dropdown.value;
      this.showDropdown = false;
      this.$emit('onDropdownSelect', dropdown);
    },
    onDropdownBlur() {
      this.showDropdown = false;
    }
  }
}
</script>
