import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Service } from '@angular/core';
export interface Claim {
  id: number;
  status: string;
  // Add other properties after confirming your backend response.
}
export interface ClaimData {
  id?: number;
  claimType: string;
  description: string;
  status?: string;
  submittedAt?: string;
}

export interface ClaimResponse {
  id: number;
  claimType: string;
  description: string;
  status: string;
  submittedAt: string;
}

@Injectable({
  providedIn: 'root'
})

export class Claim {
      private baseUrl = '/api/claims';
      private managerBaseUrl = '/api/manager';

  constructor(private http: HttpClient) {}

  getAllClaims(): Observable<Claim[]> {
    return this.http.get<Claim[]>(
      `${this.baseUrl}/fetchClaim`
    );
  }
    // Create a claim
 createClaim(claimData: ClaimData): Observable<ClaimData> {
  return this.http.post<ClaimData>(
    `${this.baseUrl}/createClaim`,
    claimData
  );
}

    // Get claim by ID
  getClaimById(id: number): Observable<ClaimData> {
    return this.http.get<ClaimData>(
      `${this.baseUrl}/fetchClaimById/${id}`
    );
  }

  // Get claims by customer ID
  getMyClaims(): Observable<ClaimData[]> {
    return this.http.get<ClaimData[]>(
      `${this.baseUrl}/myClaims`
    );
  }
   // Update a claim
  updateClaim(
    id: number,
    claimData: ClaimData
  ): Observable<ClaimData> {
    return this.http.put<ClaimData>(
      `${this.baseUrl}/updateClaimById/${id}`,
      claimData
    );
  }

  // Delete a claim
  deleteClaim(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.baseUrl}/deleteClaim/${id}`
    );
  }
  // Manager: Get all active claims
getAllManagerClaims(): Observable<ClaimResponse[]> {
  return this.http.get<ClaimResponse[]>(
    `${this.managerBaseUrl}/claims`
  );
}

// Manager: Update claim status
updateClaimStatus(id: number, status: string): Observable<ClaimData> {
  return this.http.put<ClaimData>(
    `${this.managerBaseUrl}/claims/status/${id}`,
    { status: status }
  );
}
}
