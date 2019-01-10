import { Component, OnInit, Input } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { viewParentEl } from '@angular/core/src/view/util';

@Component({
  selector: 'app-presentationdraftdetail',
  templateUrl: './presentationdraftdetail.component.html',
  styleUrls: ['./presentationdraftdetail.component.css']
})
export class PresentationdraftdetailComponent implements OnInit {

  @Input()selectedDraft: PresentationDraft;

  constructor(private location: Location) { }

  ngOnInit() {
  }

  goBack(event): void{
    if(event.target !== event.currentTarget){
      return;
    }
    this.location.back()
  }

}
