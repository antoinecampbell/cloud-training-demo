import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AnonymousGuard} from "./login/anonymous-guard.service";
import {RegisterComponent} from "./login/register.component";
import {LoginComponent} from "./login/login.component";
import {NoteComponent} from "./note/note.component";
import {AuthGuard} from "./login/auth-guard.service";

const routes: Routes = [
  {
    path: 'login',
    pathMatch: 'full',
    component: LoginComponent,
    canActivate: [AnonymousGuard]
  },
  {
    path: 'register',
    pathMatch: 'full',
    component: RegisterComponent,
    canActivate: [AnonymousGuard]
  },
  {
    path: '',
    pathMatch: 'full',
    component: NoteComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
