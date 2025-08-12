import { Component, OnInit } from '@angular/core';
import { ForumService } from '../../services/forum.service';
import { Topic } from '../../interfaces/topic';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forum',
  templateUrl: './forum.component.html'
})
export class ForumComponent implements OnInit {
  topics: Topic[] = [];

  constructor(private forumService: ForumService, private router: Router) { }

  ngOnInit() {


    const userRole = sessionStorage.getItem('role');

    // Redirect to /forum if sessionStorage doesn't have a role
    if (!userRole) {
      this.router.navigate(['/login']);
      return;
    }

    // Redirect to /403 if the role is not 'admin'
    if (userRole !== 'admin' && userRole !== 'moderator' && userRole !== 'member') {
      this.router.navigate(['/login']);
      return;
    }






    this.forumService.getTopics().subscribe(data => {
      this.topics = data;
    });

    /* this.topics = [
       { id: 1, name: 'Angular Basics', description: 'Discussion about the basics of Angular.' },
       { id: 2, name: 'Advanced TypeScript', description: 'Explore advanced features of TypeScript.' },
       { id: 3, name: 'Frontend Frameworks', description: 'Comparing popular frontend frameworks.' },
       { id: 4, name: 'Web Development Tools', description: 'Tools that improve web development.' },
       { id: 5, name: 'UI/UX Best Practices', description: 'Best practices for creating user-friendly interfaces.' }
     ];*/
  }
}
