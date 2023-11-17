import { Component, OnInit } from '@angular/core';
import { FormControl, MinLengthValidator, NgModel, NgModelGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Checkout, Product, PrvHome } from 'src/app/home/PrvHome';

@Component({
	selector: 'cmp-checkout',
	templateUrl: './CmpCheckout.html',
	styleUrls: ['./CmpCheckout.css']
})
export class CmpCheckout implements OnInit {
	product!: Product;
	checkout: Checkout = {};
	message = '';

	constructor(
		private route: ActivatedRoute,
		private prvHome: PrvHome,
		private router: Router,
		private _snackBar: MatSnackBar,

	) { }

	ngOnInit(): void {
		const productId = this.route.snapshot.paramMap.get('id'); // Get product ID from route parameter

		this.prvHome.productById(productId).subscribe((data) => {
			this.product = data;
		});
	}


	insertCheckout() {
		if (this.checkout.firstName == null || '') {
			this.message = 'column first name cannot empty'
		} else if (this.checkout.lastName == null || '') {
			this.message = 'column last name cannot empty'

		} else if (this.checkout.email == null || '') {
			this.message = 'column email cannot empty'

		} else if (this.checkout.streetAddress == null || '') {
			this.message = 'column address cannot empty'

		} else if (this.checkout.city == null || '') {
			this.message = 'column city cannot empty'

		} else if (this.checkout.province == null || '') {
			this.message = 'column province cannot empty'

		} else if (this.checkout.posCode == null || '') {
			this.message = 'column posCode cannot empty'

		} else if (this.checkout.phone == null || '') {
			this.message = 'column phone cannot empty'

		} else {
			this.router.navigate(['/menu/market'])
			this.checkout.productId = this.product
			this.prvHome.insertCheckout(this.checkout).subscribe({
				next: (data: any) => {
				}
			})
			this.message = 'Checkout Succest'
		}
		const snackBarRef = this._snackBar.open(this.message, 'Info',
			{
				horizontalPosition: 'center',
				verticalPosition: 'bottom',
				duration: 5000

			});
	}

}
