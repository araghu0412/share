import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-demo',
  templateUrl: './demo.component.html',
  styleUrls: ['./demo.component.css']
})
export class DemoComponent implements OnInit {

  resetRadio: boolean = false;
  radioList: Array<any> = [
    {
      id: "1",
      value: "Male"
    },
    {
      id: "2",
      value: "Female"
    }
  ];
  radioDefault: any = this.radioList[0];

  resetDropdown: boolean = false;
  dropdownList: Array<any> = [
    {
      id: "1",
      value: "Dropdown 1",
      isChecked: false
    },
    {
      id: "2",
      value: "Dropdown 2",
      isChecked: false
    },
    {
      id: "3",
      value: "Dropdown 3",
      isChecked: false
    }
  ];

  resetCheckBox: boolean = false;
  checkBoxList: Array<any> = [
    {
      id: "1",
      value: "Check Box 1",
      isChecked: true
    },
    {
      id: "2",
      value: "Check Box 2",
      isChecked: false
    }
  ];

  resetDatePicker: boolean = false;
  defaultDate: string = "12-APR-1989";

  isError: boolean = false;
  errorMessage: string = "";
  isSuccess: boolean = false;
  successMessage: string = "";
  loadingModal: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  onButtonClick() {
    console.log("Button Clicked");
  }

  onRadioSelect(radioSelected: string) {
    this.resetRadio = false;
    console.log("Radio selected: " + radioSelected);
  }

  onDropdownSelect(dropdown: any) {
    this.resetDropdown = false;
    console.log("Dropdown selected: " + dropdown.value);
  }

  onCheckBoxSelect(checkBoxSelected: any) {
    this.resetCheckBox = false;
    console.log("******* CheckBox Selected *******");
    for (let checkBox of checkBoxSelected) {
      console.log(checkBox.id + " - " + checkBox.value);
    }
    console.log("******* CheckBox Selected *******");
  }

  onDateSelected(selectedDate: string) {
    this.resetDatePicker = false;
    console.log("Date Selected: " + selectedDate);
  }

  resetData() {
    console.log("Reset");

    // Resetting checkbox
    this.checkBoxList = [
      {
        id: "1",
        value: "Check Box 1",
        isChecked: true
      },
      {
        id: "2",
        value: "Check Box 2",
        isChecked: false
      }
    ];

    this.resetRadio = true;
    this.resetDropdown = true;
    this.resetCheckBox = true;
    this.resetDatePicker = true;
  }

  showError() {
    this.errorMessage = "Error Message"
    this.isError = true;
  }

  closeError(isError: boolean) {
    this.isError = isError;
    this.errorMessage = "";
  }

  showSuccess() {
    this.successMessage = "Success Message"
    this.isSuccess = true;
  }

  closeSuccess(isSuccess: boolean) {
    this.successMessage = "";
    this.isSuccess = isSuccess;
  }

  onLoadingModalClick() {
    this.loadingModal = true;
    setTimeout(() => {
      this.loadingModal = false;
    }, 5000);
  }
}
