import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { UserService } from '../services/user.service';
import {Conference} from '../objects/conference/conference';
import {ConferenceService} from '../tile-view/conference/conference.service';

import {NguCarouselConfig} from "@ngu/carousel";


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  board: string;
  errorMessage: string;

  public conferences: Conference[];

  public carouselTiles: any;

  public miniConferenceTileConfigs: NguCarouselConfig;

  public deadline: Date;

  constructor(private userService: UserService, private conferenceService: ConferenceService, private router: Router) { }

  ngOnInit() {
    this.getConferences();
  }

  getConferences(){
    this.conferenceService.getConferences().subscribe(conferences => {
      this.conferences = conferences;
      this.initializeCarousel();
    });
  }

  initializeCarousel() {
    this.carouselTiles = {
      0: this.conferences
    };
    const miniTileAmount: number = this.conferences.length < 8 ? this.conferences.length : 8;
    this.miniConferenceTileConfigs = {
      grid: {xs: 2, sm: 3, md: 4, lg: miniTileAmount, all: 0},
      speed: 250,
      point: {
        visible: true
      },
      touch: true,
      loop: true,
      interval: {timing: 3000},
      animation: 'lazy'
    };
  }
  indienen(i) {
    this.deadline = new Date (this.conferences[i].deadlinePresentationDraft[0]+','+this.conferences[i].deadlinePresentationDraft[1]+','+this.conferences[i].deadlinePresentationDraft[2]);
    console.log(this.deadline);
    console.log(new Date());  
    if (this.deadline < new Date()){
      alert("De deadline voor het indienen van voorstellen is al verstreken")
    } else {
      this.router.navigateByUrl('/user/'+this.conferences[i].id+'/presentationDraftApplicantForm');
    }
  }
}

