import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export abstract class AbstractService {

  protected constructor() { }
  protected  readonly API_ROUTES = {
    base: () => `${environment.back_url}`,
  };
}
