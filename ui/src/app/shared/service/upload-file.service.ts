import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {API_IMPORT, API_UPLOAD} from '../commun/PsvUri';
import {AbstractService} from './abstract.service';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService extends AbstractService {


  constructor(private https: HttpClient) {
    super();
  }

  upload(file: File): Observable<HttpEvent<{}>> {
    const data: FormData = new FormData();
    data.append('file', file);
    const newRequest = new HttpRequest('POST', `${this.API_ROUTES.base() + API_UPLOAD}`, data, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.https.request(newRequest);
  }
  uploadCsvData(csvData: string): Observable<HttpEvent<{}>> {
    const csvFile = new File([csvData], 'data.csv', { type: 'text/csv' });
    const data: FormData = new FormData();
    data.append('file', csvFile);
    const newRequest = new HttpRequest('POST', `${this.API_ROUTES.base() + API_IMPORT}`, data, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.https.request(newRequest);
  }
  downloadFile() {
  }
}
