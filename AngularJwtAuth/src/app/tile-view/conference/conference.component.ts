import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';

import {ConferenceService} from './conference.service';

import {Conference} from '../../objects/conference/conference';
import {slider} from "../../animations/carousel";
import {NguCarouselConfig} from "@ngu/carousel";
import {interval, Observable} from "rxjs";
import {map, startWith, take} from "rxjs/operators";

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

  public carouselTilesMiniView: Observable<Conference[]>;
  public miniConferenceTileConfigs: NguCarouselConfig;

  constructor(private conferenceService: ConferenceService) {
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

  showCorrectDate(date: Date) {
    let arrayOfDate: string[] = String(date).split(",");
    let formattedDate: string = "";
    for (let i = 2; i >= 0; i--) {
      if (arrayOfDate[i] !== ",") {
        if (i == 0) {
          +arrayOfDate[i] < 10 ? formattedDate += ("0" + arrayOfDate[i]) : formattedDate += arrayOfDate[i];
        } else {
          // formattedDate +=correctDate + "-";
          +arrayOfDate[i] < 10 ? formattedDate += ("0" + arrayOfDate[i] + "-") : formattedDate += arrayOfDate[i] + "-";
        }
      }
    }
    return formattedDate;
  }

  fillConferences() {
    this.conferenceService.getConferences()
      .subscribe(conferences =>
          this.conferences = conferences,
        error => console.log(error),
        () => this.initializeCarousel()
      );
  }

  initializeCarousel() {
    this.carouselTiles = {
      0: this.conferences
    };

    const mainTileAmount = this.conferences.length < 3 ? this.conferences.length : 3;
    this.mainConferenceTileConfigs= {
      grid: {xs: 1, sm: 2, md: mainTileAmount, lg: mainTileAmount, all: 0},
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

    const miniTileAmount: number = this.conferences.length < 10 ? this.conferences.length : 10;
    this.miniConferenceTileConfigs = {
      grid: {xs: 2, sm: 3, md: miniTileAmount-2, lg: miniTileAmount, all: 0},
      speed: 250,
      point: {
        visible: true
      },
      touch: true,
      interval: {timing: 5000},
      animation: 'lazy'
    };

    this.carouselTilesMiniView = interval(500).pipe(
      startWith(-1),
      take(this.conferences.length),
      map(() => {
        return [...this.conferences];
      })
    );
    // this.carouselTileItems.forEach(el => {
    //   this.carouselTileLoad(el);
    // });
  }

}
