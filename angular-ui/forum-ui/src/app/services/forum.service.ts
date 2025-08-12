import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic';
import { ForumComment } from '../interfaces/comment';

@Injectable({
  providedIn: 'root'
})
export class ForumService {
  private apiURL = 'http://localhost:8080/api/forums';

  constructor(private http: HttpClient) { }

  getTopics(): Observable<Topic[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${sessionStorage["token"]}`
    });

    return this.http.get<Topic[]>(`${this.apiURL}`, { headers });
  }

  getComments(topicId: string): Observable<ForumComment[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${sessionStorage["token"]}`
    });
    return this.http.get<ForumComment[]>(`${this.apiURL}/${topicId}/comments`, { headers });
  }

  addComment(topicId: string, comment: ForumComment) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${sessionStorage["token"]}`
    });

    return this.http.post(`${this.apiURL}/${topicId}/comments`, comment, { headers });
  }

  editComment(topicId: string, comment: ForumComment) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${sessionStorage["token"]}`
    });

    return this.http.put(`${this.apiURL}/${topicId}/comments/${comment.id}`, comment, { headers });
  }

  deleteComment(comment: ForumComment) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${sessionStorage["token"]}`
    });

    return this.http.delete(`${this.apiURL}/comments/${comment.id}`, { headers });
  }


  createTopic(topic: Topic) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${sessionStorage["token"]}`
    });

    console.log(topic);

    return this.http.post(`${this.apiURL}`, topic, { headers });
  }




}
