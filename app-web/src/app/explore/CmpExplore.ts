import { Component, OnInit } from '@angular/core';
import { Posting, PrvHome } from '../home/PrvHome';
import { Router } from '@angular/router';

@Component({
	selector: 'cmp-explore',
	templateUrl: './CmpExplore.html',
	styleUrls: ['./CmpExplore.css']
})
export class CmpExplore implements OnInit {

	lstPosting?: Posting[];


	constructor(
		private prvHome: PrvHome,
		private router: Router
	) { }

	ngOnInit(): void {
		 this.dataPosting();
	}

	dataPosting() {
		this.prvHome.allPost().subscribe({
			next: (data: any) => {
				this.lstPosting = data;
			},
			error: e => console.error(e)
		});
	}

}
