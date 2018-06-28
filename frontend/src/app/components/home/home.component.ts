import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  animations: [fadeIn()]
})
export class HomeComponent implements OnInit {

  private currentUser: string = '';

  constructor(private userService: UserService) { }

  ngOnInit() {
    if(this.userService.getCurrentUser() != null) {
      this.currentUser = this.userService.getCurrentUser()['email'];
    }
  }

  title = 'Renting accommodation app';
}
