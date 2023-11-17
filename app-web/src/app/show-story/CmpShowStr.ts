import { Component, OnInit } from '@angular/core';
import { Member, Posting, PrvHome, Story } from '../home/PrvHome';
import { ActivatedRoute } from '@angular/router';

@Component({
	selector: 'cmp-show-str',
	templateUrl: './CmpShowStr.html',
	styleUrls: ['./CmpShowStr.css']
})
export class CmpShowStr implements OnInit {
	storys!: Posting[];
	selectedStr!: Story;
	profil!: Member;
	username = '';
	isMenuOpen = false;

	constructor(
		private prvHome: PrvHome,
		private route: ActivatedRoute
	) { }

	ngOnInit(): void {
		this.route.params.subscribe(params => {
			this.username = params['username'];
			this.selectedStory(this.username);
			console.log('username is = ' + this.username)
		})
		this.getProfil()
		// this.allStory()
	}

	getProfil() {
		this.prvHome.getMyProfil().subscribe(
			(data: any) => {
				this.profil = data;

			},
			(error: any) => {
				if (error.status === 500) {
					console.error('error status 500')
				}
				console.error('error is ' + error);
			}
		)
	}

	// allStory() {
	// 	this.prvHome.allPost().subscribe({
	// 		next: (data: any) => {
	// 			this.storys = data
	// 		}
	// 	})
	// }

	selectedStory(username: string) {
		this.prvHome.selectedStory(username).subscribe({
			next: (data: any) => {
				this.selectedStr = data
			}
		})
	}

	deleteMyStory() {
		this.prvHome.deleteStory(this.selectedStr.memId?.username).subscribe({
			next: (data: any) => {
				console.log('succes delete mystory')
			}
		})
	}
}
