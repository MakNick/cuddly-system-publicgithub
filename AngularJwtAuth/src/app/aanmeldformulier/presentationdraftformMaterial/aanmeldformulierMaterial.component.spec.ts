import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AanmeldformulierMaterialComponent } from './aanmeldformulierMaterial.component';

describe('AanmeldformulierComponent', () => {
  let component: AanmeldformulierMaterialComponent;
  let fixture: ComponentFixture<AanmeldformulierMaterialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AanmeldformulierMaterialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AanmeldformulierMaterialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
