<template>
  <div class="bhaaratha-shares-home-header">
    <div class="services-home-header">
      <MoneyManagerDropdown
        :resetDropdown="otherServicesDropdownResetDropdown"
        :dropdownList="otherServicesDropdownList"
        :placeholder="'Other Services'"
        @onDropdownSelect="onOtherServicesDropdownSelect($event)"
      ></MoneyManagerDropdown>
    </div>
  </div>
</template>

<script>
import MoneyManagerDropdown from "@/components/common/MoneyManagerDropdown.vue";

export default {
  components: {
    MoneyManagerDropdown
  },

  props: {
    otherServicesDropdownResetDropdown: {
      type: Boolean,
      required: true
    },
    otherServicesDropdownList: {
      type: Array,
      required: true
    }
  },

  methods: {
    onOtherServicesDropdownSelect(dropdownServicesSelected) {
      this.$emit('onOtherServicesDropdownSelect', dropdownServicesSelected);
    }
  }
}
</script>
