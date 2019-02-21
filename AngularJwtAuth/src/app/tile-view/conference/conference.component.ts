import {ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';

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

  public carouselTileItems: Array<any> = [0];
  public carouselTiles = {
    0: [this.conferences]
  };

  public carouselTile: NguCarouselConfig = {
    grid: { xs: 1, sm: 1, md: 1, lg: 1, all: 0 },
    slide: 3,
    speed: 250,
    point: {
      visible: true
    },
    load: 3,
    velocity: 0,
    touch: true,
    easing: 'cubic-bezier(0, 0, 0.2, 1)'
  };

  public carouselTileItems$: Observable<number[]>;
  public carouselTileConfig: NguCarouselConfig = {
    grid: { xs: 10, sm: 10, md: 10, lg: 10, all: 0 },
    speed: 250,
    point: {
      visible: true
    },
    touch: true,
    loop: true,
    interval: { timing: 1500 },
    animation: 'lazy'
  };
  tempData: any[];

  constructor(private conferenceService: ConferenceService) {
  }

  ngOnInit() {
    this.fillConferences();
    this.carouselTileItems.forEach(el => {
      this.carouselTileLoad(el);
    });
    this.tempData = [];
    this.carouselTileItems$ = interval(500).pipe(
      startWith(-1),
      take(30),
      map(val => {
        const data = (this.tempData = [
          ...this.tempData,
          this.images[Math.floor(Math.random() * this.images.length)]
        ]);
        return data;
      })
    );
  }

  public carouselTileLoad(j) {
    const len = this.carouselTiles[j].length;
    if (len <= 30) {
      for (let i = len; i < len + 15; i++) {
        this.carouselTiles[j].push(
          this.images[Math.floor(Math.random() * this.images.length)]
        );
      }
    }
  }

  fillConferences(): void {
    this.conferenceService.getConferences()
      .subscribe(conferences => this.conferences = conferences);
  }

}
