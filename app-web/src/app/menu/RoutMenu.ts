import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CmpMenu } from './CmpMenu';
import { CmpHome } from '../home/CmpHome';
import { CmpLogin } from '../login/CmpLogin';
import { CmpMyProf } from '../myProfil/CmpMyProf';
import { CmpExplore } from '../explore/CmpExplore';
import { CmpSend } from '../messages/snd-msg/CmpSend';
import { CmpMessages } from '../messages/CmpMessages';
import { CmpProfil } from '../profil/CmpProfil';
import { CmpShowStr } from '../show-story/CmpShowStr';
import { CmpStory } from '../story/CmpStory';
import { CmpMyNotif } from '../myNotification/CmpMyNotif';
import { CmpMarket } from '../market/CmpMarket';
import { CmpSell } from '../market/sell/CmpSell';
import { CmpBuy } from '../market/buy/CmpBuy';
import { CmpCheckout } from '../market/checkout/CmpCheckout';
import { CmpMyprd } from '../market/myproduct/CmpMyprd';
import { CmpPages } from '../pages/CmpPages';
import { CmpAdmin } from '../admin/CmpAdmin';
import { AdminGuard } from '../admin/AdminGuard';



@NgModule({
	imports: [
		RouterModule.forChild([
			{ path: 'login', component: CmpLogin },
			{
				path: 'menu', component: CmpMenu,
				children: [
					{ path: 'home', component: CmpHome },
					{ path: 'market', component: CmpMarket },
					{ path: 'market/myproduct/list', component: CmpMyprd },
					{ path: 'market/:id', component: CmpBuy },
					{ path: 'checkout/:id', component: CmpCheckout },
					{ path: 'myProfil', component: CmpMyProf },
					{ path: 'explore', component: CmpExplore },
					{ path: 'notif', component: CmpMyNotif },
					{ path: 'prf/:username', component: CmpProfil },
					{ path: 'messages', component: CmpMessages },
					{ path: 'messages/:username', component: CmpSend },
					{ path: 'pages', component: CmpPages },
					{ path: 'members', component: CmpAdmin, canActivate: [AdminGuard] },
					// {
					// 	path: 'messages', component: CmpMessages,
					// 	children: [
					// 		{ path: ':username', component: CmpSend }
					// 	]
					// },

				]
			},
			{
				path: 'story/:username', component: CmpShowStr,
			}
		])
	],
	exports: [RouterModule],

})
export class RoutHome { }

