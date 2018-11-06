import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TempComponent} from "./temp/temp.component";

const routes: Routes = [
  {
    path: 'temp',
    component: TempComponent,
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
