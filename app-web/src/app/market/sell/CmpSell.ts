import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { AssetToUpload } from 'src/app/createPost/AssetToUpload';
import { Member, Product, PrvHome } from 'src/app/home/PrvHome';

declare var Compress: any;

@Component({
	selector: 'cmp-sell',
	templateUrl: './CmpSell.html',
	styleUrls: ['./CmpSell.css']
})
export class CmpSell implements OnInit {

	selectedImage: string | ArrayBuffer | null = null;
	lstProduct: Product[] = [];
	photo: any;
	profil!: Member;
	product!: Product;
	name = new FormControl('', [Validators.required, Validators.maxLength(255)]);
	price = new FormControl('', [Validators.required, Validators.maxLength(15)]);



	constructor(
		private prvHome: PrvHome
	) { }

	ngOnInit(): void {
		this.getProfil();
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

	onFileSelected(event: any) {
		const file = event.target.files[0];

		if (file) {
			const reader = new FileReader();
			reader.onload = (e: any) => {
				this.selectedImage = e.target.result;
			};
			reader.readAsDataURL(file);
		}

		const options = {
			targetSize: 0.5,
			quality: 0.75,
			maxWidth: 1024,
			maxHeight: 1024
		};

		const compress = new Compress(options);
		const files = [...event.target.files];
		compress.compress(files).then((results: any) => {
			this.photo = results[0].photo;
			let toUpload = new AssetToUpload;
			toUpload.file = this.photo;
		});
	}

	sell(name: string, price: number, description: string, stock: number) {
		let toUpload = new AssetToUpload();
		toUpload.file = new File([this.photo?.data], this.photo?.name);

		this.prvHome.uploadProduct(toUpload).subscribe({
			next: (data: any) => {
				let url = data.imgUrl
				if (url) {
					this.product = {
						id:0,
						name: name,
						price: price,
						imgUrl: url,
						description: description,
						stock: stock,
						sold: 0
					}
					this.prvHome.insertProduct(this.product).subscribe({
						next: (data: any) => {
							console.log('succes sell')
							this.prvHome.needReload(true)
							// this.prvHome.allProduct().subscribe({
							// 	next: (data: any) => {
							// 		if (data) {
							// 			this.prvHome.setProduct(data)
							// 		}
							// 	}
							// })
						}
					})
				}
			}
		})
	}

	cancel() {
		this.prvHome.needReload(false)
	}


}
