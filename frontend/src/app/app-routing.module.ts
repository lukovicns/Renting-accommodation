import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AccommodationListComponent } from './components/accommodation-list/accommodation-list.component';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { RegisterComponent } from './components/register/register.component';
import { AccommodationDetailComponent } from './components/accommodation-detail/accommodation-detail.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { RecoveryEmailComponent } from './components/recovery-email/recovery-email.component';
import { RecoveryQuestionComponent } from './components/recovery-question/recovery-question.component';
import { AuthGuard } from './guards/auth.guard';
import { SearchApartmentsComponent } from './components/search-apartments/search-apartments.component';
import { ReservationFormComponent } from './components/reservation-form/reservation-form.component';
import { ReservationListComponent } from './components/reservation-list/reservation-list.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'accommodations', children: [
      { path: '', component: AccommodationListComponent },
      { path: ':id', children: [
        { path: '', component: AccommodationDetailComponent },
        { path: 'search', component: SearchApartmentsComponent },
        { path: 'apartments/:apartmentId/make-reservation', component: ReservationFormComponent, canActivate: [AuthGuard] }
      ] }
    ]
  },
  { path: 'reservations', canActivate: [AuthGuard], children: [
    { path: '', component: ReservationListComponent }
  ] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'recovery', component: RecoveryEmailComponent},
  { path: 'question', component: RecoveryQuestionComponent},
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
