import { NgModule } from '@angular/core';

import { SaisieRoutingModule } from './saisie-routing.module';
import {SharedModule} from '../../shared/shared.module';
import {SaisieComponent} from './saisie.component';
import { LineTextComponent } from './components/line-text/line-text.component';

@NgModule({
  declarations: [SaisieComponent, LineTextComponent],
    imports: [
        SaisieRoutingModule,
        SharedModule,
    ]
})
export class SaisieModule { }
