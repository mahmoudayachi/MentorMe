import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenteedashboardComponent } from './menteedashboard.component';

describe('MenteedashboardComponent', () => {
  let component: MenteedashboardComponent;
  let fixture: ComponentFixture<MenteedashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MenteedashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MenteedashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
