import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccommodationComponent } from './components/accommodation/accommodation.component';
import { AccommodationTableComponent } from './components/accommodation-table/accommodation-table.component';
import { HomeComponent } from './components/home/home.component';
import { ApartmentComponent } from './components/apartment/apartment.component';
import { AccommodationDetailComponent } from './components/accommodation-detail/accommodation-detail.component';
import { ApartmentTableComponent } from './components/apartment-table/apartment-table.component';
import { PricePlanComponent } from './components/price-plan/price-plan.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { ApartmentDetailComponent } from './components/apartment-detail/apartment-detail.component';
import { ReservationListComponent } from './components/reservation-list/reservation-list.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { MessageDetailComponent } from './components/message-detail/message-detail.component';
import { SendMessageComponent} from './components/send-message/send-message.component'

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'accommodation', component: AccommodationComponent },
  { path: 'accommodationsTable', component: AccommodationTableComponent },
  { path: 'apartment/:id', component: ApartmentComponent },
  { path: 'accommodationDetail/:id', component: AccommodationDetailComponent},
  { path: 'apartmentTable/:id', component: ApartmentTableComponent},
  { path: 'pricePlan', component: PricePlanComponent},
  { path: 'notFound', component: NotFoundComponent},
  { path: 'apartmentDetail/:id', component: ApartmentDetailComponent},
  { path: 'reservations', component: ReservationListComponent},
  { path: 'inbox', children: [
         { path: '', component: InboxComponent },
         { path: ':direction/:id', component: MessageDetailComponent },
  ] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'reply/:id', component: SendMessageComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
