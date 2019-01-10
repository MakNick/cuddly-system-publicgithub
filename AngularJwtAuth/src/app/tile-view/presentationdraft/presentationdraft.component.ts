import { Component, OnInit, NgModule } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

import { ConferenceService } from '../conference/conference.service';
import { Conference } from 'src/app/objects/conference/conference';
import { PresentationDraft } from 'src/app/objects/presentation-draft';

@Component({
  selector: 'app-presentationdraft',
  templateUrl: './presentationdraft.component.html',
  styleUrls: ['./presentationdraft.component.css']
})
export class PresentationdraftComponent implements OnInit {

  conference: Conference = new Conference();

  constructor(private conferenceService: ConferenceService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router) { }

  ngOnInit() {
    this.getConferences();
  }

  goBack(): void{
    this.location.back();
  }

  getTimeOfCreation(presentationDraft: PresentationDraft): string{
    let dateToString: string = "" + presentationDraft.timeOfCreation;
    let arrayOfDate: string[] = dateToString.split(",");
    let formattedDate: string = "";
    let counter: number = 0;
    for(let value of arrayOfDate){
      if(value !== "," && counter < 3){
        if(counter == 2){
          formattedDate += value
          counter++;
        }else{
          formattedDate += value+"-"
          counter++;
        }
      }
    }
    return formattedDate;
  }

  getConferences(): void{
    const id = +this.route.snapshot.paramMap.get('id');
    this.conferenceService.getConference(id)
    .subscribe(conference =>{ this.conference = conference; console.log(conference)});
  }

  showPresentationDetail(): void {
    this.router.navigate([{ outlets: { presentationDraftDetail : [ 'presentationDraftDetail' ] } }])
  }

}
