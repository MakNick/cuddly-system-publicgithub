import { Component, OnInit, Renderer2, ElementRef, Directive } from '@angular/core';

import { ConferenceService } from './conference.service';

import { Conference } from '../../objects/conference/conference';

@Component({
  selector: 'app-conference',
  templateUrl: './conference.component.html',
  styleUrls: ['./conference.component.css']
})
@Directive({
  selector: '[changeCarousel]'
})
export class ConferenceComponent implements OnInit {

  private conferences: Conference[];

  constructor(private conferenceService: ConferenceService,
              private renderer2: Renderer2,
              private el: ElementRef) { }

  ngOnInit() {
    this.fillConferences();
    this.renderer2.addClass(this.el.nativeElement ,"carousel-item");
    this.renderer2.addClass(this.el.nativeElement ,"active");
  }

  fillConferences(): void{
    this.conferenceService.getConferences()
    .subscribe(conferences => this.conferences = conferences);
  }

  changeClassName(): void{
    
    this.renderer2.removeClass(this.el.nativeElement ,"active");
  }

}
