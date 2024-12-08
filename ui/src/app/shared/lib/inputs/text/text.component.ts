import { Component, Input, OnInit, Injector, forwardRef, ViewChild, ElementRef, HostListener} from '@angular/core';
import { UntypedFormControl, ControlValueAccessor, NG_VALUE_ACCESSOR, NG_VALIDATORS, NgControl } from '@angular/forms';

@Component({
  selector: 'app-text',
  templateUrl: './text.component.html',
  styleUrls: ['./text.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: TextComponent,
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => TextComponent),
      multi: true,
    },
  ],
})
export class TextComponent implements ControlValueAccessor, OnInit {
  type = 'text' ;
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
  @Input() hasError: boolean;

  @Input() errorExisteName?: boolean;
  @Input() errorMessage?: string;
  @Input() controlMessages?: boolean;
  public ngControl: NgControl;
  @ViewChild('inputText') input: ElementRef;


  public onTouched = () => {};
  public onChange = (val: string) => {};
  public validateFn = (c: UntypedFormControl) => {};

  constructor(public inj: Injector) {}

  public ngOnInit() {
    this.ngControl = this.inj.get<NgControl>(NgControl);
  }
  set value(val){
    if (val !== undefined && this.value !== val){
      this.onChange(val);
    }
  }

  ////
  // ControlValueAccessor Interface Implementation
  ////

  /**
   * Tell ControlValueAccessor what is the change function to use when model / control changes from the UI.
   * Registers a callback function that is called when the control's value changes in the UI.
   * @param fn : The callback function to register
   */
  public registerOnChange(fn: any) {
    this.onChange = fn;
  }

  /**
   * Tell ControlValueAccessor what is the function to use when text is blured.
   * Registers a callback function is called by the forms API on initialization to update the form model on blur.
   * @param fn : The callback function to register
   */
  public registerOnTouched(fn: any) {
    this.onTouched = fn;
  }

  /**
   * Function called when a value is set from js (initialization & further set)
   * Writes a new value to the element.
   * @param val : The new value for the element
   */
  public writeValue(val: any) {
    if (val !== undefined && val !== null) {
      this.value = val;
    }
  }
  /**
   * Function called when form control is checked (form validation)
   * @param c : the component form control
   */
  public validate(c: UntypedFormControl) {
    return this.validateFn(c);
  }

  /**
   * Callback of the ngModelChange (see template) when the value is changed from the UI
   * When the model change we call the onChange function of ControlValueAccessor so the change is propagated as a real ngModel / control
   * @param val : The new value for the element
   */
  public onModelChange(val: any) {
    this.onChange(val);
  }


  /**
   * Clears text's value
   */
  public clearInput(): void {
    this.input.nativeElement.value = '';
    this.value = '';
    this.input.nativeElement.focus();
  }


  /**
   * Check the value of the control to know if we should display the clear cross
   */
  public isCrossDisplayed(control: any) {
    return control && control.value && !control.disabled && !this.disable && JSON.stringify(control.value) !== '{}';
  }

  checkValue() {
     this.input.nativeElement.value !== '' ? this.hasValue = true : this.hasValue = false;
  }
}
