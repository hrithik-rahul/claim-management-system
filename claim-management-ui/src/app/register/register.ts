import { FormsModule, } from '@angular/forms';
import { Auth, LoginRequest } from '../services/auth';
import { CommonModule } from '@angular/common';
import { Router,RouterLink } from '@angular/router';
import { Component } from '@angular/core';

@Component({
  imports: [FormsModule,CommonModule,RouterLink ],
  selector: 'app-register',
  styleUrl: './register.css',
  templateUrl: './register.html',
})
export class Register {
   constructor(private router: Router,
    private auth:Auth
   ) {}
  public fullName: string = '';
public username: string = '';
public email: string = '';
public phoneNumber: string = '';
public password: string = '';
public confirmPassword: string = '';
public message: string = '';

 public onRegister(): void {
      if (this.password !== this.confirmPassword) {
    this.message = 'Passwords do not match.';
    return;
  }

  const registerRequest = {
    fullName: this.fullName,
    username: this.username,
    email: this.email,
    phoneNumber: this.phoneNumber || null,
    password: this.password
  };

  this.auth.register(registerRequest).subscribe({
    next: (response) => {
      this.message = 'Registration successful! Please log in.';
      this.router.navigateByUrl('/login');
    },
    error: (error) => {
      console.error('Registration failed:', error);
      this.message =
        error.error || 'Registration failed. Please try again.';
    }
  });
  }
}
