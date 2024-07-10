import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-money-manager-check-box',
  templateUrl: './money-manager-check-box.component.html',
  styleUrls: ['./money-manager-check-box.component.css']
})
export class MoneyManagerCheckBoxComponent implements OnInit, OnChanges {

  @Input() resetCheckBox!: boolean;
  @Input() checkBoxList!: Array<any>;
  @Input() checkBoxName!: string;
  @Output("onCheckBoxSelect") checkBoxSelectEventEmitter = new EventEmitter<Array<any>>();

  selectedCheckBoxList: Array<any> = [];

  constructor() { }

  ngOnInit(): void {
    this.selectedCheckBoxList = [];
    this.checkBoxList.forEach(checkBox => {
      if (checkBox.isChecked) {
        this.selectedCheckBoxList.push(checkBox);
      }
    });
  }

  ngOnChanges() {
    if (this.resetCheckBox) {
      this.selectedCheckBoxList = [];
      this.checkBoxList.forEach(checkBox => {
        if (checkBox.isChecked) {
          this.selectedCheckBoxList.push(checkBox);
        }
      });
    }
  }

  onCheckBoxSelect(checkBox: any, pushFlag: boolean) {
    if (pushFlag) {
      checkBox.isChecked = pushFlag;
      this.selectedCheckBoxList.push(checkBox);
    } else {
      for(let i=0; i < this.selectedCheckBoxList.length; i++) {
        if (this.selectedCheckBoxList[i].id === checkBox.id) {
          this.selectedCheckBoxList.splice(i, 1);
          break;
        }
      }
    }

    this.checkBoxSelectEventEmitter.emit(this.selectedCheckBoxList);
  }

}
