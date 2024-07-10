import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-error-modal',
  templateUrl: './error-modal.component.html',
  styleUrls: ['./error-modal.component.css']
})
export class ErrorModalComponent implements OnInit {

  @Output("closeError") closeErrorEventEmitter = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit(): void {
  }

  closeError() {
    this.closeErrorEventEmitter.emit(false);
  }
}
