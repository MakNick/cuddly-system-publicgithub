import { Component, OnInit, NgModule } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

import { ConferenceService } from '../conference/conference.service';
import { Conference } from 'src/app/objects/conference/conference';
import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { PresentationdraftService } from './presentationdraft.service';

@Component({
  selector: 'app-presentationdraft',
  templateUrl: './presentationdraft.component.html',
  styleUrls: ['./presentationdraft.component.css']
})
export class PresentationdraftComponent implements OnInit {

  conference: Conference = new Conference();
  presentationDrafts: PresentationDraft[];

  constructor(private conferenceService: ConferenceService,
              private presentationDraftService: PresentationdraftService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router) { }

  ngOnInit() {
    this.getConferences();
  }

  goBack(): void{
    this.location.back();
  }

  emptyList(): void{
    this.conference = new Conference();
  }

  getTimeOfCreation(presentationDraft: PresentationDraft): string{
    let dateToString: string = "" + presentationDraft.timeOfCreation;
    let arrayOfDate: string[] = dateToString.split(",");
    let formattedDate: string = "";
    for(let i = 2; i >= 0; i--){
      if(arrayOfDate[i] !== ","){
        
        if(i == 0){
          formattedDate += arrayOfDate[i];
        }else{
          formattedDate += arrayOfDate[i]+"-";
        }
      }
    }
    return formattedDate;
  }

  showAllPresentationDrafts(): void{
    this.presentationDrafts = this.conference.presentationDrafts;
  }

  getConferences(): void{
    const id = +this.route.snapshot.paramMap.get('id');
    this.conferenceService.getConference(id)
    .subscribe(conference =>{
      this.conference = conference
      this.presentationDrafts = conference.presentationDrafts;
    });
  }

  getPresentationDraftByLabel(id: number){
    this.presentationDraftService
    .getPresentationDraftsByLabel(this.conference.id, id)
    .subscribe(PresentationDrafts => this.presentationDrafts = PresentationDrafts);
  }

  showPresentationDetail(): void {
    this.router.navigate([{ outlets: { presentationDraftDetail : [ 'presentationDraftDetail' ] } }])
  }

}
