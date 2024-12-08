import validationUtils from '../utils/validationUtils';

export const TABLE_COLUMN = [
  {name: 'DESIGNATION', display: true, prop: 'designation', order: 1, width: 300},
  {name: 'LINE_TEXT', display: true, prop: 'textLigne', order: 2, width: 60},
  {name: 'QUANTITY', display: true, prop: 'quantity', order: 3, width: 100},
  {name: 'TOTAL_MONTH', display: true, prop: 'totalMonthQte', order: 4, width: 40},
  {name: 'TRASH', display: true, prop: 'trash', order: 5, width: 20},
  {name: 'NET_PRICE_EXCLUDEING_TAX', display: true, prop: 'priceNetHT', order: 6, width: 20},
  {name: 'SALSES_UNIT', display: true, prop: 'uniteVente', order: 7, width: 20},
  {name: 'TOTAL_PRICE', display: true, prop: 'totalPrice', order: 8, width: 50},
  {name: 'DISPLAY', display: true, prop: 'display', order: 9, width: 50},
  {name: 'STATUS', display: true, prop: 'status', order: 2, width: 50},
];
export const FILE_PREVIEW_TABLE_COLUMN = [
  {name: 'SALE_SITE', display: true, prop: 'Site vente', order: 1, width: 400},
  {name: 'CLIENT_ORDER', display: true, prop: 'Client commande', order: 2, width: 60},
  {name: 'CLIENT_NAME', display: true, prop: 'Nom client commande', order: 3, width: 100},
  {name: 'DATE', display: true, prop: 'Date', order: 4, width: 40},
  {name: 'AFFAIR', display: true, prop: 'Affaire', order: 5, width: 20},
  {name: 'NET_PRICE_EXCLUDEING_TAX', display: true, prop: 'priceNetHT', order: 6, width: 20},
  {name: 'SALSES_UNIT', display: true, prop: 'uniteVente', order: 7, width: 20},
  {name: 'TOTAL_PRICE', display: true, prop: 'totalPrice', order: 8, width: 50},
  {name: 'DISPLAY', display: true, prop: 'display', order: 9, width: 50},
];

export const  RECAP_TABLE_COLUMN = [
  { name: 'DESIGNATION',               display: true,   prop: 'designation',           order: 1, width: 400},
  { name: 'TOTAL_MONTH',               display: true,   prop: 'totalMonthQte',         order: 2, width: 40},
  { name: 'NET_PRICE_EXCLUDEING_TAX',  display: true,   prop: 'priceNetHT',            order: 3, width: 20},
  { name: 'SALSES_UNIT',               display: true,   prop: 'uniteVente',             order: 4, width: 20},
  { name: 'TOTAL_PRICE',               display: true,   prop: 'totalPrice',            order: 5, width: 50},
];
export const FILE_FIELDS_OBJECT_MAPPING = new Map([
  ['Date', {
    fieldName: 'date',
    required: true,
    validator: (date: string) => {
      return validationUtils.isInvalidDateFormat(date, 'DD/MM/YYYY') || validationUtils.isNotCurrentMonth(date);
    }
  }],
  ['Nom client commande', {fieldName: 'clientName', required: false, validator: null}],
  ['Affaire', {fieldName: 'affair', required: false, validator: null}],
  ['No commande', {fieldName: 'orderNumber', required: true, validator: null}],
  ['Ligne', {fieldName: 'line', required: true, validator: null}],
  ['Article', {fieldName: 'article', required: false, validator: null}],
  ['Désignation', {fieldName: 'designation', required: false, validator: null}],
  ['Quantité', {fieldName: 'quantity', required: true, validator: validationUtils.isNotFrenchDecimalNumber}],
  ['Unité vente', {fieldName: 'salesUnit', required: false, validator: null}],
  ['Prix net HT', {fieldName: 'priceHt', required: false, validator: null}],
]);
export const CONFIRMATION_ITEMS = [
  {action: 'reset', title: 'CONFIRMATION.RESETING.TITLE', body: 'CONFIRMATION.RESETING.BODY'},
  {action: 'reset-all', title: 'CONFIRMATION.RESETING_ALL.TITLE', body: 'CONFIRMATION.RESETING_ALL.BODY'},
  {action: 'hide', title: 'CONFIRMATION.HIDING.TITLE', body: 'CONFIRMATION.HIDING.BODY'},
  {action: 'show-all', title: 'CONFIRMATION.SHOWING_ALL.TITLE', body: 'CONFIRMATION.SHOWING_ALL.BODY'},
  {action: 'validate', title: 'CONFIRMATION.VALIDATING.TITLE', body: 'CONFIRMATION.VALIDATING.BODY'},
];
export const MONTHS = [
  { label: 'Jan', value: 1},
  { label: 'Fév', value: 2},
  { label: 'Mar', value: 3},
  { label: 'Avr', value: 4},
  { label: 'Mai', value: 5},
  { label: 'Jui', value: 6},
  { label: 'Juil', value: 7},
  { label: 'Aoû', value: 8},
  { label: 'Sep', value: 9},
  { label: 'Oct', value: 10},
  { label: 'Nov', value: 11},
  { label: 'Déc', value: 12}
];
export const AUTH_ROUTE = 'auth';
export const ERROR_ROUTE = 'error';
export const SAISIE_ROUTE = 'saisie';
export const HOME_ROUTE = 'accueil';
export const RECAP_ROUTE = 'summary';
export const UPLOAD_ROUTE = 'upload';
export const ADMIN_ROUTE = 'admin';
export const BASE_ROUTE = 'portail';
export const WILD_CARD_ROUTE = '**';
export const NOT_FOUND_ROUTE = 'page-not-found';


export const LOAD_CONFIG_FILE_ERROR = 'Problème de chargement de l\'application';


export const TOKEN = 'token';
export const USER_NAME = 'username';
export const VERSION = 'version';


export const SAISIE = 'SAISIE';
export const SUMMARY = 'SUMMARY';
