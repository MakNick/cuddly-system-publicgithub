import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { viewParentEl } from '@angular/core/src/view/util';

@Component({
  selector: 'app-presentationdraftdetail',
  templateUrl: './presentationdraftdetail.component.html',
  styleUrls: ['./presentationdraftdetail.component.css']
})
export class PresentationdraftdetailComponent implements OnInit {

  @Input() 
  PsDetail: PresentationDraft;

  @Output() onClose: EventEmitter<PresentationDraft> = new EventEmitter<PresentationDraft>();

  constructor(private location: Location) { }

  ngOnInit() {
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

}
