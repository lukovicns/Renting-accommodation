import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { MessageService } from '../../services/message.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.css'],
  animations: [fadeIn()]
})
export class InboxComponent implements OnInit {

  private receivedTab: boolean = true;
  private sentTab: boolean = false;
  private receivedMessages = [];
  private sentMessages = [];
  private message = {};

  constructor(
    private messageService: MessageService,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.initReceivedMessages();
    this.initSentMessages();
  }

  initReceivedMessages() {
    this.messageService.getReceivedMessages(this.userService.getCurrentUser()['id'])
    .subscribe(res => {
      this.receivedMessages = res;
    }, err => {
      console.log(err);
    })
  }

  initSentMessages() {
    this.messageService.getSentMessages(this.userService.getCurrentUser()['id'])
    .subscribe(res => {
      this.sentMessages = res;
    }, err => {
      console.log(err);
    })
  }

  receivedTabActive() {
    this.sentTab = false;
    this.receivedTab = true;
    document.querySelector('#sent').classList.remove('active');
    document.querySelector('#received').classList.add('active');
  }

  sentTabActive() {
    this.receivedTab = false;
    this.sentTab = true;
    document.querySelector('#received').classList.remove('active');
    document.querySelector('#sent').classList.add('active');
  }

  showReceivedMessage(message) {
    // const user = this.userService.getCurrentUser();
    // this.messageService.getReceivedMessage(user.id, message.id)
    // .subscribe(res => {
      
    // }, err => {

    // });
  }

  deleteReceivedMessage(message) {

  }

  deleteSentMessage(message) {
    this.messageService.deleteSentMessage(message)
    .subscribe(res => {
      this.initSentMessages();
    }, err => {
      console.log(err);
    })
  }
}
