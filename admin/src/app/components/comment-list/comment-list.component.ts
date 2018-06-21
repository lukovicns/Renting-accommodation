import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { CommentService } from '../../services/comment.service';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.css'],
  animations: [fadeIn()]
})
export class CommentListComponent implements OnInit {

  private comments = [];
  private errorMessage: string;

  constructor(private commentService: CommentService) { }

  ngOnInit() {
    this.commentService.getWaitingComments()
    .subscribe(res => {
      this.comments = res;
    }, err => {
      console.log(err);
    })
  }

  approveComment(commentId) {
    this.commentService.approveComment(commentId)
    .subscribe(res => {
      this.ngOnInit();
    }, err => {
      this.errorMessage = err['error'];
    })
  }

  declineComment(commentId) {
    this.commentService.declineComment(commentId)
    .subscribe(res => {
      this.ngOnInit();
    }, err => {
      this.errorMessage = err['error'];
    })
  }
}
