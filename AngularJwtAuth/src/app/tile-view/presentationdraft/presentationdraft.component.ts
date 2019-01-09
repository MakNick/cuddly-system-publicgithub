import { Component, OnInit, Input } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { ConferenceService } from '../conference/conference.service';
import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { Conference } from 'src/app/objects/conference';

@Component({
  selector: 'app-presentationdraft',
  templateUrl: './presentationdraft.component.html',
  styleUrls: ['./presentationdraft.component.css']
})
export class PresentationdraftComponent implements OnInit {

  conference: Conference = new Conference();

  constructor(private conferenceService: ConferenceService,
              private route: ActivatedRoute,
              private location: Location) { }

  ngOnInit() {
    this.getConferences();
  }

  goBack(): void{
    this.location.back();
  }

  getConferences(): void{
    const id = +this.route.snapshot.paramMap.get('id');
    this.conferenceService.getConference(id)
    .subscribe(conference =>{ this.conference = conference; console.log(conference)});
  }

}
