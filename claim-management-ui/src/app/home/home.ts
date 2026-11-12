import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Claim, ClaimData } from '../services/claimService';
import { CommonModule } from '@angular/common';

@Component({
  imports: [CommonModule],
  selector: 'app-home',
   standalone: true,
  styleUrl: './home.css',
  templateUrl: './home.html',
})
export class Home implements OnInit{
  public displayName:string='';
   public loading:boolean=true;
   totalClaims = 0;
  submittedClaims = 0;
  approvedClaims = 0;
  public errorMessage:string ='';
  public rejectedClaims =0;
  public  claims: ClaimData[] = [];
  public recentClaims: ClaimData[] = [];

  constructor(private router: Router,
     private cdr: ChangeDetectorRef,
    private claimService :Claim
  ) {}

    ngOnInit(): void {
    this.displayName =
      sessionStorage.getItem('fullName') ||
      sessionStorage.getItem('username') ||
      'User';
      this.fetchClaims();
  }
  fetchClaims(): void {
    this.loading = true;
    this.claimService. getMyClaims().subscribe({
      next: (response) => {
        console.log('Claims received:', response);

        this.claims = response;
        this.totalClaims = this.claims.length;
          this.submittedClaims = this.claims.filter(
        claim => claim.status === 'SUBMITTED'
      ).length;

      this.approvedClaims = this.claims.filter(
        claim => claim.status === 'APPROVED'
      ).length;
       this.rejectedClaims = response.filter(
          claim => claim.status === 'REJECTED'
        ).length;

        // Display the latest 3 claims first
        this.recentClaims = [...response]
          .sort((a, b) => {
            const dateA = a.submittedAt
              ? new Date(a.submittedAt).getTime()
              : 0;

            const dateB = b.submittedAt
              ? new Date(b.submittedAt).getTime()
              : 0;

            return dateB - dateA;
          })
          .slice(0, 2);


      console.log('Total claims value:',response);
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

  createClaim(): void {
    this.router.navigate(['/createclaim']);
  }

  viewClaims(): void {
    this.router.navigate(['/viewclaim']);
  }

  logout(): void {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('jwtToken');
    sessionStorage.removeItem('tokenType');
    sessionStorage.removeItem('fullName');
    sessionStorage.removeItem('username');

    this.router.navigate(['/login']);
  }
}
