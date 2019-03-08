import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AanmeldformulierDialogComponent } from './aanmeldformulier-dialog.component';

describe('AanmeldformulierDialogComponent', () => {
  let component: AanmeldformulierDialogComponent;
  let fixture: ComponentFixture<AanmeldformulierDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AanmeldformulierDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AanmeldformulierDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
