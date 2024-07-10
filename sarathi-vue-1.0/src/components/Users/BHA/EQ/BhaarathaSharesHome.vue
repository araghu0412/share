<template>
  <div class="bhaaratha-shares-home">
    <BhaarathaSharesHomeHeader
      :otherServicesDropdownResetDropdown="otherServicesDropdownResetDropdown"
      :otherServicesDropdownList="otherServicesDropdownList"
      @onOtherServicesDropdownSelect="onOtherServicesDropdownSelect($event)"
    ></BhaarathaSharesHomeHeader>
  </div>
</template>

<script>
import BhaarathaSharesHomeHeader from "@/components/Users/BHA/EQ/Home/BhaarathaSharesHomeHeader.vue";

export default {
  name: "BhaarathaSharesHome",
  components: {
    BhaarathaSharesHomeHeader
  },

  data() {
    return {
      otherServicesDropdownResetDropdown: false,
      otherServicesDropdownList: []
    }
  },

  created() {
    this.otherServicesDropdownList = [
      {
        id: "short-term-investment",
        value: "Short Term Investment"
      },
      {
        id: "bulk-upload",
        value: "Bulk Upload"
      }
    ];
  },

  methods: {
    onOtherServicesDropdownSelect(otherServicesDropdownSelected) {
      this.otherServicesDropdownResetDropdown = false;
      this.$router.push("/users/BHA/EQ/" + otherServicesDropdownSelected.id);
    }
  }
}
</script>
