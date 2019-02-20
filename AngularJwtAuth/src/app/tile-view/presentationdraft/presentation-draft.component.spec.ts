import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PresentationDraftComponent } from './presentation-draft.component';
import { PresentationdraftdetailComponent } from './presentationdraftdetail/presentationdraftdetail.component';

describe('PresentationDraftComponent', () => {
  let component: PresentationDraftComponent;
  let fixture: ComponentFixture<PresentationDraftComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PresentationDraftComponent]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PresentationDraftComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
