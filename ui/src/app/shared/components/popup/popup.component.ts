import {Component, Input} from '@angular/core';

@Component({
  selector: 'psv-popup',
  templateUrl: './popup.component.html',
  styleUrls: ['./popup.component.scss']
})
export class PopupComponent {
  @Input() isPopup: boolean;
}
