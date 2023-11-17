import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Member, PrvHome } from '../home/PrvHome';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
	selector: 'cmp-messages',
	templateUrl: './CmpMessages.html',
	styleUrls: ['./CmpMessages.css']
})
export class CmpMessages implements OnInit {
	lstMember?: Member[];
	profil!: Member;
	showMenu = false;

	constructor(private prvHome: PrvHome,
		private router: Router) {
	}

	ngOnInit(): void {
		this.getProfil();

	}

	getProfil() {
		this.prvHome.getMyProfil().subscribe(
			(data: any) => {
				this.profil = data;
				if (this.profil.username) {
					this.getMem(this.profil.username);
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

	getMem(username: any) {
		this.prvHome.getOtherMem(username).subscribe({
			next: (data: any) =>
				this.lstMember = data
		})
	}

	sendMsg(username: any) {
		// this.router.navigateByUrl('/menu/messages/' + username)
		this.router.navigate(['/menu/messages/' + username])
	}



}
