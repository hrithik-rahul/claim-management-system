import { HttpClient } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { Observable } from 'rxjs';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  tokenType: string;
  fullName: string;
  username: string;
  role: string;
}
export interface RegisterRequest {
  fullName: string;
  username: string;
  email: string;
  phoneNumber?: string | null;
  password: string;
}
@Injectable({
  providedIn: 'root'
})

export class Auth {
      private baseUrl = '/auth';

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.baseUrl}/login`,
      request
    );
  }
   public register(request: RegisterRequest): Observable<string> {
    return this.http.post(
      `${this.baseUrl}/register`,
      request,
      { responseType: 'text' }
    );
  }
}
