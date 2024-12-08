import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SaisieComponent} from './saisie.component';


const routes: Routes = [
  {
    path: '',
    component: SaisieComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SaisieRoutingModule { }
