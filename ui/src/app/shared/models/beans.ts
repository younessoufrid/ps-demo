export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  username: string;
  fullname: string;
  userRoles: string[];
}

export class SimpleSelectItem {
  label: string;
  value: any;
}
/*** order beans ***/
export interface SaleSite {
  id: string;
  label: string;
  reference: string;
}
export interface Cdr {
  id: string;
  label: string;
  reference: string;
}
export interface Client {
  id: string;
  name: string;
}
export interface Affair {
  id: number;
  reference: string;
  label: string;
}
export interface Order {
  id: number;
  reference: string;
  label: string;
  number: string;
}
export interface LightOrderLine {
  date: Date;
  idOrder: number;
}
export interface OrderLine {
  id: number;
  designation: string;
  textLigne: string;
  quantity: number;
  priceNetHT: number;
  uniteVente: string;
  totalMonthQte: number;
  totalPrice: number;
  show: boolean;
  date: Date;
  line: number;
  idOrder: number;
  soumis: boolean;
}
export interface OrderFilters {
  saleSite: number;
  cdr: number;
  client: number;
  affair: number;
  order: number;
  date: string;
  action: string;
}
export interface OrderDataResponse {
  saleSites: SaleSite[];
  cdrs: Cdr[];
  clients: Client[];
  affairs: Affair[];
  orders: Order[];
  orderLines: OrderLine[];
  summary: SummaryOrderLines[];
}
export interface OrderFiltersData {
  saleSites: SimpleSelectItem[];
  cdrs: SimpleSelectItem[];
  clients: SimpleSelectItem[];
  affairs: SimpleSelectItem[];
  orders: SimpleSelectItem[];
  orderLines: OrderLine[];
  summary: SummaryOrderLines[];
}
/*** ***/
export interface YMDDate {
  year: number;
  month: number;
  day: number;
}

export class SummaryOrderLines {
  designation: string;
  line: number;
  summaryItems: Array<{date: string, quantity: number}>;
  priceNetHT: number;
  uniteVente: string;
  totalMonthQte: number;
  totalPrice: number;
  soumis: boolean;
}
