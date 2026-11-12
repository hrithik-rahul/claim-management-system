import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Createclaim } from './createclaim';

describe('Createclaim', () => {
  let component: Createclaim;
  let fixture: ComponentFixture<Createclaim>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Createclaim],
    }).compileComponents();

    fixture = TestBed.createComponent(Createclaim);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
