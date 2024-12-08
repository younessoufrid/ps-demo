import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FILE_FIELDS_OBJECT_MAPPING, SAISIE, TABLE_COLUMN} from '../../shared/commun/constants';
import {LightOrderLine, OrderDataResponse, OrderFilters, OrderFiltersData, OrderLine} from '../../shared/models/beans';
import {SaisieService} from './service/saisie.service';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {ConfirmationComponent} from '../../shared/components/confirmation/confirmation.component';
import {LineTextComponent} from './components/line-text/line-text.component';
import {finalize, map, switchMap} from 'rxjs/operators';
import {OrderService} from './service/order.service';
import {UploadFileService} from '../../shared/service/upload-file.service';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';
import {TranslateService} from '@ngx-translate/core';
import {formatrDate} from '../../shared/utils/dateUtils';
import {CsvService} from '../../shared/service/csv.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'psv-saisie',
  templateUrl: './saisie.component.html',
  styleUrls: ['./saisie.component.scss']
})
export class SaisieComponent implements OnInit, OnDestroy {
  currentFileUpload: File;
  progress: { percentage: number } = {percentage: 0};
  currentPage = 1;
  rowsPerpage = 10;
  totalRows = 0;
  columns = TABLE_COLUMN;
  filePreviewColumns: any;
  rowsToShow: OrderLine[] = [];
  rows: OrderLine[] = [];
  modalRef: NgbModalRef;
  date: string;
  orderId: number;
  filtersData: OrderFiltersData;
  showFilePreviewPopup = false;
  filePreviewData: any[];
  validationErrors: any[];
  fileValidationErrorsPopup = false;
  isUploading = false;
  @ViewChild('fileInput') fileInput: ElementRef;

  constructor(private modalService: NgbModal,
              private orderService: OrderService,
              private saisieService: SaisieService,
              private toaster: ToastrService,
              protected translateService: TranslateService,
              private uploadService: UploadFileService,
              private csvService: CsvService) {
  }

  ngOnInit(): void {
    this.rows = [];
    this.rowsToShow = [];
    this.getRowTableToShow();
    this.watchFiltersData();
  }

  // watches filters data from the service and synchronize it with this.filtersData
  private watchFiltersData() {
    this.orderService.orderData$.pipe(map(data => {
      return this.mapOrderDataToFiltersData(data);
    })).subscribe((data: OrderFiltersData) => {
      this.filtersData = data;
      this.rows = this.filtersData.orderLines;
      this.totalRows = this.rows?.filter(row => row.show === true)?.length + 1;
      this.getRowTableToShow();
    });
  }

  private mapOrderDataToFiltersData(data: OrderDataResponse) {
    const filtersData: OrderFiltersData = {saleSites: [], cdrs: [], clients: [], affairs: [], orders: [], orderLines: [], summary: []};
    filtersData.saleSites = data?.saleSites?.map(ss => {
      return {label: ss.reference, value: ss.reference};
    });
    filtersData.cdrs = data?.cdrs?.map(cdr => {
      return {label: cdr.reference, value: cdr.reference};
    });
    filtersData.clients = data?.clients?.map(client => {
      return {label: client.name, value: client.id};
    });
    filtersData.affairs = data?.affairs?.map(affair => {
      return {label: affair.reference, value: affair.id};
    });
    filtersData.orders = data?.orders?.map(order => {
      return {label: order.number, value: order.id};
    });
    filtersData.orderLines = data?.orderLines?.map(orderLine => {
      return {
        id: orderLine.id,
        designation: orderLine.designation,
        textLigne: orderLine.textLigne,
        quantity: orderLine.quantity,
        totalMonthQte: orderLine.totalMonthQte,
        priceNetHT: orderLine.priceNetHT,
        uniteVente: orderLine.uniteVente,
        line: orderLine.line,
        totalPrice: orderLine.totalPrice,
        show: orderLine.show,
        date: orderLine.date,
        idOrder: orderLine.idOrder,
        soumis: orderLine.soumis
      };
    });
    return filtersData;
  }

  searchByFilters(searchFilters: OrderFilters) {
    searchFilters.action = SAISIE;
    this.currentPage = 1;
    this.date = searchFilters.date;
    this.orderId = searchFilters.order;
    this.orderService.fetchOrderData(searchFilters);
  }


  public pageChange(event) {
    if (event) {
      this.currentPage = event;
      this.getRowTableToShow();
    }
  }

