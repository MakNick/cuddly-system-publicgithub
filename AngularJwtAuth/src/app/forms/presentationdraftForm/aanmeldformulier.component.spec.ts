import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AanmeldformulierComponent } from './aanmeldformulier.component';

describe('AanmeldformulierComponent', () => {
  let component: AanmeldformulierComponent;
  let fixture: ComponentFixture<AanmeldformulierComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AanmeldformulierComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AanmeldformulierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
