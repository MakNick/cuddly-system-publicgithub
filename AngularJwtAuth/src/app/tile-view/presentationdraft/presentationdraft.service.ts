import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { PresentationDraft } from '../../objects/presentation-draft';

@Injectable({
  providedIn: 'root'
})
export class PresentationdraftService {

  constructor(private http : HttpClient) { }

  getPresentationDraftsByLabel(conferenceId: number, labelId: number): Observable<PresentationDraft[]>{
    return this.http.get<PresentationDraft[]>(`http://localhost:8082/api/conference/${conferenceId}/findpresentationdraft/${labelId}`);
  }

  // updatePresentationDraft (conferenceId: number, presentationDraft: PresentationDraft){
  //   this.http.post<presentationDraft>(`http://localhost:8082/api/conference/${conferenceId}/savepresentationdraft`)
  // }
}
