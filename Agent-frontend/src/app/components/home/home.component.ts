import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  animations: [fadeIn()]
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
  }
  
  redirect()
  {
      this.router.navigate(['./SomewhereElse']);
  }
  
  userIsLoggedIn() {
      return this.userService.getCurrentUser() != null;
    }

  title = 'The Booking App';
}
