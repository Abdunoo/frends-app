import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Member, PrvHome } from '../home/PrvHome';
import { Token } from '@angular/compiler';
import { HttpErrorResponse } from '@angular/common/http';
import { PatternValidator } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
	selector: 'cmp-login',
	templateUrl: './CmpLogin.html',
	styleUrls: ['./CmpLogin.css']
})
export class CmpLogin {

	profil!: Member;
	token: any;
	errormsg!: string;



	constructor(
		private router: Router,
		private prvHome: PrvHome,
		private _snackBar: MatSnackBar,

	) { }

	login(username: string, password: string) {
		if (username === '') {
			this.errormsg = 'username cannot null'
		} else if (username.includes(' ')) {
			this.errormsg = 'username cannot have space'
		} else if (password === '') {
			this.errormsg = 'password cannot null'
		} else if (password.includes(' ')) {
			this.errormsg = 'password cannot have space'
		} else {
			this.errormsg = '';
			this.prvHome.login(username, password).subscribe({
				next: (data: any) => {
					this.token = data.token
					const snackBarRef = this._snackBar.open('Login Success', 'Info',
						{
							horizontalPosition: 'center',
							verticalPosition: 'bottom',
							duration: 5000
						});
					if (this.token != null) {
						localStorage.setItem('token', this.token);
						this.router.navigate(['menu/home'])
						// localStorage.setItem('id', this.profil.id.toString());
						// console.log("token member is " + this.profil.token)
						// if (this.profil.token != null) {
						// localStorage.setItem('id', this.profil.id.toString());

					}
				}, error: e => {
					if (e.status == 500 || e.status == 401) {
						this.errormsg = ('incorrect username or pasword')
					} else if (e.status == 0) {
						this.errormsg = ('cannot connect to server')
					} else {
						if (e.text) {
							this.errormsg = e.text();
						} else {
							this.errormsg = e.error;
						}
					}

				}
			})


		}
	}

	reg(username: string, password: string) {
		if (username === '') {
			this.errormsg = 'username cannot null'
		} else if (username.includes(' ')) {
			this.errormsg = 'username cannot have space'
		} else if (password === '') {
			this.errormsg = 'password cannot null'
		} else if (password.includes(' ')) {
			this.errormsg = 'password cannot have space'
		} else {
			this.prvHome.reg(username, password).subscribe({
				next: (response: any) => {
					// this.profil.token = response;
					// this.router.navigate(['login'])
					const snackBarRef = this._snackBar.open('Register Success', 'Info',
						{
							horizontalPosition: 'center',
							verticalPosition: 'bottom',
							duration: 5000
						});
				}, error: e => {
					if (e.status == 500) {
						this.errormsg = ('username alredy exist')
					} else if (e.status == 0) {
						this.errormsg = ('cannot connect to server')
					} else {
						if (e.text) {
							this.errormsg = e.text();
						} else {
							this.errormsg = e.error;
						}
					}
				}
			})

		}
	}

	// login(username: string, password: string) {
	// 	this.prvHome.login(username, password).subscribe(
	// 	  (data: any) => {
	// 		 const dataJsonString = JSON.stringify(data); // Convert data to JSON string
	// 		 localStorage.setItem('dataJson', dataJsonString); // Store the JSON string
	// 		 console.log("Data JSON string:", dataJsonString);

	// 		 // Continue with the rest of your logic
	// 		 if (data && data.token) {
	// 			const tokenObject = { token: data.token };
	// 			localStorage.setItem('token', JSON.stringify(tokenObject));
	// 			console.log("Token member is " + data.token);
	// 			this.router.navigate(['menu/home']);
	// 		 } else {
	// 			console.log('Incorrect username or password!');
	// 		 }
	// 	  },
	// 	  (error: any) => {
	// 		 console.error('Error in login:', error);

	// 		 if (error instanceof HttpErrorResponse) {
	// 			try {
	// 			  const errorResponse = JSON.parse(error.error);
	// 			  console.log('Error response:', errorResponse);
	// 			} catch (parseError) {
	// 			  console.error('Error parsing error response:', parseError);
	// 			  console.log('Raw error response:', error.error);
	// 			}
	// 		 }
	// 	  }
	// 	);
	//  }




}
