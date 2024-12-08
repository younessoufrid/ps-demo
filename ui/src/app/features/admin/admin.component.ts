import {Component, Input, OnInit} from '@angular/core';
function getUsers() {
  const users = [];
  for (let i = 0; i < 60 ; i++) {
    users.push({
      nom: 'User-' + i,
      prenom: 'User-User' + i
    });
  }
  return users;
}
@Component({
  selector: 'portail-saisie-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit{
  @Input()  users = [];
  searchUser(name: string) {
    console.log(name);
  }

  ngOnInit(): void {
    this.users = getUsers();
    console.log(this.users);
  }

}
