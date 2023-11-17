import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product, PrvHome } from 'src/app/home/PrvHome';

@Component({
	selector: 'cmp-buy',
	templateUrl: './CmpBuy.html',
	styleUrls: ['./CmpBuy.css']
})
export class CmpBuy implements OnInit {

	product: Product = {
		id: 0,
		stock: 0,
		sold: 0
	};
	constructor(
		private route: ActivatedRoute,
		private prvHome: PrvHome,
		private router: Router,


	) { }

	ngOnInit(): void {
		const productId = this.route.snapshot.paramMap.get('id'); // Get product ID from route parameter

		this.prvHome.productById(productId).subscribe((data) => {
			this.product = data;
		});
	}
	buyProduct(id: number) {
		this.product.stock -= 1
		this.product.sold += 1

		this.router.navigate(['/menu/checkout/' + id])

	}

	addToCart() {

	}
}
