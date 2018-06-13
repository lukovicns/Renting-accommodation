import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AccommodationListComponent } from './components/accommodation-list/accommodation-list.component';
import { AccommodationDetailComponent } from './components/accommodation-detail/accommodation-detail.component';
import { AccommodationService } from './services/accommodation.service';
import { UserService } from './services/user.service';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ApartmentListComponent } from './components/apartment-list/apartment-list.component';
import { ApartmentService } from './services/apartment.service';
import { ProfileComponent } from './components/profile/profile.component';
import { RecoveryEmailComponent } from './components/recovery-email/recovery-email.component';
import { RecoveryQuestionComponent } from './components/recovery-question/recovery-question.component';
import { AuthGuard } from './guards/auth.guard';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    AccommodationListComponent,
    AccommodationDetailComponent,
    PageNotFoundComponent,
    ApartmentListComponent,
    ProfileComponent,
    RecoveryEmailComponent,
    RecoveryQuestionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    UserService,
    AccommodationService,
    ApartmentService,
    AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
