import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { LoginComponent } from './components/login/login.component';
import { AgentListComponent } from './components/agent-list/agent-list.component';
import { HomeComponent } from './components/home/home.component';
import { CategoryListComponent } from './components/category-list/category-list.component';
import { TypeListComponent } from './components/type-list/type-list.component';
import { EditTypeComponent } from './components/edit-type/edit-type.component';
import { EditCategoryComponent } from './components/edit-category/edit-category.component';
import { CommentListComponent } from './components/comment-list/comment-list.component';
import { AdditionalServicesListComponent } from './components/additional-services-list/additional-services-list.component';
import { EditAdditionalServiceComponent } from './components/edit-additional-service/edit-additional-service.component';


@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    LoginComponent,
    AgentListComponent,
    HomeComponent,
    CategoryListComponent,
    TypeListComponent,
    EditTypeComponent,
    EditCategoryComponent,
    CommentListComponent,
    AdditionalServicesListComponent,
    EditAdditionalServiceComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
