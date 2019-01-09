import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PresentationdraftComponent } from './presentationdraft.component';

describe('PresentationdraftComponent', () => {
  let component: PresentationdraftComponent;
  let fixture: ComponentFixture<PresentationdraftComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PresentationdraftComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PresentationdraftComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
