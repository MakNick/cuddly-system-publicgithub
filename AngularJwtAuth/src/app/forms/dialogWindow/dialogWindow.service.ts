import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Applicant } from '../../objects/applicant';

@Injectable({
  providedIn: 'root'
})
export class dialogWindowService {

  saved: Applicant[] = [];

  constructor(private http: HttpClient) { }

}
