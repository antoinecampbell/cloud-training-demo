import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {UserService} from "./login/user.service";

@Injectable()
export class DefaultHeaderInterceptorService implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Add default headers
    const requestWithHeaders = req.clone({
      headers: DefaultHeaderInterceptorService.setHeaders(req.headers)
    });
    return next.handle(requestWithHeaders);
  }

  private static setHeaders(headers: HttpHeaders): HttpHeaders {
    headers = headers.set('X-Requested-With', 'XMLHttpRequest');
    const token = sessionStorage.getItem(UserService.STORAGE_KEY_TOKEN);
    if (token && !headers.get('Authorization')) {
      headers = headers.set('Authorization', `Bearer ${token}`)
    }
    return headers;
  }
}
