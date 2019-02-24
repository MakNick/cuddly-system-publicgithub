import { TestBed } from '@angular/core/testing';

import { PresentationDraftService } from './presentation-draft.service';

describe('PresentationDraftService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PresentationDraftService = TestBed.get(PresentationDraftService);
    expect(service).toBeTruthy();
  });
});
