import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AdminService } from '../services/admin.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private adminService: AdminService, private router: Router) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.adminService.getCurrentAdmin() != null && !this.adminService.isTokenExpired()) {
      return true;
    } else {
      if (this.adminService.isTokenExpired()) {
        this.adminService.setTokenExpiredMessage('Token expired for this user.');
        localStorage.removeItem('token');
      }
      this.router.navigate(['login']);
      return false;
    }
  }
}
