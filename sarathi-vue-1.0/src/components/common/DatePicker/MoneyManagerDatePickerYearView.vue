<template>
  <div class="date-picker-year-view">
    <div class="date-picker-top-container">
      <span
        class="date-picker-top date-picker-navigate"
        @click="onPreviousYearListClick()"
      ><i class="fas fa-caret-left"></i></span>
      <span class="date-picker-top date-picker-year-combination">{{ displayStartYear + " - " + displayEndYear }}</span>
      <span
        class="date-picker-top date-picker-navigate"
        @click="onNextYearListClick()"
      ><i class="fas fa-caret-right"></i></span>
    </div>
    <div class="date-picker-table">
      <div class="date-picker-year-heading">YEAR</div>
      <table>
        <tr v-for="calendarYear of calendarYearsList" :key="calendarYear.rowId">
          <td
            v-for="yearList of calendarYear.yearList"
            :key="yearList.yearId"
            :id="[displayYear === yearList.year ? 'date-picker-year' : '']"
            class="date-picker-year"
            @click="onYearSelect(yearList.year)"
          >
            {{ yearList.year }}
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    displayYear: {
      type: String,
      required: true
    },
    calendarYearsList: {
      type: Array,
      required: true
    },
    displayStartYear: {
      type: String,
      required: true
    },
    displayEndYear: {
      type: String,
      required: true
    }
  },

  methods: {
    onPreviousYearListClick() {
      this.$emit("onPreviousYearList");
    },

    onNextYearListClick() {
      this.$emit("onNextYearList");
    },

    onYearSelect(year) {
      this.$emit("onYearSelect", year);
    }
  }
}
</script>
