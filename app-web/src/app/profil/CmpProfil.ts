import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Member, Posting, PrvHome } from '../home/PrvHome';

@Component({
	selector: 'cmp-profil',
	templateUrl: './CmpProfil.html',
	styleUrls: ['./CmpProfil.css']
})
export class CmpProfil implements OnInit {
	profil!: Member;
	post!: Posting[];
	username!: string;


	constructor(
		private route: ActivatedRoute,
		private prvHome: PrvHome

	) { }
	ngOnInit() {
		this.route.params.subscribe(params => {
			this.username = params['username'];
			this.prvHome.memDetail(this.username).subscribe({
				next: (data: any) => {
					this.profil = data;
					if (this.profil.id) {
						this.getPost(this.profil.id)
					}
				}
			})
			
		})
	}

	getPost(id:number) {
		this.prvHome.getMemPost(id).subscribe({
			next: (data: any) => {
				this.post = data
			}
		})
	}
}
