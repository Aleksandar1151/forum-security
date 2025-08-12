import { Component, OnInit } from '@angular/core';
import { ForumService } from '../../services/forum.service';
import { ActivatedRoute } from '@angular/router';
import { ForumComment } from '../../interfaces/comment';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html'
})
export class TopicComponent implements OnInit {
  comments: ForumComment[] = [];
  newComment: string = '';
  topicId: string = '';
  topicName: string = ''; // To store the topic name

  constructor(private forumService: ForumService, private route: ActivatedRoute) { }

  ngOnInit() {


    this.route.paramMap.subscribe(params => {
      this.topicId = params.get('id')!;
      this.loadComments(this.topicId);
    });

    this.route.queryParamMap.subscribe(queryParams => {
      this.topicName = queryParams.get('name') || 'Unknown Topic';
    });

  }
  loadComments(topicId: string) {
    // Load comments for the given topic ID (mock or from API)
    /* this.comments = [
       { id :1, content: 'Great discussion!', author:'admin', createdTime:'1.1.1970', allowed:'1' },
       { id :1, content: 'Great 2!', author:'admin', createdTime:'1.1.1970', allowed:'1' },
       
     ];*/

    this.forumService.getComments(this.topicId).subscribe(data => {
      data.forEach(comment => {
        console.log(comment);
        if (comment.allowed == "true") this.comments.push(comment)
      });

    });
  }

  addComment() {

    var newComment = {
      author: sessionStorage["username"], content: this.newComment, allowed: "false", createdTime: new Date().toString()
    } as unknown as ForumComment

    this.forumService.addComment(this.topicId, newComment).subscribe(() => {
      this.newComment = '';
    });

    /*if (this.newComment.trim()) {
      this.comments.push(newComment);
      this.newComment = '';
    }*/
  }
}
