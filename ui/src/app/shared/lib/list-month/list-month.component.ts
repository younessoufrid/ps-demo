import {
  Component,
  forwardRef, Injector,
  Input,
  OnInit,
} from '@angular/core';
import {ControlValueAccessor, UntypedFormControl, NG_VALUE_ACCESSOR, NgControl, FormControl} from '@angular/forms';
import {MONTHS} from '../../commun/constants';

@Component({
  selector: 'psv-list-month',
  templateUrl: './list-month.component.html',
  styleUrls: ['./list-month.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ListMonthComponent),
      multi: true,
    },
  ],
})
export class ListMonthComponent implements OnInit, ControlValueAccessor {
  @Input() id: string;   // required;
  @Input() label: string;
  @Input() placeholder: string;
  @Input() tooltipText: string;
  @Input() enableSearch: boolean;
  @Input() required: boolean;
  @Input() disable: boolean;
  @Input() class = 'dpdvalue';
  @Input() months: {label: string, value: number}[] = [];
  @Input() years = [];
  @Input() startYear: number;
  currentYear: number;
  currentMonth: number;
  currentMonthName: string;
  selectedItem: {year: number, month: number};
  public ngControl: NgControl;

  constructor(public inj: Injector) {
  }

  ngOnInit(): void {
    if (this.id === undefined || this.id === null || this.id === '') {
      throw new Error('add id = "id_value" to your app-list-simple');
    }
    if (this.startYear === undefined || this.startYear === null) {
      throw new Error('startyear must be set');
    }
    this.months = MONTHS;
    this.years = this.getYears();
    this.selectedItem = {year: this.currentYear, month: this.currentMonth};
    this.ngControl = this.inj.get<NgControl>(NgControl);
  }

  onChange = (date: Date) => {
  };
  onTouch = (date: Date) => {
  };
  public validateFn = (c: FormControl) => {
  };

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

  writeValue(val: any) {
  }



  public validate(c: UntypedFormControl) {
    return this.validateFn(c);
  }

  showList() {
    if (!this.disable) {
      const control = document.getElementById(this.id);
      control?.classList?.toggle('current');
    }
  }

  hideList() {
    const control = document.getElementById(this.id);
    control?.classList?.remove('current');
  }

  private getYears() {
    const years = [];
    const date = new Date();
    const currentYear = date.getFullYear();
    const currentMonth = date.getMonth();
    this.currentYear = currentYear;
    this.currentMonth = currentMonth + 1;
    this.currentMonthName = this.months?.filter(month => month.value === this.currentMonth)[0].label;
    for (let i = this.startYear; i <= currentYear;  i++) {
      years.push(i);
    }
    return years;
  }

  setYear(event) {
    if(event && event.target) {
      this.selectedItem.year = event.target.value;
      this.selectedItem.month = null;
      this.currentMonthName = '';
    }
  }
  setMonth(value: number) {
    if(value) {
      this.selectedItem.month = value;
      this.currentMonthName = this.months?.filter(month => month.value === this.selectedItem?.month)[0].label;
      const date = new Date();
      date.setFullYear(this.selectedItem.year,this.selectedItem.month - 1, 1)
      this.onChange(date)
    }
  }

  protected readonly Number = Number;
}
