import { Injectable } from '@angular/core';
import {Auth, authState, GoogleAuthProvider, signInWithPopup, signOut, User} from "@angular/fire/auth";
import {Router} from "@angular/router";
import {BehaviorSubject} from "rxjs";

@Injectable()
export class AuthService {

  private initialized = false;
  private user: User | null = null;

  constructor(private auth:Auth ,private routerService: Router) {
    authState(auth).subscribe(user => {
      this.initialized = true;
      this.user=user;
      if (user) {
        this.routerService.navigateByUrl('/app');
      }else{
        this.routerService.navigateByUrl('/login');
      }
    });
  }

  signIn() {
    signInWithPopup(this.auth, new GoogleAuthProvider());
  }

  isInitialized() {
    return this.initialized;
  }

  getPrincipal(){
    return this.user;
  }

  signOut() {
    signOut(this.auth);
  }
}
