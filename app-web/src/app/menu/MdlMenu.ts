import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RoutHome } from './RoutMenu';
import { CmpMenu } from './CmpMenu';
import { MatIconModule } from '@angular/material/icon';
import { CarouselModule } from '@coreui/angular';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { CmpHome } from '../home/CmpHome';
// import { CmpCreate } from '../createPost/CmpCreate';
import { MatDialogModule } from '@angular/material/dialog';
import { CmpLogin } from '../login/CmpLogin';
import { MatMenuModule } from '@angular/material/menu';
import { CmpExplore } from '../explore/CmpExplore';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CmpSend } from '../messages/snd-msg/CmpSend';
import { CmpMessages } from '../messages/CmpMessages';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { CmpMyProf } from '../myProfil/CmpMyProf';
import { CmpProfil } from '../profil/CmpProfil';
import { CmpStory } from '../story/CmpStory';
import { CmpShowStr } from '../show-story/CmpShowStr';
import { CmpCreateStr } from '../create-myStory/CmpCreateStr';
import { CmpMyNotif } from '../myNotification/CmpMyNotif';
import { MatExpansionModule } from '@angular/material/expansion';
import { CmpMarket } from '../market/CmpMarket';
import { CmpSell } from '../market/sell/CmpSell';
import { CmpBuy } from '../market/buy/CmpBuy';
import { CmpCheckout } from '../market/checkout/CmpCheckout';
import { CmpMyprd } from '../market/myproduct/CmpMyprd';
import { OverlayModule } from '@angular/cdk/overlay';
import { CmpPages } from '../pages/CmpPages';
import { MatTooltipModule } from '@angular/material/tooltip';
import { CmpAdmin } from '../admin/CmpAdmin';
import { MatTableModule } from '@angular/material/table';
import { CmpEditMem } from '../admin/editMember/CmpEditMem';
import { AdminGuard } from '../admin/AdminGuard';









@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		RoutHome,
		MatIconModule,
		CarouselModule,
		MatCardModule,
		MatTabsModule,
		MatDialogModule,
		MatMenuModule,
		InfiniteScrollModule,
		NgxWebstorageModule.forRoot(),
		MatSnackBarModule,
		MatSidenavModule,
		FormsModule,
		MatExpansionModule,
		ReactiveFormsModule,
		OverlayModule,
		MatTooltipModule,
		MatTableModule


	],
	declarations: [CmpMenu,
		CmpHome,
		// CmpCreate,
		CmpLogin,
		CmpMyProf,
		CmpExplore,
		CmpMessages,
		CmpSend,
		CmpProfil,
		CmpStory,
		CmpShowStr,
		CmpCreateStr,
		CmpMyNotif,
		CmpMarket,
		CmpSell,
		CmpBuy,
		CmpCheckout,
		CmpMyprd,
		CmpPages,
		CmpAdmin,
		CmpEditMem
	],
	providers: [DatePipe, AdminGuard]
})
export class MdlMenu { }
