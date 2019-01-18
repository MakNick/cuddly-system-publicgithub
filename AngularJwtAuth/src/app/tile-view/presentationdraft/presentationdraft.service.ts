import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PresentationdraftService {

  constructor(private http : HttpClient) { }

  getPresentationDraftsByLabel(conferenceId: number, labelId: number): Observable<PresentationDraft[]>{
    return this.http.get<PresentationDraft[]>(`http://localhost:8082/api/conference/${conferenceId}/findpresentationdraft/${labelId}`);
  }
}
