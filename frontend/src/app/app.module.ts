import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AccommodationDetailComponent } from './components/accommodation-detail/accommodation-detail.component';
import { AccommodationListComponent } from './components/accommodation-list/accommodation-list.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AccommodationService } from './services/accommodation.service';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ApartmentListComponent } from './components/apartment-list/apartment-list.component';
import { RecoveryEmailComponent } from './components/recovery-email/recovery-email.component';
import { RecoveryQuestionComponent } from './components/recovery-question/recovery-question.component';
import { SearchApartmentsComponent } from './components/search-apartments/search-apartments.component';
import { ReservationFormComponent } from './components/reservation-form/reservation-form.component';
import { ReservationListComponent } from './components/reservation-list/reservation-list.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ReservationService } from './services/reservation.service';
import { ApartmentService } from './services/apartment.service';
import { UserService } from './services/user.service';
import { AuthGuard } from './guards/auth.guard';
import { AppComponent } from './app.component';
import { MessageService } from './services/message.service';
import { SendMessageComponent } from './components/send-message/send-message.component';
import { InboxComponent } from './components/inbox/inbox.component';
import { TruncatePipe } from './pipes/truncate.pipe';
import { CityService } from './services/city.service';
import { AdditionalServiceService } from './services/additional-service.service';
import { ApartmentDetailComponent } from './components/apartment-detail/apartment-detail.component';
import { ApartmentAdditionalServiceService } from './services/apartment-additional-service.service';
import { MessageDetailComponent } from './components/message-detail/message-detail.component';
import { EditReservationComponent } from './components/edit-reservation/edit-reservation.component';
import { AccommodationTypeService } from './services/accommodation-type.service';
import { AccommodationCategoryService } from './services/accommodation-category.service';
import { CommentService } from './services/comment.service';
import { RatingService } from './services/rating.service';
import { ImageService } from './services/image.service';


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
    RecoveryQuestionComponent,
    SearchApartmentsComponent,
    SearchApartmentsComponent,
    ReservationFormComponent,
    ReservationListComponent,
    SendMessageComponent,
    InboxComponent,
    TruncatePipe,
    ApartmentDetailComponent,
    MessageDetailComponent,
    EditReservationComponent
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
    ApartmentAdditionalServiceService,
    UserService,
    AccommodationService,
    ApartmentService,
    ReservationService,
    MessageService,
    CityService,
    AdditionalServiceService,
    AccommodationTypeService,
    AccommodationCategoryService,
    RatingService,
    CommentService,
    ImageService,
    AuthGuard
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
