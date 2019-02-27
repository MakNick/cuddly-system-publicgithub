import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Headers, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs';
import { PresentationDraft } from '../../objects/presentation-draft';
import { Conference } from 'src/app/aanmeldformulier/conferenceForm/conferenceForm';
import {Page} from "../../objects/paging/page";

@Injectable({
  providedIn: 'root'
})
export class PresentationDraftService {

  constructor(private http : HttpClient) { }

  getPresentationDraftByConferenceId(conferenceId: number, page: number, limit: number): Observable<Page>{
    return this.http.get<Page>(`http://localhost:8082/api/conference/${conferenceId}/presentationdrafts?page=${page}&limit=${limit}`);
  }

  getPresentationDraftsByConferenceIdAndLabelId(conferenceId: number, labelId: number, page: number, limit: number): Observable<Page>{
    return this.http.get<Page>(`http://localhost:8082/api/conference/${conferenceId}/presentationdrafts/label/${labelId}?page=${page}&limit=${limit}`);
  }

  getPresentationDraftsByConferenceIdAndCategory(conferenceId: number, category: string, page: number, limit: number): Observable<Page>{
    return this.http.get<Page>(`http://localhost:8082/api/conference/${conferenceId}/presentationdrafts/category/${category}?page=${page}&limit=${limit}`);
  }

  getPresentationDraftsByConferenceIdAndCategoryAndLabelId(conferenceId: number, category: string, labelId: number, page: number, limit: number): Observable<Page>{
    return this.http.get<Page>(`http://localhost:8082/api/conference/${conferenceId}/presentationdrafts/category/${category}/labelId/${labelId}?page=${page}&limit=${limit}`);
  }

  updatePresentationDraft(conferenceId: number, presentationDraft: PresentationDraft): Observable<PresentationDraft>{
    return this.http.put<PresentationDraft>(`http://localhost:8082/api/save_presentationdraft/conferenceId/${conferenceId}`, presentationDraft);
  }

  deletePresentationDraft(presentationDraft: PresentationDraft): Observable<PresentationDraft>{
    return this.http.delete<PresentationDraft>(`http://localhost:8082/api/presentationdraft/delete/${presentationDraft.id}`);
  }

  downloadSinglePdf(presentationDraft: PresentationDraft, conference: Conference){
    const httpOptions = {'responseType'  : 'arraybuffer' as 'json'};
    return this.http.get<any>(`http://localhost:8082/api/${conference.id}/download/pdf/${presentationDraft.id}`, httpOptions);
  }


}
