import {
  Component,
  EventEmitter,
  forwardRef,
  inject,
  Injector,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import {
  NgbCalendar,
  NgbDate, NgbDateStruct,
} from '@ng-bootstrap/ng-bootstrap';
import {ControlValueAccessor, UntypedFormControl, NG_VALIDATORS, NG_VALUE_ACCESSOR, NgControl, FormControl} from '@angular/forms';

@Component({
  selector: 'saisie-date-picker',
  templateUrl: './date-picker.component.html',
  styleUrls: ['./date-picker.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: DatePickerComponent,
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => DatePickerComponent),
      multi: true,
    },
  ]
})
export class DatePickerComponent implements OnInit, ControlValueAccessor {
  @Input() simpleDatePicker = false;
  @Input() label: string;
  @Input() id: string;
  @Input() required: boolean;
  @Input() maxDate: NgbDate;
  @Input() minDate: NgbDate;
  @Input() defaultDate: NgbDate;
  @Input() disable: boolean;
  lastDay: number;
  today: NgbDate;
  public ngControl: NgControl;
  public date: NgbDate;
  model: NgbDateStruct;
  public onChange = (date: Date) => {
  }
  public validateFn = (control: FormControl) => {
  }

  constructor(public inj: Injector) {
    this.today = inject(NgbCalendar).getToday();
  }

  ngOnInit(): void {
    this.ngControl = this.inj.get<NgControl>(NgControl);
    if (this.defaultDate) {
      this.model = this.defaultDate;
      this.date = this.defaultDate;
      this.onChange(this.toJsDate(this.date));
    }
  }

  onDateSelection(date: NgbDate) {
    this.date = date;
    this.onChange(this.toJsDate(this.date));
  }


  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  public validate(c: FormControl) {
    this.validateFn(c);
  }

  // setDisabledState(isDisabled: boolean): void {
  //   this.disable = isDisabled;
  // }

  writeValue(val: any): void {
    if (val) {
      this.date = this.fromJSDate(val);
      this.lastDay = this.getLastMonthDay(this.date.year, this.date.month);
    }
  }


  toJsDate(ngbDate: NgbDate) {
    if (ngbDate) {
      const dateRef = new Date(Date.now());
      const date = new Date(ngbDate.year, ngbDate.month - 1, ngbDate.day, dateRef.getHours(), dateRef.getMinutes());
      return date;
    }
  }

  fromJSDate(val: string) {
    if (val !== undefined && val !== null) {
      const date = new Date(Date.parse(val));
      return new NgbDate(date.getFullYear(), date.getMonth() + 1, date.getDate());
    }
  }

  getLastMonthDay(year: number, month: number) {
    return new Date(year, month, 30).getDay();
  }

  registerOnTouched(fn: any): void {
  }
}
