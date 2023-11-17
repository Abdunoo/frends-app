import { Component, OnInit } from '@angular/core';
import { Member, Notif, PrvHome } from '../home/PrvHome';

@Component({
	selector: 'cmp-my-notif',
	templateUrl: './CmpMyNotif.html',
	styleUrls: ['./CmpMyNotif.css']
})
export class CmpMyNotif implements OnInit {

	notif?: Notif[];
	profil?: Member;


	constructor(
		private prvHome: PrvHome,

	) { }

	ngOnInit() {
		this.getMyNotif()
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

	getMyNotif() {
		this.prvHome.getNotif().subscribe({
			next: (data: any) => {
				this.notif = data
			}
		})
	}




}
