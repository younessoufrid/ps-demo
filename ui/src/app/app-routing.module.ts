import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {PageNotFoundComponent} from './pages/page-not-found/page-not-found.component';
import {ErrorComponent} from './pages/error/error.component';
import {AUTH_ROUTE, BASE_ROUTE, ERROR_ROUTE, NOT_FOUND_ROUTE, WILD_CARD_ROUTE} from './shared/commun/constants';
import {authGuard} from './shared/guards/auth.guard';
import {LoginGuard} from './shared/guards/login.guard';


const routes: Routes = [
  {
    path: BASE_ROUTE,
    children: [
      {
        path: '',
        canActivate: [authGuard],
        loadChildren: () => import('./features/features.module').then(m => m.FeaturesModule)
      },
      {
        path: AUTH_ROUTE,
        canActivate: [LoginGuard],
        loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
      }
    ]
  },
  {
    path: '', redirectTo : `${BASE_ROUTE}/${AUTH_ROUTE}`, pathMatch: 'full'
  },
  {
    path: ERROR_ROUTE,
    component: ErrorComponent,
  },
  {
    path: NOT_FOUND_ROUTE,
    component: PageNotFoundComponent,
  },
  {
    path: WILD_CARD_ROUTE,
    redirectTo: NOT_FOUND_ROUTE,
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
