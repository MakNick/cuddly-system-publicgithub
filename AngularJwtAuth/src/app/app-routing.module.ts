import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { PmComponent } from './pm/pm.component';
import { AdminComponent } from './admin/admin.component';
import { ConferenceComponent } from './tile-view/conference/conference.component';
import { PresentationdraftComponent } from './tile-view/presentationdraft/presentationdraft.component';
import { PresentationdraftdetailComponent } from './tile-view/presentationdraft/presentationdraftdetail/presentationdraftdetail.component';
import { ConferenceFormComponent } from './aanmeldformulier/conferenceForm/conferenceForm.component';
import { AanmeldformulierComponent } from "./aanmeldformulier/presentationdraftForm/aanmeldformulier.component";
 
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
        path: 'aanmeldformulierConference',
        component: ConferenceFormComponent
    },
    {
        path: 'aanmeldformulierPresentationDraftApplicant',
        component: AanmeldformulierComponent
    },
    {
        path: 'conference',
        component: ConferenceComponent
    },
    {
        path: 'conference/:id',
        component: PresentationdraftComponent
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
        redirectTo: 'home',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
