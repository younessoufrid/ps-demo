import {
  AfterViewChecked,
  Component,
  ElementRef, EventEmitter,
  forwardRef, Injector,
  Input,
  OnInit, Output,
  QueryList,
  ViewChildren,
} from '@angular/core';
import {ControlValueAccessor, UntypedFormControl, NG_VALUE_ACCESSOR, NgControl} from '@angular/forms';
import {SimpleSelectItem} from '../../models/beans';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-list-multiple',
  templateUrl: './list-multiple.component.html',
  styleUrls: ['./list-multiple.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ListMultipleComponent),
      multi: true,
    },
  ],
})
export class ListMultipleComponent implements OnInit, ControlValueAccessor {
  @Input() id: string;   // required;
  @Input() label: string;
  @Input() placeholder: string;
  @Input() iconItemDetail = false;
  @Input() searchInputPlaceholder: string;
  @Input() maxItemsShowOnSearch: number;
  @Input() maxItemsShow: number;
  @Input() maxItemsToSelect: number;
  @Input() tooltipText: string;
  @Input() multiselect: boolean;
  @Input() enableSearch: boolean;
  @Input() defaultItem: SimpleSelectItem[];
  @Input() required: boolean;
  @Input() disable: boolean;
  listWasShowed = false;
  @Input() class = 'dpdvalue';
  @Output() namechange: EventEmitter<string> = new EventEmitter<string>();
  selectedItemsList: SimpleSelectItem[] = [];
  selectedItems = '';
  public ngControl: NgControl;

  @Input() items: SimpleSelectItem[] = [];
  private modalRef: NgbModalRef;
  isSearching = false;

  @ViewChildren('checkboxes') checkboxes: QueryList<ElementRef>;

  constructor(private modalService: NgbModal, public inj: Injector) {
  }

  ngOnInit(): void {
    this.ngControl = this.inj.get<NgControl>(NgControl);
    if (this.id === undefined || this.id === null || this.id === '') {
      throw new Error('add id = "id_value" to your app-list-multiple');
    }
    if (this.defaultItem && this.defaultItem.length > 1 && !this.multiselect) {
      throw new Error('add [multiselect] = "true" to your app-list-multiple');
    }
  }

  onChange = (items: SimpleSelectItem[]) => {
  }
  onTouch = (items: SimpleSelectItem[]) => {
  }
  public validateFn = (c: UntypedFormControl) => {
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

  writeValue(val: SimpleSelectItem[]) {
    if (val && val.length > 1 && !this.multiselect) {
      throw new Error('add [multiselect] = "true" to your app-list-multiple');
    } else if (val === null || val === undefined) {
      this.selectedItemsList = [];
    } else if (this.defaultItem) {
      this.defaultItem.forEach(item => this.selectedItemsList.push(item));
    } else {
      this.selectedItemsList = val;
      this.selectedItemsList?.forEach(value => this.items.splice(this.items.findIndex(item => item.value === value.value), 1));
      this.selectedItemsList?.forEach(item => this.items.unshift(item));
    }
    this.getListSelected();
  }

  setDisabledState(isDisabled: boolean) {
    this.disable = isDisabled;
  }

  public validate(c: UntypedFormControl) {
    return this.validateFn(c);
  }

  ItemShouldBeCheked(item) {
    return this.selectedItemsList?.filter(i => i.value === item)?.length > 0;
  }

  showList() {
    this.listWasShowed = true;
    if (!this.disable) {
      const control = document.getElementById(this.id);
      control.classList.toggle('current');
    }
  }

  hideList() {
    this.listWasShowed = false;
    const control = document.getElementById(this.id);
    if (!this.isSearching) {
      control.classList.remove('current');
    }
  }

  selectionChange() {
    this.onChange(this.selectedItemsList);
  }

  checkItem(value: SimpleSelectItem, checked: boolean) {
    if (checked === true) {
      if (this.multiselect) {
        this.selectedItemsList?.push(value);
      } else {
        this.selectedItemsList = [];
        this.checkboxes.filter(check => check.nativeElement.value !== value.value).forEach(check => check.nativeElement.checked = false);
        this.selectedItemsList?.push(value);
      }
      this.isSearching = false;
    } else {
      this.selectedItemsList?.splice(this.selectedItemsList.findIndex(item => item.value === value.value), 1);
    }
    this.getListSelected();
  }

  search(event: any) {
    if (event && event.target && event.target) {
      this.namechange.emit(event.target.value);
      if (event.target.value !== undefined && event.target.value.length > 0) {
        this.isSearching = true;
      } else {
        this.isSearching = false;
      }
    }
  }

  getListSelected() {
    let value = '';
    for (const item of this.selectedItemsList) {
      value += item.label + ', ';
    }
    value = value.substring(0, value.length - 2);
    this.selectedItems = value;
  }
}
