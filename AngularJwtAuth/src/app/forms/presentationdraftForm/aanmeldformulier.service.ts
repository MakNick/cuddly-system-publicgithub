import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PresentationDraftApplicant } from '../../objects/presentationDraftApplicant';

@Injectable({
  providedIn: 'root'
})
export class draftAanmeldService {

  constructor(private http: HttpClient) { }

  postPresentationDraftApplicant(presentationDraftApplicant, conferenceID:number){
    return this.http.post<PresentationDraftApplicant>('http://localhost:8082/api/conference/'+conferenceID+'/savepresentationdraft', presentationDraftApplicant);
  }
}
