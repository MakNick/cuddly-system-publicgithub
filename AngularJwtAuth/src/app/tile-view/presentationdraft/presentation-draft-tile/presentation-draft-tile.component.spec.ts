import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PresentationDraftTileComponent } from './presentation-draft-tile.component';

describe('PresentationDraftTileComponent', () => {
  let component: PresentationDraftTileComponent;
  let fixture: ComponentFixture<PresentationDraftTileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PresentationDraftTileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PresentationDraftTileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
