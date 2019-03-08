import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';

import {ConferenceService} from './conference.service';

import {Conference} from '../../objects/conference/conference';
import {slider} from "../../animations/carousel";
import {NguCarouselConfig} from "@ngu/carousel";
import {interval, Observable} from "rxjs";
import {map, startWith, take} from "rxjs/operators";
import {DateFormatService} from "../../services/date-format.service";

@Component({
  selector: 'app-conference',
  templateUrl: './conference.component.html',
  styleUrls: ['./conference.component.css'],
  animations: [slider],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ConferenceComponent implements OnInit {

  public conferences: Conference[];

  public carouselTiles;
  public mainConferenceTileConfigs: NguCarouselConfig;
  public miniConferenceTileConfigs: NguCarouselConfig;

  constructor(private conferenceService: ConferenceService,
              private dateFormatService: DateFormatService) {
  }

  ngOnInit() {
    this.fillConferences();
  }

  // to give each tile it's image call in the carousel tag like so: (carouselLoad)="carouselTileLoad(0)"
  // public carouselTileLoad(j) {
  //   const len = this.carouselTiles[j].length;
  //   for (let i = len; i < len; i++) {
  //     this.carouselTiles[j].push(
  //       this.images[Math.floor(Math.random() * this.images.length)]
  //     );
  //   }
  // }

  fillConferences() {
    this.conferenceService.getConferences()
      .subscribe(conferences =>
          this.conferences = conferences,
        error => console.log(error),
        () => this.initializeCarousel()
      );
  }

  showCorrectDate(date: Date){
    return this.dateFormatService.showCorrectDate(date);
  }

  initializeCarousel() {
    this.carouselTiles = {
      0: this.conferences
    };

    const mainTileAmount = this.conferences.length < 3 ? this.conferences.length : 3;
    this.mainConferenceTileConfigs= {
      grid: {xs: 1, sm: 1, md: 1, lg: mainTileAmount, all: 0},
      slide: 3,
      speed: 250,
      point: {
        visible: true
      },
      load: 3,
      velocity: 0,
      loop: true,
      touch: true,
      easing: 'cubic-bezier(0, 0, 0.2, 1)'
    };

    const miniTileAmount: number = this.conferences.length < 10 ? this.conferences.length : 8;
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
    // this.carouselTileItems.forEach(el => {
    //   this.carouselTileLoad(el);
    // });
  }

}
