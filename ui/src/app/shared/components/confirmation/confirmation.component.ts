import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {CONFIRMATION_ITEMS} from '../../commun/constants';
import {TranslateService} from "@ngx-translate/core";

/**
 * this component is used form confirmation purpos
 * @ param spécifier your action at called component and add new line on CONFIRMATION_ITEMS
 * title and body of CONFIRMATION_ITEMS are geted from i18n files
 **/
@Component({
  selector: 'portail-saisie-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.scss']
})
export class ConfirmationComponent implements OnInit{
  content;

  get getTitle() {
    return this.translateService
      .instant(CONFIRMATION_ITEMS
          .filter(item => item.action === this.content.action)[0].title,
        this.content.params
      )
  }

  get getBody() {
    return this.translateService
      .instant(CONFIRMATION_ITEMS
        .filter(item => item.action === this.content.action)[0].body,
        this.content.params
      )
  }

  constructor(public activeModal: NgbActiveModal, protected translateService: TranslateService) {

  }

  ngOnInit(): void {
    if (this.content === undefined) {
      throw new Error('No content cet');
    }
  }
}
