import { Component, OnInit } from '@angular/core';
import { Member, Posting, PrvHome } from '../home/PrvHome';
import { Router } from '@angular/router';
import { AssetToUpload } from '../createPost/AssetToUpload';

declare var Compress: any;

@Component({
	selector: 'cmp-my-prof',
	templateUrl: './CmpMyProf.html',
	styleUrls: ['./CmpMyProf.css']
})
export class CmpMyProf implements OnInit {

	profil!: Member;
	post!: Posting[];
	editUsername = false;
	editPwd = false;
	editBio = false;
	selectedImage: string | ArrayBuffer | null = null;
	photo: any;
	editPhoto = false;



	constructor(
		private prvHome: PrvHome,
		private router: Router
	) { }

	ngOnInit(): void {
		this.getMyProfil()
	}

	getMyProfil() {
		this.prvHome.getMyProfil().subscribe(
			(data: any) => {
				this.profil = data;
				if (this.profil.id) {
					this.prvHome.getMemPost(this.profil!.id).subscribe({
						next: (data: any) => {
							this.post = data;
						},
						error: e => console.error(e)
					});
				}
			},
			(error: any) => {
				if (error.status === 500) {
					console.error('error status 500')
				}
				console.error('error is ' + error);
			}
		)
	}

	changePwd(pwd: any) {
		console.log('editpwd' + pwd)
		this.prvHome.changePwd(pwd).subscribe(
		)
	}

	changeUsername(username: any) {
		console.log('change username to ' + username)
		this.prvHome.changeUsername(username).subscribe({
			next: (data: any) => {
				this.getMyProfil()
			}
		}
		)
	}

	changeBio(bio: any) {
		console.log('change username to ' + bio)
		this.prvHome.changeBio(bio).subscribe({
			next: (data: any) => {
				this.getMyProfil()
			}
		}
		)
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
			// let toUpload = new AssetToUpload;
			// toUpload.file = this.photo;
			// change photo profile
			let toUpload = new AssetToUpload();
		toUpload.file = new File([this.photo?.data], this.photo?.name);
			this.prvHome.uploadPhotoProfile(toUpload).subscribe({
				next: (data: any) => {
					let url = data.imgUrl
					if (url) {
						this.prvHome.addPhotoProfile(url).subscribe({
							next: (data: any) => {
								console.log('upload success')
							}
						});
					}
				},
				error: e => console.error(e)
			});
		});
	}

	


}
