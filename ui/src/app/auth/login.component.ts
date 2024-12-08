import {Component, OnInit} from '@angular/core';
import { UntypedFormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from './service/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {BASE_ROUTE, HOME_ROUTE, SAISIE_ROUTE} from '../shared/commun/constants';
import { AuthRequest } from '../shared/models/beans';
import {HttpStatusCode} from '@angular/common/http';

@Component({
  selector: 'portail-saisie-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  loginForm: FormGroup;
  authRequest: AuthRequest;
  loginError: string;
  isLoginError = false;
  constructor(private fb: UntypedFormBuilder,
              private authService: AuthService,
              private router: Router,
              private route: ActivatedRoute
  ) {
  }


  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });

    this.loginForm.valueChanges.subscribe(() => {
      this.isLoginError = false;
    });
  }
  login(): void {
    this.authRequest = this.loginForm.value;
    this.authService.authenticate(this.authRequest).subscribe(response => {
        if (response) {
          const redirectUrl = this.route.snapshot.queryParams.returnUrl || `${BASE_ROUTE}/${HOME_ROUTE}`;
          this.router.navigate([redirectUrl]);
        }
      }, (error) => {
      if (error.status === HttpStatusCode.BadRequest) {
        this.isLoginError = true;
        this.loginError = error.error.message;
      }
      }
    );
  }

}
