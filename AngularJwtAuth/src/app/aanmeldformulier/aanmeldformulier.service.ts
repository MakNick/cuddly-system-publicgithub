import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PresentationDraft } from '../objects/presentation-draft';
import { PresentationDraftApplicant } from '../objects/presentationDraftApplicant';

@Injectable({
  providedIn: 'root'
})
export class draftAanmeldService {

  private userUrl = 'http://localhost:8082/api/presentationdraft';

  constructor(private http: HttpClient) { }

  postPresentationDraftApplicant(presentationDraftApplicant): Observable<PresentationDraftApplicant>{
    return this.http.post<PresentationDraftApplicant>(this.userUrl, presentationDraftApplicant);
  }


}
