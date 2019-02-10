import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {UserService} from "./login/user.service";

@Injectable()
export class DefaultHeaderInterceptorService implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Add default headers
    const requestWithHeaders = req.clone({
      headers: DefaultHeaderInterceptorService.setHeaders(req, req.headers)
    });
    return next.handle(requestWithHeaders);
  }

  private static setHeaders(req: HttpRequest<any>, headers: HttpHeaders): HttpHeaders {
    headers = headers.set('X-Requested-With', 'XMLHttpRequest');
    const token = sessionStorage.getItem(UserService.STORAGE_KEY_TOKEN);
    if (req.url.indexOf('/oauth/') == -1 && token && !headers.get('Authorization')) {
      headers = headers.set('Authorization', `Bearer ${token}`)
    }
    return headers;
  }
}
