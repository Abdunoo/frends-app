import {Injectable} from '@angular/core';
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { delay } from 'rxjs/operators';
// import { PageUtil } from './PageUtil';

@Injectable()
export class DefaultHttpInterceptor implements HttpInterceptor {
	intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		const token = localStorage.getItem("token");
		if (req.body instanceof FormData) {
			const authReq = req.clone({
				withCredentials: true,
				setHeaders: {
					'token': token + ''
				}
			});
			return next.handle(authReq);
		} else {
			const authReq = req.clone({
				withCredentials: true,
				setHeaders: {
					'Content-Type': 'application/json',
					'token': token + ''
				}
			});
			return next.handle(authReq);//.pipe(delay(2000));
		}
	}
}