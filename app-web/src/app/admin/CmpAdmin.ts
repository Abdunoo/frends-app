import { Component, OnInit } from '@angular/core';
import { Member, PrvHome } from '../home/PrvHome';

@Component({
	selector: 'cmp-admin',
	templateUrl: './CmpAdmin.html',
	styleUrls: ['./CmpAdmin.css']
})
export class CmpAdmin implements OnInit {

	lstMember!: Member[];
	profil!: Member;
	displayedColumns: string[] = ['username', 'email', 'role'];
	dataSource = this.lstMember;
	showingDetail = false;
	selectedId!: number;

	constructor(
		private prvHome: PrvHome,
	) { }

	ngOnInit(): void {
		this.getProfil()
		this.getAllMembers()
	}

	getProfil() {
		// const id = localStorage.getItem("id");
		this.prvHome.getMyProfil().subscribe(
			(data: any) => {
				this.profil = data;
			},
			(error: any) => {
				if (error.status === 500) {
					console.error('error status 500')
				}
				console.error('error is ' + error);
			})
	}

	getAllMembers() {
		this.prvHome.getMember().subscribe(
			(data: any) => {
				this.lstMember = data
			},
			(error: any) => {
				if (error.status === 500) {
					console.error('error status 500')
				}
				console.error('error is ' + error);
			})
	}


}
