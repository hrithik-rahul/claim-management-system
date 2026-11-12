import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Claim, ClaimData, ClaimResponse } from '../services/claimService';
import { Router } from '@angular/router';


@Component({
  selector: 'app-manager-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './manager-dashboard.html',
  styleUrl: './manager-dashboard.css'
})
export class ManagerDashboard implements OnInit {

  claims: ClaimResponse[] = [];
  loading = false;
  message = '';
  managerName: string = '';

  constructor(private claimService: Claim,
     private router: Router,
    private cdr: ChangeDetectorRef) {}

  // Runs when manager dashboard loads
  ngOnInit(): void {
     this.managerName =
    sessionStorage.getItem('fullName') || 'Manager';
    this.loadAllClaims();
    
  }

  // Fetch all active claims
  loadAllClaims(): void {
    this.loading = true;
    this.message = '';

    this.claimService.getAllManagerClaims().subscribe({
      next: (response) => {
        this.claims = response;
        this.loading = false;
        this.cdr.detectChanges();
        
      },
      error: (error) => {
        console.error('Error fetching claims:', error);
        this.message = 'Unable to load claims.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  // Total claims
  get totalClaims(): number {
    return this.claims.length;
  }

  // Pending claims
  get pendingClaims(): number {
    return this.claims.filter(
      claim => claim.status === 'SUBMITTED' || claim.status === 'PENDING'
    ).length;
  }

  // Approved claims
  get approvedClaims(): number {
    return this.claims.filter(
      claim => claim.status === 'APPROVED'
    ).length;
  }

  // Rejected claims
  get rejectedClaims(): number {
    return this.claims.filter(
      claim => claim.status === 'REJECTED'
    ).length;
  }

  // Approve a claim
  approveClaim(id: number): void {
    this.updateClaimStatus(id, 'APPROVED');
  }

  // Reject a claim
  rejectClaim(id: number): void {
    this.updateClaimStatus(id, 'REJECTED');
  }

  // Update claim status through Claim service
  updateClaimStatus(id: number, status: string): void {

    this.claimService.updateClaimStatus(id, status).subscribe({
      next: () => {
        this.message = `Claim ${status.toLowerCase()} successfully.`;
        this.loadAllClaims();
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error updating claim:', error);
        this.message = 'Unable to update claim status.';
        this.cdr.detectChanges();
      }
    });
  }

  logout(): void {
   sessionStorage.removeItem('token');
  sessionStorage.removeItem('fullName');
  sessionStorage.removeItem('role');
  this.router.navigate(['/login']);
}
}