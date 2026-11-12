import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Viewclaim } from './viewclaim';

describe('Viewclaim', () => {
  let component: Viewclaim;
  let fixture: ComponentFixture<Viewclaim>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Viewclaim],
    }).compileComponents();

    fixture = TestBed.createComponent(Viewclaim);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
