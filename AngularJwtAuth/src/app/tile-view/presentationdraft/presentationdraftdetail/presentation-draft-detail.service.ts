import { Injectable, OnInit } from '@angular/core';
import { PresentationDraft } from 'src/app/objects/presentation-draft';

@Injectable()
export class PresentationDraftDetailService {

  selectedPresentationDraft: PresentationDraft;
  categories: string[];
  activeConferenceId: number;
  numberOfDrafts: number;

  constructor() {
  }

}
