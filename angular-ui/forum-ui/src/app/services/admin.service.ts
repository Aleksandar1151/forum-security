import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ForumUser } from '../interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiURL = 'http://localhost:8080/api/forums';

  constructor(private http: HttpClient) { }

  getUsers() {
    return this.http.get<ForumUser[]>(`${this.apiURL}/admin/users`);
  }

  approveUser(userId: string) {
    return this.http.post(`${this.apiURL}/approve`, { userId });
  }

  assignRole(userId: string, role: string) {
    return this.http.post(`${this.apiURL}/assign-role`, { userId, role });
  }
}
