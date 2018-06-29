import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { Observable } from 'rxjs/Observable';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthGuard implements CanActivate {
  
  private tokenExpired: string = '';

  constructor(private userService: UserService, private router: Router) { }
  
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
      if (this.userService.getCurrentUser() != null && !this.userService.isTokenExpired()) {
        return true;
      } else {
        if (this.userService.isTokenExpired()) {
          this.userService.setTokenExpiredMessage('Token expired for this user.');
          localStorage.removeItem('token');
        }
        this.router.navigate(['/login']);
        return false;
      }
    }
}
