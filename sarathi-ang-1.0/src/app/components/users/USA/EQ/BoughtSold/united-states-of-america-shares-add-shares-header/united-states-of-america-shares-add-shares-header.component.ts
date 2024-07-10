import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-united-states-of-america-shares-add-shares-header',
  templateUrl: './united-states-of-america-shares-add-shares-header.component.html',
  styleUrls: ['./united-states-of-america-shares-add-shares-header.component.css']
})
export class UnitedStatesOfAmericaSharesAddSharesHeaderComponent implements OnInit {

  @Output("onBackButtonClicked") backButtonClickedEventEmitter = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  onBackButtonClick() {
    this.backButtonClickedEventEmitter.emit();
  }
}
