import { ChangeDetectionStrategy, ChangeDetectorRef, Component, ElementRef, HostListener, Injectable, OnChanges, OnDestroy, OnInit, Renderer2, SimpleChanges, ViewChild, ɵdetectChanges } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Member, Posting, PrvHome } from './PrvHome';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { AssetToUpload } from '../createPost/AssetToUpload';

declare var Compress: any;

@Component({
	selector: 'cmp-home',
	templateUrl: './CmpHome.html',
	styleUrls: ['./CmpHome.css'],
	// changeDetection: ChangeDetectionStrategy.OnPush,

})
export class CmpHome implements OnInit, OnDestroy {

	lstPosting: Posting[] = [];
	loadingData = false;
	// lstMember?: Member[];
	profil!: Member;
	storys!: Posting[];
	// lstLike: Like[] = [];
	start = 0;
	max = 10;
	// liked = false;
	// saved = false;
	// panelOpenState = false;
	unsubscriber = new Subject();
	// showHead = 0;
	selectedImage: string | ArrayBuffer | null = null;
	posting!: Posting;
	photo: any;
	isMenuOpen = false;
	showSidebar = false;







	constructor(
		private prvHome: PrvHome,
		private router: Router,
		// public dialog: MatDialog,
	) {
	}

	ngOnInit(): void {
		// this.datamember();
		this.getProfil();
		this.loadData(this.start, this.max, false);
		this.prvHome.getNeedReload()
			.pipe(takeUntil(this.unsubscriber))
			.subscribe((data: any) => {
				this.loadData(0, 5, true)
			});

	}

	ngOnDestroy(): void {
		this.unsubscriber.next(null);
		this.unsubscriber.complete();
		this.unsubscriber.unsubscribe();
		// you need to unsubscribe to avoid memory leak
	}



	@HostListener("window:scroll", ["$event"])
	onScroll() {
		console.log('scroll')
		let pos = (document.documentElement.scrollTop || document.body.scrollTop) + document.documentElement.offsetHeight;
		let max = document.documentElement.scrollHeight;
		if (pos == max) {
			this.loadMoreData();
		}
	}

	loadMoreData() {
		if (!this.loadingData) {
			this.start += this.max;
			this.loadData(this.start, this.max, false);
		}
	}

	loadData(start: number, max: number, dataAfterCreate: Boolean) {
		this.loadingData = true;
		this.prvHome.search(start, max).subscribe(
			(data: any) => {
				if (dataAfterCreate) {
					this.lstPosting = data
					this.loadingData = false;

				} else {
					this.lstPosting = this.lstPosting.concat(data);
					this.loadingData = false;
					console.log('load Data posting succes')
				}


			},
			(error) => {
				console.error(error);
				this.loadingData = false;
			}
		);
	}

	// loadData(start: number, max: number) {
	// 	this.loadingData = true;
	// 	this.prvHome.search(start, max).subscribe(
	// 		(data: any) => {
	// 			if (!this.lstPosting) {
	// 				this.lstPosting = [];
	// 			}
	// 			this.lstPosting = this.lstPosting.concat(data);
	// 			this.loadingData = false;
	// 			console.log('load Data posting succes')
	// 		},
	// 		// error: (e) => {
	// 		// 	console.error(e);
	// 		// 	this.loadingData = false;
	// 		// }
	// 	);
	// }

	getProfil() {
		this.prvHome.getMyProfil().subscribe(
			(data: any) => {
				this.profil = data;
				// if (this.profil.id != null) {
				// 	this.getLike(this.profil.id)
				// }
			},
			(error: any) => {
				if (error.status === 500) {
					console.error('error status 500')
				}
				console.error('error is ' + error);
			}
		)
	}

	// datamember() {
	// 	this.prvHome.getMember().subscribe({
	// 		next: (data: any) => {
	// 			this.lstMember = data;
	// 		},
	// 		error: e => console.error(e)
	// 	})
	// }

	logout() {
		localStorage.removeItem("token");
		this.router.navigate(['login'])
	}

	delete(id: any) {
		this.prvHome.deletePost(id).subscribe({
			next: (data: any) => {
				this.prvHome.search(0, 10).subscribe(
					(data: any) => {
						this.loadData(0, 10, true)
					}
				);
			},
			error: e => console.error(e)
		})
	}

	// edit(id:any) {
	// 	this.prvHome.findPostById(id).subscribe({
	// 		next:(data:any)=> {
	// 			CmpCreate.show(this.dialog,data).subscribe(result => {
	// 				console.log('dialog result', result)
	// 				if (result === true) {
	// 					this.loadData(0, 5, true)
	// 				}
	// 			})
	// 		}
	// 	})
	// }
	// likePost(id: any) {
	// 	this.prvHome.likePost(id).subscribe({
	// 	})
	// 	this.liked = true;
	// }

	// dislike(id: any) {
	// 	this.prvHome.dislike(id).subscribe({

	// 	})
	// 	this.liked = false;

	// }

	// getLike(id: any) {
	// 	this.prvHome.likeByMemId(id).subscribe({
	// 		next: (data: any) => {
	// 			if (data.length != 0) {
	// 				this.lstLike = data
	// 				console.log("lstLike = " + this.lstLike + "dan" + this.profil.id)
	// 			}
	// 		}
	// 	})
	// }

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

	share(caption: any) {
		console.log('caption is ' + caption)
		let toUpload = new AssetToUpload();
		toUpload.file = new File([this.photo?.data], this.photo?.name);
		if (toUpload.file.name === "undefined") {
			this.posting = {
				memId: {
					id: this.profil.id
				},
				caption: caption,
				imgUrl: null
			};
			this.prvHome.share(this.posting).subscribe({
				next: (data: any) => {
					this.loadData(0, 10, true)
				}
			});
			this.prvHome.needReload(true)
		} else {
			this.prvHome.upload(toUpload).subscribe({
				next: (data: any) => {
					this.posting = {
						memId: {
							id: this.profil.id
						},
						caption: caption,
						imgUrl: data.imgUrl
					};
					// this.posting.imgUrl = data.imgUrl;

					console.log('end upload ' + this.posting?.imgUrl);

					if (this.posting.imgUrl) {
						this.prvHome.share(this.posting).subscribe({
							next: (data: any) => {
								this.loadData(0, 10, true)
							}

						});
					}
					// this.dialogRef.close(true)
					this.prvHome.needReload(true)
				},
				error: e => console.error(e)
			});

		}

	}

}










