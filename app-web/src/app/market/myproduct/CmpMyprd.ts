import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { Router } from '@angular/router';
import { Member, Product, PrvHome } from 'src/app/home/PrvHome';

@Component({
	selector: 'cmp-myprd',
	templateUrl: './CmpMyprd.html',
	styleUrls: ['./CmpMyprd.css']
})
export class CmpMyprd implements OnInit {

	lstProduct?: Product[]
	showForm = false
	profil?: Member;


	constructor(
		private prvHome: PrvHome,
		private router: Router,

	) { }

	ngOnInit(): void {
		this.getProfil()
		this.getAllProduct()
	}

	getAllProduct() {
		this.prvHome.myProduct().subscribe({
			next: (data: any) => {
				this.lstProduct = data
			}
		})
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

	delete(id: any) {
		this.prvHome.deletePrd(id).subscribe({
			next: (data: any) => {
				console.log('delete succes')
				this.getAllProduct()
			}
		})
	}


}
