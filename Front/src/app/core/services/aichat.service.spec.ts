import { TestBed } from '@angular/core/testing';

import { AichatService } from './aichat.service';

describe('AichatService', () => {
  let service: AichatService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AichatService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
