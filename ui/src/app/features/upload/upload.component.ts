import { Component } from '@angular/core';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss']
})
export class UploadComponent {
  items = [
    'Les dates saisies doivent appartenir au mois en cours.',
    'Les quantités doivent être des valeurs non négatives ; dans le cas contraire, la ligne sera rejetée.',
    'Les numéros de commande ne doivent pas être nuls et doivent correspondre à des commandes valides.',
    'Le numéro de ligne ne doit pas être nul.',
    'Les prix nets HT doivent être valides ; la virgule doit être représentée par une virgule (,) dans le cas des nombres décimaux.',
  ];
}
