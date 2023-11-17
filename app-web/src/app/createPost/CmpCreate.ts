// import { Component, Inject, OnInit } from '@angular/core';
// import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
// import { Member, Posting, PrvHome } from '../home/PrvHome';
// import { Router } from '@angular/router';
// import { AssetToUpload } from './AssetToUpload';

// declare var Compress: any;

// @Component({
// 	selector: 'cmp-create',
// 	templateUrl: './CmpCreate.html',
// 	styleUrls: ['./CmpCreate.css']
// })
// export class CmpCreate implements OnInit {


// 	selectedImage: string | ArrayBuffer | null = null;
// 	profil!: Member
// 	posting!: Posting;
// 	photo: any;
// 	postId = 0;
// 	constructor(
// 		private prvHome: PrvHome,
// 		private router: Router,
// 		// private dialogRef: MatDialogRef<CmpCreate, boolean>,
// 		// @Inject(MAT_DIALOG_DATA) public data: any
// 	) { }

// 	ngOnInit(): void {
// 		this.getProfil();
// 		// if (this.data) {
// 		// 	this.postId = this.data.post.id
// 		// 	this.selectedImage = this.data.post.imgUrl
// 		// 	this.posting = {
// 		// 		caption: this.data.post.caption,
// 		// 		imgUrl: this.selectedImage
// 		// 	}
// 		// 	// this.posting.caption = this.data.post.caption
// 		// 	console.log('caption is = ' + this.posting.caption)
// 		// }
// 	}

// 	getProfil() {
// 		// const id = localStorage.getItem("id");
// 		this.prvHome.getMyProfil().subscribe(
// 			(data: any) => {
// 				this.profil = data;
// 			},
// 			(error: any) => {
// 				if (error.status === 500) {
// 					console.error('error status 500')
// 				}
// 				console.error('error is ' + error);
// 			})
// 	}

// 	// const file = event.target.files[0];
// 	// if (file) {
// 	// 	const reader = new FileReader();
// 	// 	reader.onload = (e: any) => {
// 	// 		this.selectedImage = e.target.result;
// 	// 	};
// 	// 	reader.readAsDataURL(file);
// 	// }
// 	onFileSelected(event: any) {
// 		const file = event.target.files[0];

// 		if (file) {
// 			const reader = new FileReader();
// 			reader.onload = (e: any) => {
// 				this.selectedImage = e.target.result;
// 			};
// 			reader.readAsDataURL(file);
// 		}

// 		const options = {
// 			targetSize: 0.5,
// 			quality: 0.75,
// 			maxWidth: 1024,
// 			maxHeight: 1024
// 		};

// 		const compress = new Compress(options);
// 		const files = [...event.target.files];
// 		compress.compress(files).then((results: any) => {
// 			this.photo = results[0].photo;
// 			let toUpload = new AssetToUpload;
// 			toUpload.file = this.photo;
// 		});
// 	}

// 	share(caption: any) {
// 		let toUpload = new AssetToUpload();
// 		toUpload.file = new File([this.photo?.data], this.photo?.name);

// 		this.prvHome.upload(toUpload).subscribe({
// 			next: (data: any) => {
// 				this.posting = {
// 					memId: {
// 						id: this.profil.id
// 					},
// 					caption: caption,
// 					imgUrl: data.imgUrl
// 				};
// 				// this.posting.imgUrl = data.imgUrl;

// 				console.log('end upload ' + this.posting?.imgUrl);

// 				if (this.posting.imgUrl) {
// 					this.prvHome.share(this.posting).subscribe({
// 						next: (data: any) => console.log('upload success')

// 					});
// 				}
// 				// this.dialogRef.close(true)
// 			},
// 			error: e => console.error(e)
// 		});
// 	}

// 	update(caption: any) {
// 		let toUpload = new AssetToUpload();
// 		toUpload.file = new File([this.photo?.data], this.photo?.name);
// 		console.log('photoname = ' + this.photo?.name)
// 		if (this.photo?.name === undefined) {
// 			this.prvHome.oriDataById(this.postId).subscribe({
// 				next: (ori: any) => {
// 					this.posting = {
// 						id: this.postId,
// 						memId: {
// 							id: this.profil.id
// 						},
// 						caption: caption,
// 						imgUrl: ori.imgUrl
// 					};
// 					console.log('oriname = ' + ori.name)
// 					this.prvHome.update(this.posting).subscribe({
// 						next: (data: any) => console.log('upload success')

