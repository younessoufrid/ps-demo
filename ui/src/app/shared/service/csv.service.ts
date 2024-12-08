import {Injectable} from '@angular/core';
import * as Papa from 'papaparse';
import {Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CsvService {
  constructor() {
  }

  parseCSV(file: File): Observable<any> {
    return new Observable(observer => {
      Papa.parse(file, {
        header: true,
        dynamicTyping: true,
        worker: true,
        skipEmptyLines: true,
        complete: (results) => {
          observer.next(results.data); // Emit parsed CSV data
          observer.complete(); // Complete the Observable
        },
        error: (error) => {
          observer.error(error); // Emit error
        }
      });
    });
  }

  jsonToCsv(data: any[], options: object): string {
    return Papa.unparse(data, options);
  }

  processFile(file: File): Observable<any[]> {
    return this.parseCSV(file).pipe(
      catchError(error => {
        throw error; // Propagate the error further if necessary
      })
    );
  }
}
