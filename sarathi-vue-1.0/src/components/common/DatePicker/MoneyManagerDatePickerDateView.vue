<template>
  <div class="date-picker-date-view">
    <div class="date-picker-top-container">
      <span
        class="date-picker-top"
        @click="onPreviousMonthClick()"
      ><i class="fas fa-caret-left"></i></span>
      <span
        class="date-picker-top"
        @click="onMonthClick()"
      >{{ displayMonth }}</span>
      <span
        class="date-picker-top"
        @click="onYearClick()"
      >{{ displayYear }}</span>
      <span
        class="date-picker-top"
        @click="onNextMonthClick()"
      ><i class="fas fa-caret-right"></i></span>
    </div>
    <div class="date-picker-table">
      <table>
        <tr>
          <td v-for="[key, value] of dayMap" :key="key" class="date-picker-day">{{ value }}</td>
        </tr>
        <tr v-for="calendarDay of calendarDaysList" :key="calendarDay.weekId">
          <td
            v-for="weeklyDay of calendarDay.weeklyDaysList" :key="weeklyDay.id"
            :id="[weeklyDay.value === selectedDate && displayMonth === selectedMonth && displayYear === selectedYear ? 'date-picker-date-id' : '']"
            :class="[weeklyDay.value !== '' ? 'date-picker-date' : '']"
            @click="onDateClick(weeklyDay.value)"
          >
            {{ weeklyDay.value }}
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    dayMap: {
      type: Map,
      required: true
    },
    displayMonth: {
      type: String,
      required: true
    },
    displayYear: {
      type: String,
      required: true
    },
    calendarDaysList: {
      type: Array,
      required: true
    },
    selectedDate: {
      type: String,
      required: true
    },
    selectedMonth: {
      type: String,
      required: true
    },
    selectedYear: {
      type: String,
      required: true
    }
  },

  methods: {
    onDateClick(date) {
      if (date !== "") {
        this.$emit("onDateSelect", {date: date, month: this.displayMonth, year: this.displayYear});
      }
    },

    onPreviousMonthClick() {
      this.$emit("onPreviousMonth");
    },

    onNextMonthClick() {
      this.$emit("onNextMonth");
    },

    onMonthClick() {
      this.$emit("onMonthClick");
    },

    onYearClick() {
      this.$emit("onYearClick");
    }
  }
}
</script>
