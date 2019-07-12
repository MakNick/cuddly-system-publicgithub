import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

import { AuthService } from '../auth/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { AuthLoginInfo } from '../auth/login-info';
import { MatSnackBar } from '@angular/material';
import { LoginFailComponent } from './snackbar-loginfail.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  private loginInfo: AuthLoginInfo;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private snackBar: MatSnackBar, private router: Router) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  openSnackBar() {
    if (this.isLoginFailed) {
      this.snackBar.openFromComponent(LoginFailComponent, {
        duration: 3500,
        panelClass: ['snackbar-color']
      });
    }
  }

  onSubmit() {
    console.log(this.form);

    this.loginInfo = new AuthLoginInfo(
      this.form.username,
      this.form.password);

    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();
        this.reloadPage();
        if (this.roles[0] == 'ROLE_USER'){
          this.router.navigateByUrl('/user');
        } else if (this.roles[0] == 'ROLE_ADMIN'){
          this.router.navigateByUrl('/conference');
        } else {
          this.reloadPage();
        }
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
        this.openSnackBar();
      }
    );
  }

  reloadPage() {
    window.location.reload();
  }
}
