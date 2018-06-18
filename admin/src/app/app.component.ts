import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from './services/admin.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private adminService: AdminService, private router: Router) { }

  ngOnInit() {
    this.adminIsLoggedIn();
  }

  adminIsLoggedIn() {
    return this.adminService.getCurrentAdmin() != null;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }
}
