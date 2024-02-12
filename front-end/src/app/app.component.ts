import { Component } from '@angular/core';
import {AuthService} from "./service/auth.service";

@Component({
  selector: 'app-root',
  styleUrl: './app.component.scss',
  template:`
      @if (!authService.isInitialized()) {
          <app-loader/>
      } @else {
          <router-outlet/>
      }
  `
})
export class AppComponent {
  title = 'to-do-app';
  constructor(public authService: AuthService) {
  }
}
