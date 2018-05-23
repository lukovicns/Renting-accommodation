import { Component } from '@angular/core';
import { UserService } from './services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  constructor(private userService: UserService, private router: Router) { }

  userIsLoggedIn() {
    return this.userService.getCurrentUser() != null;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }
}
