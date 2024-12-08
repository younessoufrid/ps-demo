import {
  Component,
  ElementRef,
  forwardRef, Injector,
  Input, OnChanges,
  OnInit, Output,
  Renderer2, SimpleChanges, ViewChild,
} from '@angular/core';
import {ControlValueAccessor, UntypedFormControl, NG_VALUE_ACCESSOR, NgControl, FormControl} from '@angular/forms';
import {SimpleSelectItem} from '../../models/beans';
import {deepEqual} from '../../utils/objectUtils';

@Component({
  selector: 'app-list-simple',
  templateUrl: './list-simple.component.html',
  styleUrls: ['./list-simple.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ListSimpleComponent),
      multi: true,
    },
  ],
})
export class ListSimpleComponent implements OnInit, ControlValueAccessor, OnChanges {
  @Input() id: string;   // required;
  @Input() label: string;
  @Input() placeholder: string;
  @Input() searchInputPlaceholder: string;
  @Input() maxItemsShowOnSearch: number;
  @Input() maxItemsShow: number;
  @Input() tooltipText: string;
  @Input() enableSearch: boolean;
  @Input() required: boolean;
  @Input() errorMessage: string;
  @Input() disable: boolean;
  @Input() autoSelectSingleItem = false;
  @Input() class = 'dpdvalue';
  @Input() items: SimpleSelectItem[] = [];
  initItems: SimpleSelectItem[] = [];
  selectedItem: SimpleSelectItem;
  isSearching = false;
  searchTerms = '';
  public ngControl: NgControl;

  @ViewChild('dropdownContainer') dropdownContainer: ElementRef;

  constructor(public inj: Injector, private renderer: Renderer2) {
  }

  ngOnInit(): void {
    this.ngControl = this.inj.get<NgControl>(NgControl);
    if (this.id === undefined || this.id === null || this.id === '') {
      throw new Error('add id = "id_value" to your app-list-simple');
    }
    this.initItems = this.items;
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.items) {
      const currentValue = changes.items.currentValue;
      const previousValue = changes.items.previousValue;

      // Check if items array has changed
      if (currentValue !== previousValue) {
        // Check if there's only one item and autoSelectSingleItem is enabled
        if (currentValue?.length === 1 && this.autoSelectSingleItem) {
          this.selectedItem = currentValue[0];
          this.onChange(this.selectedItem.value);
        }

        // Update initItems with the current value of items
        this.initItems = currentValue;
      }
    }
  }

  onChange = (items: any) => {
  };
  onTouch = (items: any) => {
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
    if (val !== undefined && val !== null) {
      this.selectedItem = val;
      this.onChange(this.selectedItem.value);
      this.items.splice(this.items.findIndex(item => item.value === this.selectedItem.value), 1);
      this.items.unshift(this.selectedItem);
    } else {
      this.selectedItem = null;
    }
  }

  // setDisabledState(isDisabled: boolean) {
  //   this.disable = isDisabled;
  // }

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

  checkItem(value: SimpleSelectItem, checked) {
    if (checked === true) {
      this.selectedItem = value;
      this.isSearching = false;
      this.hideList();
      this.onChange(this.selectedItem.value);
    } else {
      this.selectedItem = null;
      this.isSearching = false;
      this.onChange(null);
    }
  }

  clearSelectedItem() {
    if (this.selectedItem != null) {
      this.selectedItem = null;
      this.isSearching = false;
      this.onChange(null);
    }
  }

  search(event: any) {
    if (event && event.target) {
      this.isSearching = event.target.value !== undefined && event.target.value.length > 0;
      this.searchTerms = event.target.value;
      this.filterItems();
    }
  }

  filterItems() {
    this.items = this.searchTerms ? this.initItems.filter(item =>
      item.label?.includes(this.searchTerms)) : this.initItems;
  }
}
