import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {EMPTY, Observable} from "rxjs";
import {tap} from "rxjs/operators";

export interface IUserService {
  login(username: string, password: string): Observable<any>;

  logout(): Observable<any>;

  checkToken(): Observable<any>;

  refreshToken(tokenResponse: any): Observable<any>;

  createUser(user: any): Observable<any>;
}

@Injectable()
export class UserService implements IUserService {

  private readonly CLIENT_ID = 'clientApp';
  private readonly CLIENT_SECRET = 'secret';
  static readonly STORAGE_KEY_TOKEN = 'STORAGE_KEY_TOKEN';

  user: any;
  tokenResponse: any;

  constructor(private httpClient: HttpClient) {
  }

  login(username: string, password: string): Observable<any> {
    const headers = this.getClientAuthHeader();
    const params = new HttpParams()
      .append('grant_type', 'password')
      .append('username', username)
      .append('password', password);
    return this.httpClient.post('/user-service/oauth/token', params, {
      headers: headers
    }).pipe(
      tap(this.handleTokenSuccess)
    );
  }

  logout(): Observable<any> {
    delete this.user;
    sessionStorage.removeItem(UserService.STORAGE_KEY_TOKEN);
    return EMPTY;
  }

  checkToken(): Observable<any> {
    const token = sessionStorage.getItem(UserService.STORAGE_KEY_TOKEN);
    const params = new HttpParams().append('token', token);
    return this.httpClient.post('/user-service/oauth/check_token', params)
      .pipe(
        tap(user => this.user = user)
      );
  }

  refreshToken(tokenResponse: any): Observable<any> {
    const headers = this.getClientAuthHeader();
    const params = new HttpParams()
      .append('grant_type', 'refresh_token')
      .append('refresh_token', tokenResponse['refresh_token']);
    return this.httpClient.post('/user-service/oauth/token', params, {
      headers: headers
    }).pipe(
      tap(this.handleTokenSuccess)
    );
  }

  createUser(user: any): Observable<any> {
    return this.httpClient.post('/api/users', user);
  }

  handleTokenSuccess = (tokenResponse: any) => {
    this.tokenResponse = tokenResponse;
    this.user = UserService.getTokenPayload(tokenResponse['access_token']);
    sessionStorage.setItem(UserService.STORAGE_KEY_TOKEN, tokenResponse['access_token']);
  };

  private getClientAuthHeader() {
    const auth = btoa(`${this.CLIENT_ID}:${this.CLIENT_SECRET}`);
    return new HttpHeaders().append('Authorization', `Basic ${auth}`)
  }

  private static getTokenPayload(accessToken: string): any {
    try {
      return JSON.parse(atob(accessToken.split('.')[1]));
    } catch (e) {
      console.error(e);
      return {};
    }
  }
}
