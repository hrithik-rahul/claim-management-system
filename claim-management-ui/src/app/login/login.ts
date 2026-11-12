import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule, } from '@angular/forms';
import { Auth, LoginRequest } from '../services/auth';
import { CommonModule } from '@angular/common';
import { Router,RouterLink } from '@angular/router';


@Component({
  standalone: true,
  imports: [FormsModule,CommonModule,RouterLink ],
  selector: 'app-login',
  styleUrl: './login.css',
  templateUrl: './login.html',
})
export class Login {
  constructor(private auth: Auth,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  username: string = '';
  password: string = '';
message: string = '';

  onLogin() {
     const login: LoginRequest = {
      username: this.username,
      password: this.password
    };

    this.auth.login(login).subscribe({
  next: (response) => {
    sessionStorage.setItem('token', response.token);
     sessionStorage.setItem('fullName', response.fullName || '');
  sessionStorage.setItem('username', response.username || '');
  sessionStorage.setItem('tokenType', response.tokenType);
  sessionStorage.setItem('role', response.role);
   if (response.role === 'ROLE_MANAGER') {
    this.router.navigateByUrl('/manager-dashboard');
  } else {
    this.router.navigateByUrl('/home');
  }
        this.message = 'Login successful!';
         this.cdr.detectChanges();
  },
  error: (error) => {
    console.error('Login failed:', error);
        this.message = 'Login failed. Check your credentials.';
         this.cdr.detectChanges();
  }
});
  }
}
