<template>
  <div class="united-states-of-america-shares-home">
    <UnitedStatesOfAmericaSharesHomeHeader
      :otherServicesDropdownResetDropdown="otherServicesDropdownResetDropdown"
      :otherServicesDropdownList="otherServicesDropdownList"
      @onOtherServicesDropdownSelect="onOtherServicesDropdownSelect($event)"
    ></UnitedStatesOfAmericaSharesHomeHeader>
  </div>
</template>

<script>
import UnitedStatesOfAmericaSharesHomeHeader from "@/components/Users/USA/EQ/Home/UnitedStatesOfAmericaSharesHomeHeader.vue";

export default {
  name: "UnitedStatesOfAmericaSharesHome",
  components: {
    UnitedStatesOfAmericaSharesHomeHeader
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
      }
    ];
  },

  methods: {
    onOtherServicesDropdownSelect(otherServicesDropdownSelected) {
      this.otherServicesDropdownResetDropdown = false;
      this.$router.push("/users/USA/EQ/" + otherServicesDropdownSelected.id);
    }
  }
}
</script>
