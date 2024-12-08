import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../auth/service/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit{
  user = '';
  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.user = this.authService.getUserName()?.toUpperCase();
  }

}
