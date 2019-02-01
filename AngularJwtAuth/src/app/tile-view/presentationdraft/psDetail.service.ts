import { Injectable } from '@angular/core';
import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { Conference } from 'src/app/objects/conference/conference';

@Injectable()
export class PsDetailService {

  selectedPresentationDraft: PresentationDraft;
  activeConference: Conference;
  
  constructor() { }

}