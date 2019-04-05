import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { PresentationDraft } from '../../objects/presentation-draft';
import {Page} from "../../objects/paging/page";

@Injectable({
  providedIn: 'root'
})
export class PresentationDraftService {

  constructor(private http : HttpClient) { }

  searchPresentationDraft(conferenceId: number, subject: string, category: string, label: string, page: number, limit: number): Observable<Page>{
    return this.http.get<Page>(`http://localhost:8082/api/conference/${conferenceId}/presentationdrafts/search?s=${subject}&c=${category}&l=${label}&page=${page}&limit=${limit}`);
  }

  getPresentationDraftByConferenceId(conferenceId: number, page: number, limit: number): Observable<Page>{
    return this.http.get<Page>(`http://localhost:8082/api/conference/${conferenceId}/presentationdrafts?page=${page}&limit=${limit}`);
  }

  updatePresentationDraft(conferenceId: number, presentationDraft: PresentationDraft): Observable<PresentationDraft>{
    return this.http.put<PresentationDraft>(`http://localhost:8082/api/save_presentationdraft/conferenceId/${conferenceId}`, presentationDraft);
  }

  deletePresentationDraft(presentationDraft: PresentationDraft): Observable<PresentationDraft>{
    return this.http.delete<PresentationDraft>(`http://localhost:8082/api/presentationdraft/delete/${presentationDraft.id}`);
  }

  downloadSinglePdf(presentationDraft: PresentationDraft, conferenceId: number){
    const httpOptions = {'responseType'  : 'arraybuffer' as 'json'};
    return this.http.get<any>(`http://localhost:8082/api/${conferenceId}/download/pdf/${presentationDraft.id}`, httpOptions);
  }


}

