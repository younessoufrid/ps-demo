import {Component, OnDestroy, OnInit} from '@angular/core';
import {BASE_ROUTE} from '../../shared/commun/constants';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.scss']
})
export class PageNotFoundComponent implements OnInit , OnDestroy {

  constructor() {

  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
  }

  wrtiePath(subPath: string): string {
    return `/${BASE_ROUTE}/${subPath}`;
  }
}
