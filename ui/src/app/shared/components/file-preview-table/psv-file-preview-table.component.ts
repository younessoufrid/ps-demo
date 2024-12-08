import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ColumnMode} from '@swimlane/ngx-datatable';
import {OrderLine} from '../../models/beans';
import {LoadingSpinnerService} from '../../service/loading-spinner.service';

@Component({
  selector: 'psv-file-preview-table',
  templateUrl: './psv-file-preview-table.component.html',
  styleUrls: ['./psv-file-preview-table.component.scss']
})
export class PsvFilePreviewTableComponent implements OnInit {
  SAISIE_PATTERN = /^-?\d+(\.\d+)?(\s*\+\s*-?\d+(\.\d+)?)*$/;
  @Input() columns: any[];
  @Input() rows: any[];
  @Input() headerHeight: number;
  @Input() footerHeight: number;
  @Input() rowHeight: number;
  @Input() loadingIndicator = false;
  @Input() scrollbarH = true;
  @Input() scrollbarV = true;
  @Input() totalRows: number;
  @Input() rowsPerPage: number;
  @Output() pageChange: EventEmitter<number> = new EventEmitter();
  rowsToShow: any;
  currentPage = 1;

  ColumnMode = ColumnMode;


  constructor(private loadingSpinnerService: LoadingSpinnerService) {
  }

  ngOnInit(): void {
    this.getRowTableToShow();
    this.loadingSpinnerService.loadingIndicator.subscribe(value => this.loadingIndicator = value);
  }

  onPage(event) {
    if (event && this.currentPage !== event) {
      this.currentPage = event.page;
      this.getRowTableToShow();
    }
  }

  public getRowTableToShow() {
    const startIndex = (this.rowsPerPage - 1) * this.currentPage - (this.rowsPerPage - 1);
    const endIndex = (this.rowsPerPage - 1) * this.currentPage;
    this.rowsToShow = this.rows?.slice(startIndex, endIndex);
  }

  protected readonly Object = Object;
}
