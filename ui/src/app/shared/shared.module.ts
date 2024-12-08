import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';
import { PsvTableComponent } from './components/table/psv-table.component';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {DatePickerComponent} from './lib/date-picker/date-picker.component';
import {ListMultipleComponent} from './lib/list-multiple/list-multiple.component';
import { TextComponent } from './lib/inputs/text/text.component';
import { PasswordComponent } from './lib/inputs/password/password.component';
import {NgbDatepickerModule, NgbToastModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';
import {NgSelectModule} from '@ng-select/ng-select';
import {NgModule} from '@angular/core';
import {TranslateModule} from '@ngx-translate/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { SearchComponent } from './lib/inputs/search/search.component';
import {ListSimpleComponent} from './lib/list-simple/list-simple.component';
import {ErrorComponent} from '../pages/error/error.component';
import {SimpleDropdownComponent} from './lib/simple-dropdown/simple-dropdown.component';
import { LoadingSpinnerComponent } from './components/loading-spinner/loading-spinner.component';
import { ConfirmationComponent } from './components/confirmation/confirmation.component';
import {PsvSummaryTableComponent} from './components/summary-table/psv-summary-table.component';
import {FilterComponent} from './components/filter/filter.component';
import {PsvFilePreviewTableComponent} from './components/file-preview-table/psv-file-preview-table.component';
import { PopupComponent } from './components/popup/popup.component';
import {TooltipModule} from 'ngx-bootstrap/tooltip';
import {ListMonthComponent} from './lib/list-month/list-month.component';
@NgModule({
  declarations: [
    ListMultipleComponent,
    ListSimpleComponent,
    DatePickerComponent,
    PsvTableComponent,
    PsvFilePreviewTableComponent,
    TextComponent,
    ErrorComponent,
    PasswordComponent,
    SearchComponent,
    SimpleDropdownComponent,
    LoadingSpinnerComponent,
    ConfirmationComponent,
    PopupComponent,
    PsvSummaryTableComponent,
    FilterComponent,
    ListMonthComponent
  ],
  exports: [
    PsvTableComponent,
    PsvFilePreviewTableComponent,
    DatePickerComponent,
    ListMultipleComponent,
    ListSimpleComponent,
    TextComponent,
    ErrorComponent,
    PasswordComponent,
    FormsModule,
    TranslateModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule,
    NgbModule,
    NgbToastModule,
    RouterModule,
    SearchComponent,
    SimpleDropdownComponent,
    LoadingSpinnerComponent,
    ConfirmationComponent,
    PopupComponent,
    PsvSummaryTableComponent,
    FilterComponent,
    ListMonthComponent
  ],
    imports: [
        CommonModule,
        RouterModule,
        NgxDatatableModule,
        FormsModule,
        NgbToastModule,
        FormsModule,
        TranslateModule.forChild(),
        NgbDatepickerModule,
        NgSelectModule,
        HttpClientModule,
        ReactiveFormsModule,
        NgbModule,
        TooltipModule
    ]
})
export class SharedModule { }
