import { TestBed } from '@angular/core/testing';
import { Claim } from './claimService';


describe('Claim', () => {
  let service: Claim;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Claim);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
