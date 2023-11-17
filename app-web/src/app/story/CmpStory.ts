import { Component, OnDestroy, OnInit } from '@angular/core';
import { Member, Posting, PrvHome, Story } from '../home/PrvHome';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { Subject, takeUntil } from 'rxjs';
import { CmpCreateStr } from '../create-myStory/CmpCreateStr';

@Component({
	selector: 'cmp-story',
	templateUrl: './CmpStory.html',
	styleUrls: ['./CmpStory.css']
})
export class CmpStory implements OnInit, OnDestroy {
	storys!: Story[];
	unsubscriber = new Subject();
	myStory!: Story;
	haveStory = false;
	profil!: Member;


	constructor(
		private prvHome: PrvHome,
		private router: Router,
		public dialog: MatDialog,
		private route: ActivatedRoute

	) { }

	ngOnInit(): void {
		this.allStory()
		this.getMyStory();
		this.prvHome.getNeedReload()
			.pipe(takeUntil(this.unsubscriber))
			.subscribe((data: any) => {
				this.getMyStory()
			});

	}

	ngOnDestroy(): void {
		this.unsubscriber.next(null);
		this.unsubscriber.complete();
		this.unsubscriber.unsubscribe();
		// you need to unsubscribe to avoid memory leak
	}

	// getProfil() {
	// 	this.prvHome.getMyProfil().subscribe(
	// 		(data: any) => {
	// 			this.profil = data;

	// 		},
	// 		(error: any) => {
	// 			if (error.status === 500) {
	// 				console.error('error status 500')
	// 			}
	// 			console.error('error is ' + error);
	// 		}
	// 	)
	// }

	allStory() {
		this.prvHome.getAllStory().subscribe({
			next: (data: any) => {
				this.storys = data
			}
		})
	}

	getMyStory() {
		this.prvHome.getMyStory().subscribe(
			(data: any) => {
				if (data) {
					this.myStory = data
					this.haveStory = true
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

	showStory(username: any) {
		// this.router.navigateByUrl('/menu/messages/' + username)
		this.router.navigate(['/story/' + username])
	}

	openDialog(): void {
		// CmpCreateStr.show(this.dialog).subscribe(result => {
		// 	console.log('dialog story result', result)
		// 	if (result === true) {
		// 		this.prvHome.needReload(true)
		// 	}
		// })
		const dialogRef = this.dialog.open(CmpCreateStr, {
			width: '600px',
			height: '400px'
		});
		dialogRef.afterClosed().subscribe(result => {
			console.log('The dialog was closed');
			this.getMyStory();

		});
	}


}
