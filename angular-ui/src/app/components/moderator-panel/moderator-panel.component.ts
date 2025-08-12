import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { ForumComment } from '../../interfaces/comment';
import { ForumService } from '../../services/forum.service';
import { Topic } from '../../interfaces/topic';
import { AuthService } from '../../services/auth.service';
import { ForumUser } from '../../interfaces/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-moderator-panel',
  templateUrl: './moderator-panel.component.html',
  styleUrl: './moderator-panel.component.css'
})
export class ModeratorPanelComponent {
  users: any[] = [];
  topics: Topic[] = [];

  currentUser?: ForumUser;

  AllowVisible: boolean = false;
  EditVisible: boolean = false;
  DeleteVisible: boolean = false;


  constructor(private forumService: ForumService, private router: Router, private adminService: AdminService, private authService: AuthService) { }

  displayedComments: ForumComment[] = [];


  fetchComments() {

    this.forumService.getTopics().subscribe(data => {
      this.topics = data;
      data.forEach(topic => {
        topic.comments!.forEach(element => {
          if (element.allowed == "false") this.displayedComments.push(element);
        });
      });
    });
  }

  allowComment(comment: ForumComment) {
    comment.allowed = "true";


    this.displayedComments = this.displayedComments.filter(c => c.allowed !== "true");


    const topic = this.topics.find(topic =>
      topic.comments!.some(c => c.id === comment.id)
    );

    if (topic) {

      this.forumService.editComment(topic.id!.toString(), comment).subscribe(data => {

      });
    } else {
      console.error("Comment not found in any topic");
    }
  }


  editComment(comment: ForumComment) {

    const topic = this.topics.find(topic =>
      topic.comments!.some(c => c.id === comment.id)
    );

    if (topic) {

      this.forumService.editComment(topic.id!.toString(), comment).subscribe(data => {

      });
    } else {
      console.error("Comment not found in any topic");
    }

  }

  deleteComment(comment: ForumComment) {
    comment.allowed = "true";

    this.displayedComments = this.displayedComments.filter(c => c.allowed != "true");

    this.forumService.deleteComment(comment).subscribe(data => {

    });
  }



  ngOnInit() {

    const userRole = sessionStorage.getItem('role');


    if (!userRole) {
      this.router.navigate(['/login']);
      return;
    }


    if (userRole !== 'admin' && userRole !== 'moderator') {
      this.router.navigate(['/login']);
      return;
    }











    this.fetchComments();
    this.authService.getUser(sessionStorage["username"]).subscribe(data => {
      this.currentUser = data;

      let permissionsArray = this.currentUser!.permission.split('');

      if (permissionsArray[0] == "1") this.AllowVisible = true;
      if (permissionsArray[1] == "1") this.EditVisible = true;
      if (permissionsArray[2] == "1") this.DeleteVisible = true;

    });







  }

  approveUser(userId: string) {
    /*c this.adminService.approveUser(userId).subscribe(() => {
       // Handle approval logic
     });*/
  }

  assignRole(userId: string, role: string) {
    this.adminService.assignRole(userId, role).subscribe(() => {
      // Handle role assignment logic
    });
  }
}
