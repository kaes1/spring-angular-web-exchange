import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AdminLoggedInGuard implements CanActivate {

  constructor(private jwtHelper: JwtHelperService,
              private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const token = localStorage.getItem('jwt');
    const role = localStorage.getItem('role');

    if (!token || this.jwtHelper.isTokenExpired(token)) {
      this.router.navigate(['login']);
      return false;
    }

    if (role !== 'ROLE_ADMIN') {
      this.router.navigate(['home']);
      return false;
    }

    return true;
  }
}
