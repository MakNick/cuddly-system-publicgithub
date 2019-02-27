import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PresentationDraftDetailComponent } from './presentation-draft-detail.component';

describe('PresentationDraftDetailComponent', () => {
  let component: PresentationDraftDetailComponent;
  let fixture: ComponentFixture<PresentationDraftDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PresentationDraftDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PresentationDraftDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
