import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Conference } from './conferenceForm';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConferenceService {

  url: string = 'http://localhost:8082/api/conference'

  constructor(private http: HttpClient) { }

  postConference(conferenceData) {
    return this.http.post<any>(this.url, conferenceData);
  }
  //GET
  getConferences(): Observable<Conference[]> {
    return this.http.get<Conference[]>(this.url);
  }

  //DELETE
  deleteConference(id: number): Observable<Conference> {
    return this.http.delete<Conference>(`http://localhost:8082/api/conference/delete/${id}`);
}
}
