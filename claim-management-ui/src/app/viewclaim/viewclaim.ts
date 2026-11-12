import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Claim, ClaimData } from '../services/claimService';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [CommonModule,FormsModule],
  selector: 'app-viewclaim',
  styleUrl: './viewclaim.css',
  templateUrl: './viewclaim.html',
})


export class Viewclaim implements OnInit {
  public  claims: ClaimData[] = [];
   public loading = false;
  public errorMessage = '';
  public successMessage = '';

  // Edit form properties
 public editingClaimId: number | null = null;
  public editClaimType = '';
 public editDescription = '';
  public isSaving = false;
public  submittedClaims = 0;
public approvedClaims = 0;
public rejectedClaims = 0;
   constructor(private router: Router,
     private cdr: ChangeDetectorRef,
    private claimService :Claim
  ) {}
  ngOnInit(): void {
     this.fetchClaims();
  }
   fetchClaims(): void {
    this.loading = true;
    this.errorMessage = '';

    this.claimService.getMyClaims().subscribe({
      next: (response: ClaimData[]) => {
        this.claims = response;
        this.submittedClaims = this.claims.filter(
  claim => claim.status === 'SUBMITTED'
).length;

this.approvedClaims = this.claims.filter(
  claim => claim.status === 'APPROVED'
).length;

this.rejectedClaims = this.claims.filter(
  claim => claim.status === 'REJECTED'
).length;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Failed to fetch claims:', error);
        this.errorMessage = 'Unable to load your claims. Please try again.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }
 // Check whether a claim can be edited
  canEdit(claim: ClaimData): boolean {
    return claim.status?.toUpperCase() !== 'APPROVED';
  }

  // Open the edit form for a selected claim
  editClaim(claim: ClaimData): void {
    if (!this.canEdit(claim)) {
      return;
    }

    this.editingClaimId = claim.id ||null;
    this.editClaimType = claim.claimType;
    this.editDescription = claim.description || '';

    this.errorMessage = '';
    this.successMessage = '';
  }

  // Cancel editing
  cancelEdit(): void {
    this.editingClaimId = 0;
    this.editClaimType = '';
    this.editDescription = '';
    this.isSaving = false;
  }
   saveClaim(): void {
    if (this.editingClaimId === null) {
      return;
    }

    if (!this.editClaimType || !this.editDescription.trim()) {
      this.errorMessage = 'Please fill in all fields.';
      return;
    }
    if (this.editingClaimId === null) {
     return ;
  }

const claimId = this.editingClaimId;


    const updatedClaim = {
      claimType: this.editClaimType,
      description: this.editDescription.trim()
    };

    this.isSaving = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.claimService.updateClaim(claimId, updatedClaim).subscribe({
      next: () => {
        this.successMessage = 'Claim updated successfully!';
        this.cancelEdit();
        this.fetchClaims();
      },
      error: (error) => {
        console.error('Failed to update claim:', error);
        this.errorMessage =
          error.error?.message || 'Unable to update claim. Please try again.';
        this.isSaving = false;
        this.cdr.detectChanges();
      }
    });
  }
   // Return to dashboard
  goToDashboard(): void {
    this.router.navigate(['/home']);
  }

}
