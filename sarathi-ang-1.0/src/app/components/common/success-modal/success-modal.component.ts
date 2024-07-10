import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-success-modal',
  templateUrl: './success-modal.component.html',
  styleUrls: ['./success-modal.component.css']
})
export class SuccessModalComponent implements OnInit {

  @Output("closeSuccess") closeSuccessEventEmitter = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit(): void {
  }

  closeSuccess() {
    this.closeSuccessEventEmitter.emit(false);
  }
}
