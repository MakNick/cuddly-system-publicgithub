import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { PmComponent } from './pm/pm.component';

import { httpInterceptorProviders } from './auth/auth-interceptor';
import { ConferenceComponent } from './tile-view/conference/conference.component';
import { PresentationDraftComponent } from './tile-view/presentationdraft/presentation-draft.component';
import { PresentationdraftdetailComponent } from './tile-view/presentationdraft/presentationdraftdetail/presentationdraftdetail.component';
import { PresentationDraftDetailService } from './tile-view/presentationdraft/presentationdraftdetail/presentation-draft-detail.service';
import { ConferenceFormComponent } from './aanmeldformulier/conferenceForm/conferenceForm.component';
import { AanmeldformulierComponent } from "./aanmeldformulier/presentationdraftForm/aanmeldformulier.component";
import { AanmeldformulierFeedbackComponent } from './aanmeldformulier/presentationdraftForm/aanmeldformulier.component';
import { MaterialsModule } from '../material-module';
import { DialogWindowComponent } from './aanmeldformulier/dialogWindow/dialogWindow.component';

import { FilterPipe } from "./pipes/filter-pipe";
import { SaveDialog } from './tile-view/presentationdraft/presentationdraftdetail/savedialog.component';
import { DeleteDialog } from './tile-view/presentationdraft/presentationdraftdetail/deletedialog.component';

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
    PresentationdraftdetailComponent,
    ConferenceFormComponent,
    AanmeldformulierComponent,
    DialogWindowComponent,
    AanmeldformulierFeedbackComponent,
    FilterPipe,
    SaveDialog,
    DeleteDialog
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
  entryComponents: [DialogWindowComponent, AanmeldformulierFeedbackComponent, SaveDialog, DeleteDialog],
  providers: [httpInterceptorProviders, PresentationDraftDetailService],
  bootstrap: [AppComponent]
})
export class AppModule { }