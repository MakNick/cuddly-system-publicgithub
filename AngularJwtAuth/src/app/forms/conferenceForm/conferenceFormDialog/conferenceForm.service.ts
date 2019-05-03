import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Applicant } from '../../objects/applicant';
import { Stage } from '../../objects/conference/stage';


@Injectable({
  providedIn: 'root'
})
export class dialogWindowService {

  savedApplicants: Applicant[] = [];
  savedCategories: string[] = [];
  savedStages: Stage[] = [];


  constructor(private http: HttpClient) { }

}
