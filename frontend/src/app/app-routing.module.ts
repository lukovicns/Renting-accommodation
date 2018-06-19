import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './components/home/home.component';
import { AccommodationDetailComponent } from './components/accommodation-detail/accommodation-detail.component';
import { AccommodationListComponent } from './components/accommodation-list/accommodation-list.component';
import { RecoveryQuestionComponent } from './components/recovery-question/recovery-question.component';
import { SearchApartmentsComponent } from './components/search-apartments/search-apartments.component';
import { ReservationFormComponent } from './components/reservation-form/reservation-form.component';
import { ReservationListComponent } from './components/reservation-list/reservation-list.component';
import { RecoveryEmailComponent } from './components/recovery-email/recovery-email.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { RegisterComponent } from './components/register/register.component';
import { ProfileComponent } from './components/profile/profile.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { SendMessageComponent } from './components/send-message/send-message.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { ApartmentDetailComponent } from './components/apartment-detail/apartment-detail.component';
import { MessageDetailComponent } from './components/message-detail/message-detail.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'accommodations', children: [
      { path: '', component: AccommodationListComponent },
      { path: 'search', component: SearchApartmentsComponent },
      { path: ':id', children: [
        { path: '', component: AccommodationDetailComponent },
        { path: 'apartments', children: [
          { path: ':apartmentId', children: [
            { path: '', component: ApartmentDetailComponent },
            { path: 'make-reservation', component: ReservationFormComponent, canActivate: [AuthGuard] },
            { path: 'send-message', component: SendMessageComponent, canActivate: [AuthGuard] }
          ] },
        ] }
      ] }
    ]
  },
  { path: 'reservations', canActivate: [AuthGuard], children: [
    { path: '', component: ReservationListComponent }
  ] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'inbox', canActivate: [AuthGuard], children: [
    { path: '', component: InboxComponent },
    { path: ':direction/:id', component: MessageDetailComponent }
  ] },
  { path: 'recovery', component: RecoveryEmailComponent},
  { path: 'question', component: RecoveryQuestionComponent},
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
