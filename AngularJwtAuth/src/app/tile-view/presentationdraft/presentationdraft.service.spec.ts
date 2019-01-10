import { TestBed } from '@angular/core/testing';

import { PresentationdraftService } from './presentationdraft.service';

describe('PresentationdraftService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PresentationdraftService = TestBed.get(PresentationdraftService);
    expect(service).toBeTruthy();
  });
});
