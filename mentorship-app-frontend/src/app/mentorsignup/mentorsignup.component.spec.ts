import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MentorsignupComponent } from './mentorsignup.component';

describe('MentorsignupComponent', () => {
  let component: MentorsignupComponent;
  let fixture: ComponentFixture<MentorsignupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MentorsignupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MentorsignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