  resetQuantity(row: OrderLine) {
    this.modalRef = this.openpopup(ConfirmationComponent);
    this.modalRef.componentInstance.content = {
      action: 'reset',
      params: {designation: row.designation, date: this.date.substring(3)}
    };
    this.modalRef.closed.subscribe(data => {
      if (data) {
        const rowToReset = this.rows.find(r => r.id === row.id);
        rowToReset.date = formatrDate(this.date);
        this.saisieService.resetQuantity(rowToReset).subscribe(
          qteData => {
            if (qteData) {
              rowToReset.totalMonthQte = qteData.totalMonthQte;
              rowToReset.quantity = qteData.quantity;
              rowToReset.totalPrice = qteData.totalPrice;
              this.getRowTableToShow();
            }
          }
        );
      }
    });
  }

  hideLine(row: OrderLine) {
    this.modalRef = this.openpopup(ConfirmationComponent);
    const rowToHide = this.rows.find(r => r.id === row.id);
    this.modalRef.componentInstance.content = {
      action: 'hide',
      params: {designation: rowToHide.designation}
    };
    this.modalRef.closed.subscribe(data => {
      if (data) {
        rowToHide.date = formatrDate(this.date);
        this.saisieService.hideLine(rowToHide).subscribe(
          hidedata => {
            if (hidedata) {
              rowToHide.show = hidedata.show;
              const currentPageHasAny = this.rowsToShow?.filter(r => r.show)?.length === 1;
              if (currentPageHasAny) {
                this.currentPage--;
              }
            }
            this.getRowTableToShow();
          }
        );
      }
    });
  }

  updateQuantity(row: OrderLine) {
    const rowToUpdate = this.rows.find(r => r.id === row.id);
    rowToUpdate.date = formatrDate(this.date);
    this.saisieService.updateQuantity(rowToUpdate).subscribe(
      data => {
        rowToUpdate.totalMonthQte = data.totalMonthQte;
        rowToUpdate.quantity = data.quantity;
        rowToUpdate.totalPrice = data.totalPrice;
        this.getRowTableToShow();
      }
    );
  }

  showHidenLines() {
    this.modalRef = this.openpopup(ConfirmationComponent);
    this.modalRef.componentInstance.content = {
      action: 'show-all',
      params: {order: this.filtersData.orders
          .find(order => order.value === this.orderId).label,}
    };
    this.modalRef.closed.subscribe(data => {
      if (data) {
        if (this.orderId) {
          const saisieWo: LightOrderLine = {
            date: formatrDate(this.date),
            idOrder: this.orderId
          };
          this.saisieService.ShowHidenLines(saisieWo).subscribe(
            data => {
              this.rows.forEach(row => row.show = true);
              this.getRowTableToShow();
            }
          );
        }
      }
    });

  }

  editLineText(row: OrderLine) {
    this.modalRef = this.openpopup(LineTextComponent);
    this.modalRef.componentInstance.value = row?.textLigne;
    this.modalRef.closed.subscribe(data => {
      if (data.response && data.value !== row.textLigne) {
        row.textLigne = data.value;
        const rowToUpdate = this.rows.find(r => r.id === row.id);
        rowToUpdate.date = formatrDate(this.date);
        this.saisieService.updateTextLigne(row).subscribe(
          textLignedata => {
            rowToUpdate.textLigne = textLignedata.textLigne;
          }
        );
      }
    });
  }

  public getRowTableToShow() {
    const startIndex = (this.rowsPerpage - 1) * this.currentPage - (this.rowsPerpage - 1);
    const endIndex = (this.rowsPerpage - 1) * this.currentPage;
    this.rowsToShow = this.rows?.filter(row => row.show === true)?.sort((a, b) => a.line - b.line)?.slice(startIndex, endIndex);
    this.rowsToShow?.push(this.getTotalMonthPrice());
  }


  openpopup(component: any) {
    return this.modalService.open(component, {
      backdrop: 'static',
      keyboard: false,
    });
  }


  private getTotalMonthPrice(): OrderLine {
    return {
      date: undefined,
      designation: undefined,
      textLigne: undefined,
      id: undefined,
      idOrder: undefined,
      line: undefined,
      priceNetHT: undefined,
      quantity: undefined,
      uniteVente: 'TABLE.TOTAL_PRICE_MONTH',
      show: true,
      totalMonthQte: undefined,
      totalPrice: this.rows.reduce((pre, curr) => pre + curr.totalPrice, 0),
      soumis: undefined
    };
  }

  exportData() {
    if (this.checkIfUniqueData()) {
      this.uploadService.downloadFile();
    }
  }

  importFile(event) {
    this.progress.percentage = 0;
    if (event && event.target && event.target.files) {
      this.currentFileUpload = event.target.files[0];
      this.processFile().subscribe(data => {
        this.filePreviewData = this.mapFromFileObjects(data);
        this.generateColumnsList();
        this.populateValidationErrors();
        this.showFilePreview();
      });
    }
  }

  processFile(): Observable<any[]> {
    return this.csvService.processFile(this.currentFileUpload);
  }

