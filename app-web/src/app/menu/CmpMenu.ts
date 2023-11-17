import { Component, ElementRef, Injectable, OnDestroy, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Chats, Member, Posting, PrvHome } from '../home/PrvHome';
import { MatDialog } from '@angular/material/dialog';
// import { CmpCreate } from '../createPost/CmpCreate';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { WebSocketSubject } from 'rxjs/webSocket';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject, async, takeUntil } from 'rxjs';
import { CmpHome } from '../home/CmpHome';
import { Overlay, OverlayRef } from '@angular/cdk/overlay';
import { TemplatePortal } from '@angular/cdk/portal';

@Component({
	selector: 'cmp-menu',
	templateUrl: './CmpMenu.html',
	styleUrls: ['./CmpMenu.css'],
})


export class CmpMenu implements OnInit, OnDestroy {


	lstPosting?: Posting[];
	profil!: Member;
	private socketReceiver!: WebSocketSubject<any>;
	messages: string[] = [];
	messageToSend: string = '';
	unsubscriber = new Subject();
	newMessages!: Chats;
	notifNav = false;
	searchQuery!: string;
	searchResult?: Member[];
	isMenuOpen = false;
	searchNav = false;
 



	constructor(
		private prvHome: PrvHome,
		// public dialog: MatDialog,
		private router: Router,
		private _snackBar: MatSnackBar,
  


	) {
		this.prvHome.getMessage().pipe(takeUntil(this.unsubscriber))
			.subscribe(
				(data: any) => {
					this.newMessages = data.message
					if (this.newMessages?.senderId?.username != undefined) {
						const notif = 'You Have Message From ' + this.newMessages.senderId?.username
						// prvHome.saveNotif(notif);

						console.log(notif)
						const snackBarRef = this._snackBar.open(notif, 'Show',
							{
								horizontalPosition: 'right',
								verticalPosition: 'top',
								duration: 5000

							});
						snackBarRef.onAction().subscribe(() => {
							this.router.navigate(['menu/messages/' + this.newMessages.senderId?.username])
						});
					}
				}
			)
	}

	ngOnInit(): void {
		this.getProfil();
	}

	ngOnDestroy(): void {
		this.unsubscriber.next(null);
		this.unsubscriber.complete();
		this.unsubscriber.unsubscribe();
		// you need to unsubscribe to avoid memory leak
	}

	// openDialog(): void {

	// 	CmpCreate.show(this.dialog, null).subscribe(result => {
	// 		console.log('dialog result', result)
	// 		if (result === true) {
	// 			this.prvHome.needReload(true)
	// 		}
	// 	})

		// const dialogRef = this.dialog.open(CmpCreate, {
		// 	maxWidth: '600px',
		// 	maxHeight: '400px'
		// });
		// dialogRef.afterClosed().subscribe(result => {
		// 	console.log('dialog result', result)
		// 	this.prvHome.needReload()
			
		// })
	// }

	getProfil() {
		this.prvHome.getMyProfil().subscribe(
			(data: any) => {
				this.profil = data;
				if (this.profil.username != undefined) {
					console.log('profile ' + this.profil.username);
					if (this.profil.username) {
						this.socketReceiver = new WebSocketSubject(this.prvHome.webSocket + this.profil.username);
						this.socketReceiver.subscribe(
							(message) => {
								// console.log('receive message ', message);
								this.messages.push(message);
								this.prvHome.setMessage(message);
							},
							(error) => console.error(error)
						);
					}
				}
			},
			(error: any) => {
				if (error.status === 500) {
					console.error('error status 500')
				}
				console.error('error is ' + error);
			}
		)
	}


	logout() {
		console.log('logout')
		localStorage.removeItem("token");
		this.router.navigate(['login'])
	}

	// toggleSidenav() {
	// 	this.sidenavVisible = true
	// }


	onSearch() {
		this.prvHome.searchMem(this.searchQuery).subscribe({
			next: (data: any) => {
				this.searchResult = data
			},
		})
	}

	prfUser(username: any) {
		// this.router.navigateByUrl('/menu/messages/' + username)
		this.router.navigate(['/menu/prf/' + username])
	}
	

	//   private initializeWebSocket() {
	//     // Initialize WebSocket when the component is initialized
	//     if (this.profil.username) {
	//       this.socketReceiver = new WebSocketSubject('ws://localhost:8080/chat/' + this.profil.username);
	//       this.socketReceiver.subscribe(
	//         (message) => {
	//           console.log('receiver message ', message);
	//           this.messages.push(message);
	//         },
	//         (error) => console.error(error)
	//       );
	//     }
	//   }
}
