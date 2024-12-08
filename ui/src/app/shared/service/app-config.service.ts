import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AppConfigService {
  private config: object = null;
  private baseUrl: string = environment.baseUrl;
  constructor(private httpClient: HttpClient) { }


   /**
     *check if configuration is loaded
    */
  public isConfigLoaded() {
    return this.config ? true : false;
  }

  /**
   * Use to get the data found in the config file
   */
  public getConfig(key: any) {
    return this.config ? this.config[key] : null;
  }

  public loadConfig(): Promise<any> {
    return new Promise<any>((resolve) => {
       this.httpClient.get<any>(`${this.baseUrl}/assets/config/version.json`)
         .pipe(
           catchError((error: any): any => {
             console.log('version file could not be loaded' + error);
             resolve(false);
           })
         )
         .subscribe(data => {
           if (data) {
             this.config = data
             resolve(true);
           }
         }
       );
    });
  }
}
