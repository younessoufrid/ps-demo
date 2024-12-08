import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ADMIN_ROUTE, HOME_ROUTE, RECAP_ROUTE, SAISIE_ROUTE, UPLOAD_ROUTE} from '../shared/commun/constants';
import {authGuard} from '../shared/guards/auth.guard';


const routes: Routes = [
  {
    path: '',
    children : [
      {
        path: HOME_ROUTE,
        canActivate: [authGuard],
        loadChildren: () => import('./home/home.module').then(m => m.HomeModule)
      },
      {
        path: SAISIE_ROUTE,
        canActivate: [authGuard],
        loadChildren: () => import('./saisie/saisie.module').then(m => m.SaisieModule)
      },
      {
        path: RECAP_ROUTE,
        canActivate: [authGuard],
        loadChildren: () => import('./summary/summary.module').then(m => m.SummaryModule)
      },
      {
        path: UPLOAD_ROUTE,
        canActivate: [authGuard],
        loadChildren: () => import('./upload/upload.module').then(m => m.UploadModule)
      },
      {
        path: ADMIN_ROUTE,
        canActivate: [authGuard],
        loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
      },
      {
        path: '', redirectTo: HOME_ROUTE, pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeaturesRoutingModule { }
