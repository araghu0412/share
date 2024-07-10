<template>
  <div class="date-picker-month-view">
    <div class="date-picker-top-container">
      <span class="date-picker-top">{{ displayMonth }}</span>
      <div class="date-picker-table">
        <div class="date-picker-month-heading">MONTH</div>
        <table>
          <tr v-for="calendarMonth of calendarMonthsList" :key="calendarMonth.rowId">
            <td
              v-for="monthList of calendarMonth.monthList"
              :key="monthList.monthId" 
              :id="[displayMonth === monthList.month ? 'date-picker-month-id' : '' ]"
              class="date-picker-month"
              @click="onMonthSelect(monthList.month)"
            >
              {{ monthList.month }}
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    calendarMonthsList: {
      type: Array,
      required: true
    },
    displayMonth: {
      type: String,
      required: true
    }
  },

  methods: {
    onMonthSelect(month) {
      this.$emit("onMonthSelect", month);
    }
  }
}
</script>
