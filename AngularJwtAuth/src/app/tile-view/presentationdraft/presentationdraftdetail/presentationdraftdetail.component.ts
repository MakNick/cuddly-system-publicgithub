import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { viewParentEl } from '@angular/core/src/view/util';
import { Conference } from 'src/app/objects/conference/conference';

@Component({
  selector: 'app-presentationdraftdetail',
  templateUrl: './presentationdraftdetail.component.html',
  styleUrls: ['./presentationdraftdetail.component.css']
})
export class PresentationdraftdetailComponent implements OnInit {

  categoryList:any[];

  @Input() 
  PsDetail: PresentationDraft;

  @Input()
  conferenceDetail: Conference;

   @Output() onClose: EventEmitter<PresentationDraft> = new EventEmitter<PresentationDraft>();

  constructor(private location: Location) { }

  ngOnInit() {
    console.log(this.conferenceDetail);
  }

  goBack(event): void{
    if(event.target !== event.currentTarget){
      return;
    }
  this.onClose.emit(this.PsDetail);
  this.PsDetail = null;
  }

  changeLabel(value){
    this.PsDetail.label = value;
  }

  changeCategory(value){
    this.PsDetail.category = value;
    console.log(this.PsDetail);
  }

}
