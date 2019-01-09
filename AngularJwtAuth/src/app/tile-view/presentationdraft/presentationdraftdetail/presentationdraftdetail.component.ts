import { Component, OnInit, Input } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';

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

  goBack(): void{
    this.location.back();
  }

}
