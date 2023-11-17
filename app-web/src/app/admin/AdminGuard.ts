import { Injectable, OnInit } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Member, PrvHome } from '../home/PrvHome';

@Injectable({
	providedIn: 'root',
})
export class AdminGuard implements CanActivate, OnInit {

	profil!: Member;

	constructor(
		private router: Router,
		private prvHome: PrvHome
	) { }


	canActivate(): boolean {
		this.getMyProfil()

		const userRole = this.profil?.role;
		if (userRole === 'admin') {
			return true;
		} else {
			this.router.navigate(['/menu/home']);
			console.log('no permission')
			return false;
		}
	}

	ngOnInit(): void {
	}

	getMyProfil() {
		this.prvHome.getMyProfil().subscribe(
			(data: any) => {
				this.profil = data
			}
		)
	}
}
