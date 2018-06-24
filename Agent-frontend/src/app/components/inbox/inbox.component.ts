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
  private receivedMessages: any;
  private sentMessages: any;
  private message = {};
  private show = false;
  private msg;

  constructor(
    private messageService: MessageService,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.initReceivedMessages();
    this.initSentMessages();
  }

  initReceivedMessages() {
    this.messageService.getReceivedMessages()
    .subscribe(res => {
        if(typeof(res).valueOf() == 'object' && res['return'].length == undefined)
        {
            this.show = true;
            this.receivedMessages = res['return'];
            this.receivedMessages = Array.of(this.receivedMessages);
            
        }else if(res['return'] == "No messagess available.")
        {
            this.msg = res['return'];
            this.show = false;
            
        }else
        {
            this.show = true;
            this.receivedMessages = res['return'];
        }
        
//        this.receivedMessages = res['return'];
//        this.receivedMessages = Array.of(this.receivedMessages);
        console.log(this.receivedMessages);
    }, err => {
      console.log(err);
    })
  }

  initSentMessages() {
    this.messageService.getSentMessages()
    .subscribe(res => {
        console.log(res['return'])
        if(typeof(res).valueOf() == 'object' && res['return'].length == undefined)
        {
            this.show = true;
            this.sentMessages = res['return'];
            this.sentMessages = Array.of(this.sentMessages);
            
        }else if(res['return'] == "No accommodations available.")
        {
            this.msg = res['return'];
            this.show = false;
            
        }else
        {
            this.show = true;
            this.sentMessages = res['return'];
        }
        
//        console.log(res);
//        this.sentMessages = res['return'];
//        this.sentMessages = Array.of(this.sentMessages);
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

  deleteReceivedMessage(message) {
      console.log(message.id)
      
    this.messageService.deleteReceivedMessage(message)
    .subscribe(res => {
        this.ngOnInit();
      this.initReceivedMessages();
    }, err => {
      console.log(err);
    })
  }

  deleteSentMessage(message) {
    this.messageService.deleteSentMessage(message)
    .subscribe(res => {
        this.ngOnInit();
      this.initSentMessages();
    }, err => {
      console.log(err);
    })
  }
}
