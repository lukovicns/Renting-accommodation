 import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccommodationService } from './services/accommodation.service';
import { ReservationService } from './services/reservation.service';
import { AccommodationComponent } from './components/accommodation/accommodation.component';
import { ApartmentComponent } from './components/apartment/apartment.component';
import { AccommodationTableComponent } from './components/accommodation-table/accommodation-table.component';
import { HomeComponent } from './components/home/home.component';
import { AccommodationDetailComponent } from './components/accommodation-detail/accommodation-detail.component';
import { ApartmentTableComponent } from './components/apartment-table/apartment-table.component';
import { PricePlanComponent } from './components/price-plan/price-plan.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { ApartmentDetailComponent } from './components/apartment-detail/apartment-detail.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { ReservationListComponent } from './components/reservation-list/reservation-list.component';
import { MessageDetailComponent } from './components/message-detail/message-detail.component';
import { SendMessageComponent } from './components/send-message/send-message.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
//import { DomSanitizerImpl } from '@angular/platform-browser/src/security/dom_sanitization_service';     
import { CookieService } from 'ngx-cookie-service';
import { UserService } from './services/user.service';
import { HttpInterceptorService } from './services/httpinterceptor.service';
import { HTTP_INTERCEPTORS } from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    AccommodationComponent,
    ApartmentComponent,
    AccommodationTableComponent,
    HomeComponent,
    AccommodationDetailComponent,
    ApartmentTableComponent,
    PricePlanComponent,
    NotFoundComponent,
    ApartmentDetailComponent,
    InboxComponent,
    ReservationListComponent,
    MessageDetailComponent,
    SendMessageComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule
//    DomSanitizerImpl
  ],
  providers: [
    AccommodationService,
    ReservationService,
    CookieService, 
    UserService,
    { 
        provide: HTTP_INTERCEPTORS,
        useClass: HttpInterceptorService,
        multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
