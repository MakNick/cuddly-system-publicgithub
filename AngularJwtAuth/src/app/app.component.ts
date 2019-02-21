import {Component, OnInit, HostBinding} from '@angular/core';
import {TokenStorageService} from './auth/token-storage.service';

import {
  trigger,
  state,
  style,
  animate,
  transition,
  // ...
} from '@angular/animations';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0}),
        animate('.5s', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('.5s', style({ opacity: 0}))
      ])
    ]),
    trigger("nav-link", [

      state('open', style({
        left: "5px",
        borderLeft: "2px solid #FFE600"
      })),

      state('closed', style({
        left: "0px",
        borderLeft: "0px solid #FFE600"
      })),

      transition('open => closed', [
        animate('.5s')
      ]),

      transition('closed => open', [
        animate('.2s')
      ])

    ])

  ]
})

export class AppComponent implements OnInit {

  private roles: string[];
  private authority: string;

  constructor(private tokenStorage: TokenStorageService) {
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        } else if (role === 'ROLE_PM') {
          this.authority = 'pm';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }
  }
}
