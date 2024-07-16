import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AssetToUpload } from '../createPost/AssetToUpload';
import { BehaviorSubject, Subject, concatMap, startWith } from 'rxjs';
import { environment } from 'src/environments/environment';


export interface Posting {
	id?: number;
	imgUrl: any;
	caption: string;
	memId?: { id?: number, username?: string, imgUrl?: string }

}
export interface Member {
	id?: number;
	username?: string;
	email?: string;
	password?: string;
	token?: string;
	bio?: string;
	imgUrl?: string;
	role?: string;
}


export interface Chats {
	id: number;
	senderId?: { id?: number, username?: string };
	getterId?: { id?: number, username?: string };
	messages?: string;
}

export interface Story {
	id?: number;
	imgUrl: string;
	startShow?: Date;
	endShow?: Date;
	memId?: { id?: number, username?: string }
}

export interface Notif {
	id?: number;
	notif?: string;
	memId?: Member
	time?: Date;
}

// export interface Like {
// 	id?: number;
// 	postId?: Posting;
// 	LikedBy?: Member;
// }

export interface Product {
	id: number;
	name?: string | null
	imgUrl?: string;
	price?: any;
	sellerId?: Member;
	description?: string;
	stock: number;
	sold: number;
}

export interface Checkout {
	id?: number;
	firstName?: any
	lastName?: any;
	email?: any;
	streetAddress?: any;
	province?: any;
	city?: any;
	posCode?: any;
	phone?: any;
	productId?: Product;
	buyerId?: Member;
}



@Injectable({ providedIn: 'root' })
export class PrvHome {
	// private postObservable = new BehaviorSubject<Posting[]>([])
	// private storyObservable = new BehaviorSubject<Story[]>([])
	// private productObservable = new BehaviorSubject<Product[]>([])

	private chatObservable = new BehaviorSubject<Chats[]>([])
	private needReload$ = new Subject<boolean>

	API = environment.API
	webSocket = environment.webSocket

	constructor(private http: HttpClient) { }



	getNeedReload() {
		return this.needReload$
	}
	needReload(val: boolean) {
		this.needReload$.next((val))
	}
	setMessage(val: Chats[]) {
		this.chatObservable.next((val));
	}
	getMessage() {
		return this.chatObservable;
	}


	//***************************//******************************//

	// posting
	allPost() {
		return this.http.get<Posting>(this.API + `post/listall`) // mengakses API
	}
	search(start: number, max: number) {
		return this.http.get<Posting>(this.API + `post/list?start=` + start + '&max=' + max) // mengakses API
	}

	update(payload: any) {
		return this.http.post<Posting>(this.API + `post/edit`, payload) // mengakses API
	}

	findPostById(id: any) {
		return this.http.get<any>(this.API + `post/find/` + id) // mengakses API
	}

	oriDataById(id: any) {
		return this.http.get<any>(this.API + `post/ori/` + id) // mengakses API
	}

	upload(asset: AssetToUpload) {
		let form = new FormData()
		form.append('file', asset.file, asset.file.name);
		return this.http.post<any>(this.API + `post/upload`, form) // mengakses API
	}

	share(payload: any) {
		return this.http.post<Posting>(this.API + `post/share`, payload) // mengakses API
	}

	getMemPost(profilId: number) {
		return this.http.get<Posting[]>(this.API + `post/list/` + profilId) // mengakses API
	}

	deletePost(id: number) {
		return this.http.delete<any>(this.API + `post/delete/` + id) // mengakses API
	}

	// member
	getMember() {
		return this.http.get<Member[]>(this.API + `member/list`) // mengakses API
	}

	login(username: string, password: string) {
		let payload = { username, password }
		return this.http.post<Member>(this.API + `member/login`, payload) // mengakses API
	}

	getMyProfil() {
		return this.http.get<Member>(this.API + `member/profil`) // mengakses API
	}

	reg(username: string, password: string) {
		let payload = { username, password }
		return this.http.post<Member>(this.API + `member/reg`, payload) // mengakses API
	}

	updateDataMember(member: Member) {
		return this.http.post<Member>(this.API + `member/update`, member) // mengakses API
	}

	getOtherMem(username: string) {
		return this.http.get<Member[]>(this.API + `member/except/` + username) // mengakses API
	}

	memDetail(username: string) {
		return this.http.get<Member>(this.API + `member/username/` + username) // mengakses API
	}

	searchMem(searchQuery: string) {
		return this.http.get<Member[]>(this.API + `member/search/` + searchQuery) // mengakses API
	}

