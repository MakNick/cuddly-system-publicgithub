import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatMenuModule,
  MatIconModule,
  MatToolbarModule,
  MatGridListModule
} from '@angular/material';

import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatRippleModule} from '@angular/material/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { PmComponent } from './pm/pm.component';

import {FilterPipe} from "./pipes/filter-pipe";
import { httpInterceptorProviders } from './auth/auth-interceptor';
import { ConferenceComponent } from './tile-view/conference/conference.component';
import { PresentationDraftComponent } from './tile-view/presentationdraft/presentation-draft.component';
import { PresentationDraftDetailComponent } from './tile-view/presentationdraft/presentationdraftdetail/presentation-draft-detail.component';
import { PresentationDraftDetailService } from './tile-view/presentationdraft/presentationdraftdetail/presentation-draft-detail.service';
import { ConferenceFormComponent } from './aanmeldformulier/conferenceForm/conferenceForm.component';
import { AanmeldformulierComponent } from "./aanmeldformulier/presentationdraftForm/aanmeldformulier.component";
import {MaterialModule} from "./material.module";
import {NguCarouselModule} from "@ngu/carousel";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UserComponent,
    RegisterComponent,
    HomeComponent,
    AdminComponent,
    PmComponent,
    ConferenceComponent,
    PresentationDraftComponent,
    PresentationDraftDetailComponent,
    ConferenceFormComponent,
    AanmeldformulierComponent,
    FilterPipe,
  ],
  imports: [ 
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NguCarouselModule,
    ReactiveFormsModule,
    MaterialModule
    
  ],
  providers: [httpInterceptorProviders, PresentationDraftDetailService],
  bootstrap: [AppComponent]
})
export class AppModule { }
