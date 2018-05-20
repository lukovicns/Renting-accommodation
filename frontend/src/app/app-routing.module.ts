import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AccommodationListComponent } from './components/accommodation-list/accommodation-list.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AccommodationDetailComponent } from './components/accommodation-detail/accommodation-detail.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ApartmentListComponent } from './components/apartment-list/apartment-list.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'accommodations', children: [
      { path: '', component: AccommodationListComponent },
      { path: ':id', component: AccommodationDetailComponent }
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
