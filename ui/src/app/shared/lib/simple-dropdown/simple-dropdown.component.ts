import {
  Component,
  forwardRef,
  Input,
  OnInit,
} from '@angular/core';
import {
  ControlValueAccessor,
  UntypedFormControl,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
} from '@angular/forms';
import {DropdownPosition} from '@ng-select/ng-select';

@Component({
  selector: 'app-simple-dropdown',
  templateUrl: './simple-dropdown.component.html',
  styleUrls: ['./simple-dropdown.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: SimpleDropdownComponent,
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => SimpleDropdownComponent),
      multi: true,
    },
  ],
})
export class SimpleDropdownComponent implements OnInit, ControlValueAccessor {
  @Input() dropdownPosition: DropdownPosition;
  @Input() id: string;
  @Input() label: string;
  class = 'simple-drop-down';
  @Input() disable: boolean;
  @Input() items: any[];
  @Input() selectedItem;

  public onTouched = () => {};
  public onChange = (val: string) => {};
  public validateFn = (c: UntypedFormControl) => {};

  constructor() {}

  public ngOnInit() {
    if (this.id === undefined || this.id === null || this.id === '') {
      throw  new Error('Add id = "id_value" to your tag component');
    }
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disable = isDisabled;
  }

  writeValue(val: string): void {
    if (typeof val !== 'undefined') {
      this.selectedItem = val;
    }
  }

  public validate(c: UntypedFormControl) {
    return this.validateFn(c);
  }

  public onModelChange() {
    this.onChange(this.selectedItem);
  }

}
