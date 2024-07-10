<template>
  <div>
    <hr>
    <router-link :to="'/'">Home</router-link>
    <hr>
    <MoneyManagerButton
      :buttonTitle="'Button Title'"
      :buttonClass="'btn-primary'"
      @onButtonClick="onButtonClick()"
    >
      <template v-slot:title> Title from Parent </template>
    </MoneyManagerButton>
    <hr />
    <MoneyManagerRadio
      :resetRadio="resetRadio"
      :radioList="radioList"
      :radioDefault="radioDefault"
      @onRadioSelect="onRadioSelect"
    ></MoneyManagerRadio>
    <hr />
    <MoneyManagerDropdown
      :resetDropdown="resetDropdown"
      :dropdownList="dropdownList"
      @onDropdownSelect="onDropdownSelect($event)"
    ></MoneyManagerDropdown>
    <hr />
    <MoneyManagerCheckBox
      :resetCheckBox="resetCheckBox"
      :checkBoxList="checkBoxList"
      :defaultCheckBoxList="defaultCheckBoxList"
      @onCheckBoxSelect="onCheckBoxSelect($event)"
    ></MoneyManagerCheckBox>
    <hr />
    <MoneyManagerDatePicker
      :resetDatePicker="resetDatePicker"
      :defaultDate="defaultDate"
      @onSelectedFullDate="onDateSelected($event)"
    ></MoneyManagerDatePicker>
    <hr />
    <MoneyManagerButton
      :buttonTitle="'Loading Modal'"
      :buttonClass="'btn-primary btn-sm'"
      @onButtonClick="onLoadingModalClick()"
    ></MoneyManagerButton>
    <hr />
    <MoneyManagerButton
      :buttonTitle="'Reset'"
      :buttonClass="'btn-primary btn-sm'"
      @onButtonClick="resetData()"
    ></MoneyManagerButton>
    <MoneyManagerButton
      :buttonTitle="'Submit'"
      :buttonClass="'btn-primary btn-sm'"
      @onButtonClick="submitData()"
    ></MoneyManagerButton>
    <hr />
    <MoneyManagerLoadingModal
      v-if="loadingModal"
    ></MoneyManagerLoadingModal>
  </div>
</template>

<script>
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import MoneyManagerRadio from "@/components/common/MoneyManagerRadio.vue";
import MoneyManagerDropdown from "@/components/common/MoneyManagerDropdown.vue";
import MoneyManagerCheckBox from "@/components/common/MoneyManagerCheckBox.vue";
import MoneyManagerDatePicker from "@/components/common/MoneyManagerDatePicker.vue";
import MoneyManagerLoadingModal from "@/components/common/MoneyManagerLoadingModal.vue";

export default {
  name: "Demo",
  components: {
    MoneyManagerButton,
    MoneyManagerRadio,
    MoneyManagerDropdown,
    MoneyManagerCheckBox,
    MoneyManagerDatePicker,
    MoneyManagerLoadingModal
  },
  data() {
    return {
      resetRadio: false,
      radioList: [
        {
          id: "1",
          value: "Male",
        },
        {
          id: "2",
          value: "Female",
        },
      ],
      radioDefault: {},
      resetDropdown: false,
      dropdownList: [
        {
          id: "1",
          value: "Dropdown 1"
        },
        {
          id: "2",
          value: "Dropdown 2"
        },
        {
          id: "3",
          value: "Dropdown 3"
        }
      ],
      resetCheckBox: false,
      checkBoxList: [
        {
          id: "1",
          value: "Check Box 1"
        },
        {
          id: "2",
          value: "Check Box 2"
        }
      ],
      defaultCheckBoxList: [
        {
          id: "1",
          value: "Check Box 1"
        }
      ],
      resetDatePicker: false,
      defaultDate: "",
      loadingModal: false
    };
  },
  created() {
    this.radioDefault = this.radioList[0];
  },
  methods: {
    onButtonClick() {
      console.log("Button clicked");
    },
    onRadioSelect(radioSelected) {
      this.resetRadio = false;
      console.log("Radio selected: " + radioSelected.value);
    },
    onDropdownSelect(dropdownSelected) {
      this.resetDropdown = false;
      console.log("Dropdown Selected: " + dropdownSelected.value);
    },
    onCheckBoxSelect(checkBoxSelected) {
      this.resetCheckBox = false;
      console.log("******* CheckBox Selected *******")
      for (let checkBox of checkBoxSelected) {
        console.log(checkBox.id + " - " + checkBox.value);
      }
      console.log("******* CheckBox Selected *******");
    },
    onDateSelected(selectedDate) {
      this.resetDatePicker = false;
      console.log("Date Selected: " + selectedDate);
    },
    resetData() {
      console.log("Reset");
      this.resetRadio = true;
      this.resetDropdown = true;
      this.resetCheckBox = true;
      this.resetDatePicker = true;
    },
    onLoadingModalClick() {
      this.loadingModal = true;
      setTimeout(() => {
        this.loadingModal = false;
      }, 5000);
    }
  },
};
</script>
