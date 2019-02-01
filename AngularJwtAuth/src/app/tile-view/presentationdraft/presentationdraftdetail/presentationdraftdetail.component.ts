import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { viewParentEl } from '@angular/core/src/view/util';
import { Conference } from 'src/app/objects/conference/conference';
import { PsDetailService } from '../psDetail.service';

@Component({
  
  selector: 'app-presentationdraftdetail',
  templateUrl: './presentationdraftdetail.component.html',
  styleUrls: ['./presentationdraftdetail.component.css']
})
export class PresentationdraftdetailComponent implements OnInit {

  PsDetail: PresentationDraft;

  conferenceDetail: Conference;

  constructor(private location: Location,
              private psDetailService: PsDetailService) { }

  ngOnInit() {
    this.PsDetail=this.psDetailService.selectedPresentationDraft;
    this.conferenceDetail=this.psDetailService.activeConference;
  }

  goBack(event): void{
    if(event.target !== event.currentTarget){
      return;
    }
  }

  changeLabel(value){
    this.PsDetail.label = value;
  }

  changeCategory(value){
    this.PsDetail.category = value;
    console.log(this.PsDetail);
  }

}
