<template>
  <div class="app">
    <MoneyManagerHeader></MoneyManagerHeader>
    <router-view></router-view>
  </div>
</template>

<script>
import MoneyManagerHeader from "@/components/MoneyManagerHeader.vue";

export default {
  name: "MoneyManager",
  components: {
    MoneyManagerHeader
  }
};
</script>
