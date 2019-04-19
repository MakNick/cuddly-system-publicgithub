import { Component, OnInit } from '@angular/core';
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

  constructor(private userService: UserService, private conferenceService: ConferenceService) { }

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

}
