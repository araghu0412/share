import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-input-field-error',
  templateUrl: './input-field-error.component.html',
  styleUrls: ['./input-field-error.component.css']
})
export class InputFieldErrorComponent implements OnInit {

  @Input() errorMessage!: string;

  constructor() { }

  ngOnInit(): void {
  }

}
