import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-confirmation-modal',
  templateUrl: './confirmation-modal.component.html',
  styleUrls: ['./confirmation-modal.component.css']
})
export class ConfirmationModalComponent implements OnInit {

  @Input() confirmationMessage!: string;
  @Output("onCloseConfirmation") closeConfirmationEventEmitter = new EventEmitter<any>();
  @Output("onConfirmationContinue") continueConfirmationEventEmitter = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
  }

  onCloseClick() {
    this.closeConfirmationEventEmitter.emit();
  }

  onContinueClick() {
    this.continueConfirmationEventEmitter.emit();
  }

}
