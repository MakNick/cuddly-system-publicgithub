import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { PmComponent } from './pm/pm.component';

import { httpInterceptorProviders } from './auth/auth-interceptor';
import { ConferenceComponent } from './tile-view/conference/conference.component';
import { PresentationdraftComponent } from './tile-view/presentationdraft/presentationdraft.component';
import { PresentationdraftdetailComponent } from './tile-view/presentationdraft/presentationdraftdetail/presentationdraftdetail.component';
import { PsDetailService } from './tile-view/presentationdraft/psDetail.service';
import { ConferenceFormComponent } from './aanmeldformulier/conferenceForm/conferenceForm.component';
import { AanmeldformulierComponent } from "./aanmeldformulier/presentationdraftForm/aanmeldformulier.component";
import { AanmeldformulierMaterialComponent, AanmeldformulierFeedbackComponent } from './aanmeldformulier/presentationdraftformMaterial/aanmeldformulierMaterial.component';
import { MaterialsModule } from '../material-module';
import { DialogWindowComponent } from './aanmeldformulier/dialogWindow/dialogWindow.component';

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
    PresentationdraftComponent,
    PresentationdraftdetailComponent,
    ConferenceFormComponent,
    AanmeldformulierComponent,
    AanmeldformulierMaterialComponent,
    DialogWindowComponent,
    AanmeldformulierFeedbackComponent
  ],
  imports: [ 
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MaterialsModule
  ],
  entryComponents: [DialogWindowComponent, AanmeldformulierFeedbackComponent],
  providers: [httpInterceptorProviders, PsDetailService],
  bootstrap: [AppComponent]
})
export class AppModule { }
