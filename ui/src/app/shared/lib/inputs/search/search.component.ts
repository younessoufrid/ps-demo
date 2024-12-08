import {Component, EventEmitter, forwardRef, Input, OnInit, Output} from '@angular/core';
import {FormControl, NG_VALIDATORS, NG_VALUE_ACCESSOR} from '@angular/forms';


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: SearchComponent,
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => SearchComponent),
      multi: true,
    },
  ],
})
export class SearchComponent implements OnInit {
  type = 'search' ;
  initialValue = '';
  @Input() label: string;
  @Input() id: string;
  @Input() searchable: boolean;
  @Input() placeholder: string;
  @Input() required: boolean;
  @Input() minlength: number;
  @Input() maxlength: number;
  @Input() disable: boolean;
  @Input() autocomplete: string;
  @Input() tooltipText: string;
  @Input() placement = '';

  @Output() valueEmited: EventEmitter<string> = new EventEmitter<string>();

  constructor() {}

  public ngOnInit() {

  }

  public validate(c: FormControl) {
  }



  search(val: string) {
    this.valueEmited.emit(val);
  }
}
