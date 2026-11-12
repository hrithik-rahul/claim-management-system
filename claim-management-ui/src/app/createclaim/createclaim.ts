import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Claim } from '../services/claimService';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { timeout } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  imports: [CommonModule, FormsModule],
  selector: 'app-createclaim',
  standalone: true,
  styleUrl: './createclaim.css',
  templateUrl: './createclaim.html',
})
export class Createclaim implements OnInit{

  claimType: string = '';
  description: string = '';

  successMessage: string = '';
  errorMessage: string = '';
  isSubmitting: boolean = false;

  constructor(private claimService: Claim,
      private cdr: ChangeDetectorRef,
      private router: Router
  ) {}

    ngOnInit(): void {
    console.log('Create Claim component initialized');
  }

  submitClaim(): void {

    this.successMessage = '';
    this.errorMessage = '';

    if (!this.claimType || !this.description.trim()) {
      this.errorMessage = 'Please fill in all fields.';
      return;
    }

    const claimData = {
      claimType: this.claimType,
      description: this.description.trim()
    };

    this.isSubmitting = true;


    this.claimService.createClaim(claimData)
    .subscribe({
      next: () => {
        this.successMessage = 'Claim submitted successfully!';
        this.claimType = '';
        this.description = '';
        this.isSubmitting = false;
         this.cdr.detectChanges();
          setTimeout(() => {
    this.router.navigate(['/home']);
  }, 1000);
      },
      error: (error) => {
        console.error('Error creating claim:', error);
        this.errorMessage =
          error.error?.message ||
          'Unable to submit claim. Please try again.';
        this.isSubmitting = false;
         this.cdr.detectChanges();
      },
    complete: () => {
      console.log('Claim request completed');
    }
    });
  }
}