	changePwd(pwd: any) {
		return this.http.get<Member>(this.API + `member/changePwd?pwd=` + pwd) // mengakses API
	}

	changeUsername(username: any) {
		return this.http.get<Member>(this.API + `member/changeUsrName?usrnm=` + username) // mengakses API
	}

	changeBio(bio: any) {
		return this.http.get<Member>(this.API + `member/changeBio?bio=` + bio) // mengakses API
	}

	getMemberById(id: number) {
		return this.http.get<Member>(this.API + `member/id/` + id) // mengakses API
	}

	deleteMember(id: number) {
		return this.http.delete<any>(this.API + `member/delete/` + id) // mengakses API
	}

	addMember(member: Member) {
		return this.http.post<Member>(this.API + `member/add`, member) // mengakses API
	}

	// story
	uploadstory(asset: AssetToUpload) {
		let form = new FormData()
		form.append('file', asset.file, asset.file.name);
		return this.http.post<any>(this.API + `story/upload`, form) // mengakses API
	}

	shareStory(payload: any) {
		return this.http.post<Story>(this.API + `story/share`, payload) // mengakses API
	}

	getAllStory() {
		return this.http.get<Story>(this.API + `story/allStory`) // mengakses API
	}

	getMyStory() {
		return this.http.get<Story>(this.API + `story/myStory`) // mengakses API
	}

	selectedStory(username: string) {
		return this.http.get<Story>(this.API + `story/selected?username=` + username) // mengakses API
	}

	deleteStory(username: any) {
		return this.http.delete<any>(this.API + `story/delete?username=` + username) // mengakses API
	}

	// market
	insertProduct(Product: Product) {
		return this.http.post<Product>(this.API + `market/sell`, Product) // mengakses API
	}

	uploadProduct(asset: AssetToUpload) {
		let form = new FormData()
		form.append('file', asset.file, asset.file.name);
		return this.http.post<any>(this.API + `market/upload`, form) // mengakses API
	}

	allProduct() {
		return this.http.get<Product[]>(this.API + `market/allProduct`) // mengakses API
	}

	myProduct() {
		return this.http.get<Product[]>(this.API + `market/myproduct`) // mengakses API
	}

	productById(id: any) {
		return this.http.get<Product>(this.API + `market/product/` + id) // mengakses API
	}

	deletePrd(id: any) {
		return this.http.delete<any>(this.API + `market/delete/` + id) // mengakses API
	}

	// message
	sendMessage(username: string, messageToSend: string) {
		return this.http.get(this.API + 'msg/private/' + username + '/' + messageToSend)
	}

	getMyMessage(username: string) {
		return this.http.get<Chats[]>(this.API + 'msg/private/myMessage/' + username)
	}

	// checkout
	insertCheckout(checkout: Checkout) {
		return this.http.post<Checkout>(this.API + `checkout/insert`, checkout) // mengakses API
	}

	// notif
	getNotif() {
		return this.http.get<Notif[]>(this.API + 'notif/list');
	}

	// pages
	uploadPhotoProfile(asset: AssetToUpload) {
		let form = new FormData()
		form.append('file', asset.file, asset.file.name);
		return this.http.post<any>(this.API + `member/upload`, form) // mengakses API
	}

	addPhotoProfile(imgUrl: string) {
		return this.http.get<Member>(this.API + `member/changePhoto?photo=` + imgUrl) // mengakses API

	}

	// saveNotif(message: any) {
	// 	return this.http.get<any>('http://localhost:8080/msg/private/' + message);
	// }

	// likePost(id: number) {
	// 	return this.http.get<any>(`http://localhost:8080/like/like/` + id) // mengakses API
	// }

	// dislike(id: number) {
	// 	return this.http.get<any>(`http://localhost:8080/like/dislike/` + id) // mengakses API
	// }

	// likeByMemId(id: number) {
	// 	return this.http.get<Like[]>(`http://localhost:8080/like/allMem/` + id) // mengakses API
	// }

	// setPost(val: Posting[]) {
	// 	this.postObservable.next((val))
	// }

	// getPost() {
	// 	return this.postObservable;
	// }
	// setStory(val: Story[]) {
	// 	this.storyObservable.next((val))
	// }

	// getStory() {
	// 	return this.storyObservable;
	// }

	// setProduct(val: Product[]) {
	// 	this.productObservable.next((val))
	// }

	// getProduct() {
	// 	return this.productObservable;
	// }


}

