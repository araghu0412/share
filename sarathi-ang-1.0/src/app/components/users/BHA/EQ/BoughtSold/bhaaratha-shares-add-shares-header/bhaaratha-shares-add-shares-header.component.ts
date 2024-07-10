import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-bhaaratha-shares-add-shares-header',
  templateUrl: './bhaaratha-shares-add-shares-header.component.html',
  styleUrls: ['./bhaaratha-shares-add-shares-header.component.css']
})
export class BhaarathaSharesAddSharesHeaderComponent implements OnInit {

  @Output("onBackButtonClicked") backButtonClickedEventEmitter = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  onBackButtonClick() {
    this.backButtonClickedEventEmitter.emit();
  }
}
