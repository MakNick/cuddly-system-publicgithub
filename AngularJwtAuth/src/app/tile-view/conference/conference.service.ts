import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Conference } from '../../objects/conference/conference';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ConferenceService {
  
  constructor(private http : HttpClient) { }

  getConferences(): Observable<Conference[]>{
    return this.http.get<Conference[]>("http://localhost:8082/api/conference");
  }

  getConference(id: number): Observable<Conference>{
    return this.http.get<Conference>(`http://localhost:8082/api/conference/${id}`);
  }

  getAllPdfByConferenceId(id: number){
    const httpOptions = {'responseType'  : 'arraybuffer' as 'json'};
    return this.http.get(`http://localhost:8082/api/${id}/download/pdf`, httpOptions);
  }

  getExcelByConferenceId(id: number){
    const httpOptions = {'responseType'  : 'arraybuffer' as 'json'};
    return this.http.get(`http://localhost:8082/api/${id}/download/excel`, httpOptions);
  }
}
