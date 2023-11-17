import { Component } from '@angular/core';
import { Member, Posting, PrvHome, Story } from '../home/PrvHome';
import { Router } from '@angular/router';
import { AssetToUpload } from '../createPost/AssetToUpload';
import { DatePipe } from '@angular/common';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';

declare var Compress: any;

@Component({
	selector: 'cmp-my-story',
	templateUrl: './CmpCreateStr.html',
	styleUrls: ['./CmpCreateStr.css']
})
export class CmpCreateStr {


	selectedImage: string | ArrayBuffer | null = null;
	profil!: Member
	story!: Story;
	close = false
	photo: any;
	imgUrl?: string
	constructor(
		private prvHome: PrvHome,
		private router: Router,
		public datepipe: DatePipe,
		private dialogRef: MatDialogRef<CmpCreateStr, boolean>

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

	share() {
		this.dialogRef.close(true)
		let p = new Date

		let currentDateTime: Date = p;
		let endDateTime: Date = new Date(p);
		endDateTime.setDate(endDateTime.getDate() + 1);
		console.log('start time = ' + currentDateTime + ', end time = ' + endDateTime)


		let toUpload = new AssetToUpload();
		toUpload.file = new File([this.photo?.data], this.photo?.name);

		this.prvHome.uploadstory(toUpload).subscribe({
			next: (data: any) => {
				let url = data.imgUrl
				if (url) {

					this.story = {
						id: 0,
						imgUrl: url,
						memId: {
							id: this.profil.id,
							username: ''
						},
						startShow: currentDateTime,
						endShow: endDateTime
					}
					this.prvHome.shareStory(this.story).subscribe({
						next: (data: any) => {
							// this.prvHome.getMyStory().subscribe(
							// 	(data: any) => {
							// 		this.prvHome.setStory(data)
							// 	},
							// 	(error: any) => {
							// 		if (error.status === 500) {
							// 			console.error('error status 500')
							// 		}
							// 		console.error('error is ' + error);
							// 	}
							// )
							console.log('upload success')

						}

					});
				}
			},
			error: e => console.error(e)
		});
	}

	static show(dialog: MatDialog) {
		const localDialogRef = dialog.open<CmpCreateStr, any, boolean>(CmpCreateStr, {
			width: '150px',
			height: '100px'
		});
		return localDialogRef.afterClosed()
	}

	cancel() {
		this.dialogRef.close(false)
	}

}