// 					});
// 					// this.dialogRef.close(true)
// 				}
// 			})
// 		} else {
// 			this.prvHome.upload(toUpload).subscribe({
// 				next: (data: any) => {
// 					this.posting = {
// 						id: this.postId,
// 						caption: caption,
// 						imgUrl: data.imgUrl
// 					};
// 					this.prvHome.update(this.posting).subscribe({
// 						next: (data: any) => console.log('upload success')
// 					});
// 					// this.dialogRef.close(true)
// 				}
// 			})
// 		}
// 	}

// 	// this.prvHome.upload(toUpload).subscribe({
// 	// 	next: (data: any) => {
// 	// 		this.posting = {
// 	// 			id: this.postId,
// 	// 			caption: caption,
// 	// 			imgUrl: data.imgUrl
// 	// 		};
// 	// 		// this.posting.imgUrl = data.imgUrl;

// 	// 		console.log('end upload ' + this.posting?.imgUrl);

// 	// 		// if (this.posting.caption) {
// 	// 		// 	this.prvHome.update(this.posting).subscribe({
// 	// 		// 		next: (data: any) => console.log('upload success')

// 	// 		// 	});
// 	// 		// }
// 	// 	},
// 	// 	error: e => console.error(e)
// 	// });

// 	// static show(dialog: MatDialog, post: Posting | null) {
// 	// 	const localDialogRef = dialog.open<CmpCreate, any, boolean>(CmpCreate, {
// 	// 		maxWidth: '600px',
// 	// 		maxHeight: '400px',
// 	// 		data: { post }
// 	// 	}
// 	// 	);
// 	// 	return localDialogRef.afterClosed()
// 	// }

// 	// cancel() {
// 	// 	this.dialogRef.close(false)
// 	// }

// 	// onFileSelected(evt: any) {
// 	// 	const options = {
// 	// 		targetSize: 0.5,
// 	// 		quality: 0.75,
// 	// 		maxWidth: 1024,
// 	// 		maxHeight: 1024
// 	// 	};
// 	// 	const compress = new Compress(options);
// 	// 	const file = evt.target.files[0];
// 	// 	if (file) {
// 	// 		const reader = new FileReader();
// 	// 		reader.onload = (e: any) => {
// 	// 			this.selectedImage = e.target.result;
// 	// 		};
// 	// 		reader.readAsDataURL(file);
// 	// 	}

// 	// 	compress.compress(file).then((conversions: any) => {
// 	// 		this.photo = conversions[0].photo;
// 	// 		const info = conversions[0].info;
// 	// 		const objectUrl = URL.createObjectURL(this.photo.data);
// 	// 		let toUpload = new AssetToUpload;
// 	// 	},
// 	// 		false);
// 	// }

// 	// share(caption: string) {
// 	// 	this.posting = {
// 	// 		memId: {
// 	// 			id: this.profil.id
// 	// 		}
// 	// 	};
// 	// 	this.posting.caption = caption;
// 	// 	this.prvHome.share(this.posting).subscribe({
// 	// 		next: (data: any) => {
// 	// 			this.getProfil()
// 	// 		},
// 	// 		error: e => console.error(e)
// 	// 	})
// 	// 	this.close = true;
// 	// }

// 	// Assuming you have an HTML input element with the id "fileInput"
// 	// const fileInput = document.getElementById('fileInput') as HTMLInputElement;


// 	// 	share(caption: string) {
// 	// let toUpload = new AssetToUpload;
// 	// toUpload.file = new File ([file])



// 	// formData.append('image', this.selectedImage);
// 	// formData.append('memId', this.profil?.id.toString())
// 	// formData.append('caption', caption)
// 	// 	this.prvHome.share(formData).subscribe({
// 	// 		next: (data: any) => data
// 	// 	})
// 	// }

// }

