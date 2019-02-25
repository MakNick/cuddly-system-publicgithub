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

  private conferences: Conference[];

  @Input() name: string;
  images = [
    'assets/bg.jpg',
    'assets/car.png',
    'assets/canberra.jpg',
    'assets/holi.jpg'
  ];

  public carouselTiles;

  public carouselTile: NguCarouselConfig = {
    grid: {xs: 1, sm: 1, md: 1, lg: 3, all: 0},
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

  public carouselTileItems$: Observable<Conference[]>;
  public carouselTileConfig: NguCarouselConfig = {
    grid: {xs: 10, sm: 10, md: 10, lg: 10, all: 0},
    speed: 250,
    point: {
      visible: true
    },
    touch: true,
    loop: true,
    interval: {timing: 1500},
    animation: 'lazy'
  };

  private tempData: any[];

  constructor(private conferenceService: ConferenceService) {
  }

  ngOnInit() {
    this.fillConferences();
  }

  // to give each tile it's image
  public carouselTileLoad(j) {
  //   // alert(this.carouselTiles[j].length);
  //   // const len = this.carouselTiles[j].length;
  //   // for (let i = len; i < len; i++) {
  //   //   this.carouselTiles[j].push(
  //   //     this.images[Math.floor(Math.random() * this.images.length)]
  //   //   );
  //   // }
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
    }
    // for (let i = 0; i < this.conferences.length; i++) {
    //   this.carouselTiles = {
    //     i: [this.conferences[i]]
    //   }
    // }
    // this.carouselTileItems.forEach(el => {
    //   this.carouselTileLoad(el);
    // });
    this.tempData = [];
    this.carouselTileItems$ = interval(500).pipe(
      startWith(-1),
      take(this.conferences.length),
      map(val => {
        const data = (this.tempData = [
          ...this.conferences
        ]);
        return data;
      })
    );
  }

}
