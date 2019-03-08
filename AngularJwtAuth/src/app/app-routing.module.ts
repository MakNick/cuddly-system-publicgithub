import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { PmComponent } from './pm/pm.component';
import { AdminComponent } from './admin/admin.component';
import { ConferenceComponent } from './tile-view/conference/conference.component';
import { PresentationDraftComponent } from './tile-view/presentationdraft/presentation-draft.component';
import { PresentationdraftdetailComponent } from './tile-view/presentationdraft/presentationdraftdetail/presentation-draft-detail.component';
import { ConferenceFormComponent } from './forms/conferenceForm/conferenceForm.component';
import { AanmeldformulierComponent } from "./forms/presentationdraftForm/aanmeldformulier.component";
 
const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'user',
        component: UserComponent
    },
    {
        path: 'pm',
        component: PmComponent
    },
    {
        path: 'admin',
        component: AdminComponent
    },
    {
        path: 'conference/conferenceForm',
        component: ConferenceFormComponent
    },
    {
        path: 'presentationDraftApplicantForm',
        component: AanmeldformulierComponent
    },
    {
        path: 'conference',
        component: ConferenceComponent
    },
    {
        path: 'conference/:id',
        component: PresentationDraftComponent
    },
    {
        path: 'conference/:id/presentationDraftDetail',
        component: PresentationdraftdetailComponent
    },
    {
        path: 'auth/login',
        component: LoginComponent
    },
    {
        path: 'signup',
        component: RegisterComponent
    },
    {
        path: '',
        redirectTo: 'auth/login',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
