import { Component, ErrorHandler, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { empty } from 'rxjs';
import { Member, PrvHome } from 'src/app/home/PrvHome';

@Component({
	selector: 'cmp-edit-mem',
	templateUrl: './CmpEditMem.html',
	styleUrls: ['./CmpEditMem.css']
})
export class CmpEditMem implements OnInit {
	@Input() id!: number;
	dtlMember: Member = {};
	memberForm: FormGroup;
	errorMsg = '';
	showingEdit = false;


	constructor(
		private prvHome: PrvHome,
		private _snackBar: MatSnackBar,
		private fb: FormBuilder
	) {
		this.memberForm = this.fb.group({
			id:[this.id],
			username: ['', [Validators.required]],
			email: [''],
			password: [''],
			role: ['', [Validators.required]],
			bio: [''],
		});
	}

	ngOnInit(): void {
		console.log('detail id = ' + this.id)
		this.getDetailMember()
	}

	getDetailMember() {
		if (this.id != 0) {
			this.prvHome.getMemberById(this.id).subscribe(
				(data: any) => {
					this.dtlMember = data
				}
			)
		}
	}

	save() {
		if (this.memberForm.valid) {

			if (this.id != 0) {
				this.dtlMember = this.memberForm.value
				this.dtlMember.id = this.id
				// const dtlMember = this.memberForm.value;
				this.prvHome.updateDataMember(this.dtlMember).subscribe({
					next: (data: any) => {
						console.log('success update member')
						this.memberForm.value.empty
					}, error: e => console.error(e)
				})

			} else {
				const formData = this.memberForm.value;

				this.prvHome.addMember(formData).subscribe({
					next: (data: any) => {
						console.log('success add member')
					}, error: e => console.error(e)
				})
			}
		} else {
			this.errorMsg = 'Please fill in all required fields and correct any validation errors.'
		}

	}

	deleteMember() {
		this.prvHome.deleteMember(this.id).subscribe(
			(data: any) => {
				console.log('success delete member')
			}
		)
	}
}
