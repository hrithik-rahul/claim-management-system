import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Login } from './login/login';
import { Register } from './register/register';
import { Createclaim } from './createclaim/createclaim';
import { Viewclaim } from './viewclaim/viewclaim';
import { ManagerDashboard } from './manager-dashboard/manager-dashboard';
export const routes: Routes = [
     { path: 'login', component: Login },
  { path: 'home', component: Home },
  { path: 'register', component: Register },
  { path: 'viewclaim', component: Viewclaim },
  { path: 'manager-dashboard', component: ManagerDashboard },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'createclaim', component: Createclaim }

];
