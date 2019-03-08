import {Component, Inject, Input, OnInit} from '@angular/core';
import {PresentationDraft} from "../../../objects/presentation-draft";
import {DateFormatService} from "../../../services/date-format.service";

@Component({
  selector: 'app-presentation-draft-tile',
  templateUrl: './presentation-draft-tile.component.html',
  styleUrls: ['./presentation-draft-tile.component.css']
})
export class PresentationDraftTileComponent implements OnInit {

  @Input()
  presentationDraft: PresentationDraft;

  constructor(private dateFormatService: DateFormatService) { }

  ngOnInit() {
  }

  showCorrectDate(date: Date){
    return this.dateFormatService.showCorrectDate(date);
  }

}
