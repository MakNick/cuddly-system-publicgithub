import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConferenceFormDialogComponent } from './conferenceForm-dialog.component';

describe('ConferenceFormDialogComponent', () => {
  let component: ConferenceFormDialogComponent;
  let fixture: ComponentFixture<ConferenceFormDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConferenceFormDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConferenceFormDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
