import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AdminService } from '../../services/admin.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  animations: [fadeIn()]
})
export class UserListComponent implements OnInit {

  private users = [];

  constructor(
    private userService: UserService,
    private adminService: AdminService
  ) { }

  ngOnInit() {
    this.initUsers();
  }

  initUsers() {
    this.userService.getUsers()
    .subscribe(res => {
      this.users = res;
    }, err => {
      console.log(err);
    })
  }

  blockUser(userId) {
    this.adminService.blockUser(userId)
    .subscribe(res => {
      console.log(res);
      this.initUsers();
    }, err => {
      console.log(err);
    })
  }

  activateUser(userId) {
    this.adminService.activateUser(userId)
    .subscribe(res => {
      console.log(res);
      this.initUsers();
    }, err => {
      console.log(err);
    })
  }

  deleteUser(userId) {
    this.adminService.deleteUser(userId)
    .subscribe(res => {
      console.log(res);
      this.initUsers();
    }, err => {
      console.log(err);
    })
  }
}