  mapFromFileObjects(entries: object[]) {
    return entries.map((obj, index) => {
      const mappedObj = {};
      let notValidLine = false;

      FILE_FIELDS_OBJECT_MAPPING.forEach((value, key) => {
        const {fieldName, required, validator} = value;
        const columnValue = obj[key];

        let validationError = null;

        if (validator) {
          const validation = validator(columnValue);
          if (validation) {
            validationError = this.translateService.instant(validation.error, validation.params);
            notValidLine = true;
          }
        }

        if (required && columnValue === null) {
          validationError = this.translateService.instant('MESSAGE.ERROR.REQUIRED_FIELD');
          notValidLine = true;
        }

        mappedObj[fieldName] = {
          line: index + 1,
          column: key,
          value: columnValue,
          validationError
        };
      });

      return {status: !notValidLine, ...mappedObj};
    });
  }

  generateColumnsList() {
    this.filePreviewColumns = Object.keys(this.filePreviewData[0]).map(key => {
      return {name: this.filePreviewData[0][key].column, prop: key};
    });
  }

  populateValidationErrors() {
    this.validationErrors = this.filePreviewData.reduce((acc, obj) => {
      const objErrorMessages = Object.values(obj)
        .map(({line, column, validationError}) => {
          if (validationError) {
            return this.translateService.instant('MESSAGE.ERROR.CSV_LINE_VALIDATION_ERROR', {
              line,
              column,
              errorMessage: validationError
            });
          }
          return null;
        })
        .filter(message => message !== null);

      return acc.concat(objErrorMessages);
    }, []);
  }

  showFilePreview() {
    this.showFilePreviewPopup = true;
  }

  showFileValidationErrors() {
    this.fileValidationErrorsPopup = true;
  }

  closeFileValidationErrors() {
    this.fileValidationErrorsPopup = false;
  }

  closeFilePreview() {
    this.fileInput.nativeElement.value = '';
    this.currentFileUpload = null;
    this.filePreviewData = null;
    this.validationErrors = null;
    this.showFilePreviewPopup = false;
  }

  uploadFile() {
    this.uploadService.upload(this.currentFileUpload).subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress.percentage = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.toaster.success(`${this.translateService.instant('UPLOAD_SUCCESS')}`);
          this.currentFileUpload = undefined;
          // document.getElementById('labeled-input').classList.remove('disabled-label');
        }
      }
    );
  }

  uploadCsvData() {
    this.isUploading = true;
    let correctEntriesNumber: number;
    let incorrectEntriesNumber: number;
    this.processFile()
      .pipe(
        switchMap(data => {
          const filteredEntries = data.filter((obj, index) => this.filePreviewData[index].status);
          correctEntriesNumber = filteredEntries.length;
          incorrectEntriesNumber = data.length - filteredEntries.length;
          const csvData = this.csvService.jsonToCsv(filteredEntries, { delimiter: ';' });
          return this.uploadService.uploadCsvData(csvData)
            .pipe(
              finalize(() => {
                this.isUploading = false;
              })
            );
        })
      )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            this.handleUploadSuccess(correctEntriesNumber, incorrectEntriesNumber);
            this.closeFilePreview();
          }
        }
      );
  }

  private handleUploadSuccess(correctEntriesNumber: number, incorrectEntriesNumber: number) {
    const messageKey = 'FILE_UPLOAD_PARTIAL';
    const message = this.translateService.instant(messageKey, {
      successfulLines: correctEntriesNumber,
      totalLines: correctEntriesNumber + incorrectEntriesNumber
    });
    const messageType = incorrectEntriesNumber > 0 ? 'warning' : 'success';
    this.toaster[messageType](message);
  }

  private checkIfUniqueData() {
    return (this.filtersData.orders && this.filtersData.orders.length === 1) &&
      (this.filtersData.cdrs && this.filtersData.cdrs.length === 1) &&
      (this.filtersData.affairs && this.filtersData.affairs.length === 1) &&
      (this.filtersData.clients && this.filtersData.clients.length === 1);
  }

  resetOrderQuantity(event: boolean) {
    this.modalRef = this.openpopup(ConfirmationComponent);
    this.modalRef.componentInstance.content = {
      action: 'reset-all',
      params: {
        order: this.filtersData.orders
          .find(order => order.value === this.orderId).label,
        date: this.date.substring(3)
      }
    };
    this.modalRef.closed.subscribe(data => {
      if (data) {
        const saisieWo: LightOrderLine = {
          date: formatrDate(this.date),
          idOrder: this.orderId
        };
        this.saisieService.resetCommandeQuantity(saisieWo).subscribe(
          orderLines => {
            if (orderLines) {
              this.rows = orderLines;
              this.getRowTableToShow();
            }
          }
        );
      }
    });
  }

  ngOnDestroy(): void {
    this.orderService.orderDataSubject.next(null);
  }
}
