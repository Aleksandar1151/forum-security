import { ForumUser } from './../interfaces/user';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { TokenUser } from '../interfaces/tokenUser';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiURL = 'http://localhost:8080/api/forums';

  public static UserPermission: string;

  private verificationStatus = new BehaviorSubject<boolean>(false);


  public verificationStatus$ = this.verificationStatus.asObservable();

  constructor(private http: HttpClient, private router: Router) { }

  login(credentials: { username: string; password: string }): Observable<TokenUser> {
    console.log(credentials);
    return this.http.post<TokenUser>(`${this.apiURL}/login`, credentials);
  }

  register(newUser: ForumUser): Observable<ForumUser> {

    return this.http.post<ForumUser>(`${this.apiURL}/admin/users`, newUser);
  }


  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  getToken() {
    return localStorage.getItem('token');
  }

  isLoggedIn() {
    return !!localStorage.getItem('token');
  }

  getUser(username: string): Observable<ForumUser> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${sessionStorage["token"]}`
    });

    return this.http.get<ForumUser>(`${this.apiURL}/admin/users/${username}`, { headers });
  }


  sendWelcomeMail(mail: string): Observable<String> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${sessionStorage["token"]}`
    });

    console.log(mail);

    return this.http.post<String>(`${this.apiURL}/sendmail/${mail}`, {}, { responseType: 'text' as 'json' });
  }

  verifyCode(username: string, code: string): Observable<String> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${sessionStorage["token"]}`
    });

    console.log("headers" + JSON.stringify(headers));

    return this.http.post<String>(`${this.apiURL}/verify-code/${username}/${code}`, {}, { headers, responseType: 'text' as 'json' });
  }


  updateVerificationStatus(isVerified: boolean) {
    this.verificationStatus.next(isVerified);
  }




}
