import { Component, OnDestroy, OnInit } from '@angular/core';
import { Member, Posting, Product, PrvHome } from '../home/PrvHome';
import { Subject, takeUntil } from 'rxjs';
import { Router } from '@angular/router';

@Component({
	selector: 'cmp-market',
	templateUrl: './CmpMarket.html',
	styleUrls: ['./CmpMarket.css']
})
export class CmpMarket implements OnInit, OnDestroy {

	lstProduct?: Product[]
	showForm = false
	unsubscriber = new Subject();
	profil?: Member;
	isMenuOpen = false;


	constructor(
		private prvHome: PrvHome,
		private router: Router,

	) { }

	ngOnInit(): void {
		this.getProfil()
		this.getAllProduct()
		this.prvHome.getNeedReload()
			.pipe(takeUntil(this.unsubscriber))
			.subscribe((data: any) => {
				if (data) {
					console.log('need reload data = ' + data)
					this.showForm = false
					this.getAllProduct()
				} else {
					this.showForm = false
				}

			});
	}

	ngOnDestroy(): void {
		this.unsubscriber.next(null);
		this.unsubscriber.complete();
		this.unsubscriber.unsubscribe();
		// you need to unsubscribe to avoid memory leak
	}

	getAllProduct() {
		this.prvHome.allProduct().subscribe({
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
			next:(data:any)=>{
				console.log('delete succes')
				this.getAllProduct()
			}
		})
	}

	myProduct(){
		this.router.navigate(['menu/market/myproduct/list'])
	}

}
