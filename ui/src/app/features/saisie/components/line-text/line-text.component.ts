import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {state} from '@angular/animations';

@Component({
  selector: 'portail-saisie-line-text',
  templateUrl: './line-text.component.html',
  styleUrls: ['./line-text.component.scss']
})
export class LineTextComponent {
  value: string;
  disable = true;

  constructor(public activeModal: NgbActiveModal) {

  }

  protected readonly state = state;

  stateChanged(value: string) {
    this.disable = this.value === value;
  }
}
