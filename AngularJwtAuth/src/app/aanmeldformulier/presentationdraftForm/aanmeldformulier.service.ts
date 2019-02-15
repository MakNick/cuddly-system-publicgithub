import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PresentationDraftApplicant } from '../../objects/presentationDraftApplicant';

@Injectable({
  providedIn: 'root'
})
export class draftAanmeldService {

  private userUrl = 'http://localhost:8082/api/presentationdraft';

  constructor(private http: HttpClient) { }

  postPresentationDraftApplicant(presentationDraftApplicant){
    return this.http.post<PresentationDraftApplicant>(this.userUrl, presentationDraftApplicant);
  }


}
