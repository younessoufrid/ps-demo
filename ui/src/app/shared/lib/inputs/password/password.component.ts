import {Component, Input, Injector, forwardRef, OnInit, ViewChild, ElementRef} from '@angular/core';
import { TextComponent } from '../text/text.component';
import {ControlValueAccessor, NG_VALUE_ACCESSOR, NG_VALIDATORS, UntypedFormControl, Validators, NgControl} from '@angular/forms';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: PasswordComponent,
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => PasswordComponent),
      multi: true,
    },
  ],
})
export class PasswordComponent implements ControlValueAccessor, OnInit {
  type = 'password' ;
  hasValue: boolean;

  @Input() label: string;
  @Input() id: string;
  @Input() placeholder: string;
  @Input() required: boolean;
  @Input() clearable: boolean;
  @Input() minlength?: number;
  @Input() maxlength?: number;
  @Input() disable: boolean;
  @Input() autocomplete: string;
  @Input() tooltipText: string;
  @Input() placement = '';
  @Input() errorMessage?: string;
  @Input() controlMessages?: boolean;
  @Input() isRulesNeeded: boolean;
  @Input() hasError: boolean;

  public ngControl: NgControl;
  @ViewChild('inputPassword') input: ElementRef;

  public isEqual = false;
  public displayValue = false;
  public Object = Object;

  /**
   * Generic password rules
   */
  public rules = {
    uppercase: {
      flag: false,
      validated: false,
      key: 'UPPERCASE',
      rule: new RegExp(/[A-Z]/),
    },
    specialChar: {
      flag: false,
      validated: false,
      key: 'SPECIAL_CHAR',
      rule: new RegExp(/[!,@,_,%,^,&,*,?,-,+,<,>,°,:,/]/),
    },
    digit: {
      flag: false,
      validated: false,
      key: 'DIGIT',
      rule: new RegExp(/\d+/),
    },
    minLength: {
      flag: false,
      validated: false,
      key: 'MIN_LENGTH',
      rule: 7,
    },
    loginValue: {
      flag: false,
      validated: false,
      key: 'LOGIN_VALUE',
      rule: '',
    },
  };
  public onTouched = () => {};
  public onChange = (val: string) => {};
  public validateFn = (c: UntypedFormControl) => {};

  constructor(public inj: Injector) {}

  public ngOnInit() {
    this.ngControl = this.inj.get<NgControl>(NgControl);
  }

  set value(val) {
    if (val !== undefined && this.value !== val){
      this.onChange(val);
    }
  }

  public validate(c: UntypedFormControl) {
    return this.validateFn(c);
  }
  public onModelChange(val: any) {
    this.onChange(val);
  }
  /**
   * Show / Hide Button
   */
  public displayPasswordValue(): void {
    this.displayValue ? (this.displayValue = false) : (this.displayValue = true);
  }

  registerOnChange(fn: any): void {
  this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
  }

  writeValue(val: any): void {
    if (typeof val !== 'undefined') {
      this.value = val;
    }
  }
  public clearInput(): void {
    this.input.nativeElement.value = '';
    this.value = '';
    this.input.nativeElement.focus();
  }

  checkValue() {
    this.input.nativeElement.value !== '' ? this.hasValue = true : this.hasValue = false;
  }

}
