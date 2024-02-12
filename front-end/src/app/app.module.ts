import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MainComponent } from './view/main/main.component';
import { HeaderComponent } from './view/header/header.component';
import { FormComponent } from './view/form/form.component';
import { TaskListComponent } from './view/task-list/task-list.component';
import { TaskComponent } from './view/task/task.component';
import { LoginComponent } from './view/login/login.component';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { getAuth, provideAuth } from '@angular/fire/auth';
import {AuthService} from "./service/auth.service";
import {authGuard} from "./guard/auth.guard";
import { LoaderComponent } from './view/loader/loader.component';
import {TaskService} from "./service/task-service";
import {HttpClientModule, provideHttpClient, withInterceptors} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {SpringTaskService} from "./service/spring-task.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";
import {errorInterceptor} from "./interceptor/error.interceptor";
import {RoutingModule} from "./routing.module";




@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    HeaderComponent,
    FormComponent,
    TaskListComponent,
    TaskComponent,
    LoginComponent,
    LoaderComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    RoutingModule,
    provideFirebaseApp(() => initializeApp({
      "apiKey": "AIzaSyDXphsbqPNgpIGQ44cKdJde72avvW4Gpu8",
      "authDomain": "to-do-app-a89b3.firebaseapp.com",
      "projectId": "to-do-app-a89b3",
      "storageBucket": "to-do-app-a89b3.appspot.com",
      "messagingSenderId": "1032060344951",
      "appId": "1:1032060344951:web:5f224d29e090e96b1ac472"
    })),
    provideAuth(() => getAuth()),
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      enableHtml: true,
      positionClass: 'toast-bottom-center',
      preventDuplicates: true,
      progressBar: true,
      timeOut: 1500
    })

  ],
  providers: [AuthService,{provide: TaskService, useClass: SpringTaskService}
    , provideHttpClient(withInterceptors([errorInterceptor]))],
  bootstrap: [AppComponent]
})
export class AppModule { }
