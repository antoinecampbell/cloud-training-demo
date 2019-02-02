import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SharedModule} from "./shared.module";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./login/register.component";
import {UserService} from "./login/user.service";
import {AuthGuard} from "./login/auth-guard.service";
import {AnonymousGuard} from "./login/anonymous-guard.service";
import {NoteComponent} from "./note/note.component";
import {NoteDialogComponent} from "./note/note-dialog.component";
import {NoteDialogPagerComponent} from "./note/note-dialog-pager.component";
import {of} from "rxjs";
import {catchError, flatMap} from "rxjs/operators";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {DefaultHeaderInterceptorService} from "./default-header-interceptor.service";
import {NoteService} from "./note/note.service";

export function appInit(userService: UserService): Function {
  return () =>
    userService.checkToken()
      .pipe(
        flatMap(() => of(true)),
        catchError(() => of(true))
      ).toPromise();
}

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    NoteComponent,
    NoteDialogComponent,
    NoteDialogPagerComponent,
  ],
  imports: [
    SharedModule,
    AppRoutingModule,
  ],
  entryComponents: [
    NoteDialogComponent,
    NoteDialogPagerComponent
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: appInit,
      multi: true,
      deps: [UserService]
    },
    {provide: HTTP_INTERCEPTORS, useClass: DefaultHeaderInterceptorService, multi: true},
    UserService,
    NoteService,
    AuthGuard,
    AnonymousGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
