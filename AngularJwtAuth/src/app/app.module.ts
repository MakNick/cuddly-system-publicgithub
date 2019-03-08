import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { PmComponent } from './pm/pm.component';

import { FilterPipe } from "./pipes/filter-pipe";
import { httpInterceptorProviders } from './auth/auth-interceptor';
import { ConferenceComponent } from './tile-view/conference/conference.component';
import { PresentationDraftComponent } from './tile-view/presentationdraft/presentation-draft.component';
import { PresentationdraftdetailComponent } from './tile-view/presentationdraft/presentationdraftdetail/presentation-draft-detail.component';
import { PresentationDraftDetailService } from './tile-view/presentationdraft/presentationdraftdetail/presentation-draft-detail.service';
import { ConferenceFormComponent } from './forms/conferenceForm/conferenceForm.component';
import { AanmeldformulierComponent } from "./forms/presentationdraftForm/aanmeldformulier.component";
import { MaterialModule } from "../material.module";
import { NguCarouselModule } from "@ngu/carousel";
import { ScrollingModule } from "@angular/cdk/scrolling";
import { AanmeldformulierFeedbackComponent } from './forms/presentationdraftForm/aanmeldformulier.component';
import { DialogWindowComponent } from './forms/dialogWindow/dialogWindow.component';
import { PresentationDraftTileComponent } from './tile-view/presentationdraft/presentation-draft-tile/presentation-draft-tile.component';
import { SaveDialog } from './tile-view/presentationdraft/presentationdraftdetail/savedialog.component';
import { DeleteDialog } from './tile-view/presentationdraft/presentationdraftdetail/deletedialog.component';
import { MailDialog } from './tile-view/presentationdraft/presentationdraftdetail/maildialog.component';
import { AngularFontAwesomeModule } from 'angular-font-awesome';


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
    PresentationDraftTileComponent,
    DeleteDialog,
    MailDialog,
    SaveDialog
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NguCarouselModule,
    ReactiveFormsModule,
    MaterialModule,
    ScrollingModule,
    AngularFontAwesomeModule
  ],
  entryComponents: [DialogWindowComponent, AanmeldformulierFeedbackComponent, SaveDialog, DeleteDialog, MailDialog],
  providers: [httpInterceptorProviders, PresentationDraftDetailService],
  bootstrap: [AppComponent]
})
export class AppModule { }
