import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PresentationdraftdetailComponent } from './presentationdraftdetail.component';

describe('PresentationdraftdetailComponent', () => {
  let component: PresentationdraftdetailComponent;
  let fixture: ComponentFixture<PresentationdraftdetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PresentationdraftdetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PresentationdraftdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
