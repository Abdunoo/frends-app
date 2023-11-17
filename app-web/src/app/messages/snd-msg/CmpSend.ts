import { Component, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Chats, Member, PrvHome } from 'src/app/home/PrvHome';
import { WebSocketSubject } from 'rxjs/webSocket';
import { HttpClient } from '@angular/common/http';
import { Subject, takeUntil } from 'rxjs';


@Component({
	selector: 'cmp-send',
	templateUrl: './CmpSend.html',
	styleUrls: ['./CmpSend.css']
})
export class CmpSend implements OnInit, OnDestroy {
	profil!: Member;
	member!: Member;
	username: any;
	myMessage!: Chats[];
	newMessages!: Chats;
	unsubscriber = new Subject();
	// myChats!: string[];
	// yourChats!: string[];
	allChats!: any;
	haveMsg = false;



	constructor(
		private route: ActivatedRoute,
		private prvHome: PrvHome,
		private http: HttpClient

	) {

		this.prvHome.getMessage().pipe(takeUntil(this.unsubscriber))
			.subscribe(
				(data: any) => {
					// this.newMessages = data
					this.myMessage?.push(data.message);
					// this.myMessage.push({
					// 	id: 0,
					// 	messages:data.message,
					// 	senderId:{username:this.username}
					// })
				}
			)
	}

	ngOnDestroy(): void {
		this.unsubscriber.next(null);
		this.unsubscriber.complete();
		this.unsubscriber.unsubscribe();
		// you need to unsubscribe to avoid memory leak
	}

	ngOnInit() {
		this.getProfil();
		this.route.params.subscribe(params => {
			this.username = params['username'];
			this.getMyMessage();
			this.haveMsg = false;
		})
		this.memDetail(this.username);
	}

	getProfil() {
		this.prvHome.getMyProfil().subscribe(
			(data: any) => {
				this.profil = data;
				//   if (this.profil.username) {
				// 		console.log('profile ' + this.profil.username);
				// 		this.socketReceiver = new WebSocketSubject('ws://localhost:8080/chat/' + this.profil.username); // Initialize socketReceiver here
				// 		this.socketReceiver.subscribe(
				// 			 (message: any) => {
				// 				  // Handle the received message here
				// 				  console.log('Received message:', message);
				// 				  this.messages.push(message); // Assuming the message object has a 'message' property
				// 			 },
				// 			 (error: any) => {
				// 				  console.error('WebSocket error:', error);
				// 			 }
				// 		);
				//   }
			},
			(error: any) => {
				if (error.status === 500) {
					console.error('error status 500');
				}
				console.error('error is ' + error);
			}
		);
	}

	getTargetById(){
		this.prvHome.oriDataById
	}

	memDetail(username: string) {
		this.prvHome.memDetail(username).subscribe(
			(data: any) => {
				this.member = data;
			},
			(error: any) => {
				if (error.status === 500) {
					console.error('error status 500')
				}
				console.error('error is ' + error);
			}
		)
	}

	sendMessage(message: any) {
		this.haveMsg = false;
		this.myMessage.push({
			id: 0,
			senderId: { username: this.profil?.username },
			getterId: { username: this.username },
			messages: message
		})
		this.prvHome.sendMessage(this.username, message).subscribe({
			error: error => console.error(error)
		})

	}
	getMyMessage() {
		this.prvHome.getMyMessage(this.username).subscribe(
			(data: any) => {
				this.myMessage = data
				if (this.myMessage.length === 0) {
					this.haveMsg = true;
				}

				// this.myChats = [];
				// this.yourChats = [];
				// this.allChats = [this.myChats,this.yourChats]

				// for (const message of data) {
				// 	if (message.senderId.username === this.profil.username && message.getterId.username === this.username) {
				// 	  this.myChats.push(message.messages);
				// 	  this.haveMsg = true; 
				// 	}
				// 	if (message.senderId.username === this.username) {
				// 	  this.yourChats.push(message.messages);
				// 	  this.haveMsg = true; 
				// 	}
				//  }
			},
			(error: any) => {
				if (error.status === 500) {
					console.error('error status 500');
				}
				console.error('error is ' + error);
			}
		);
	}


}
