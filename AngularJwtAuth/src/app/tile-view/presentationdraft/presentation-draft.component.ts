import {Component, OnInit} from '@angular/core';
import {PresentationDraftService} from './presentation-draft.service';
import {ActivatedRoute} from '@angular/router';
import {Page} from "../../objects/page";
import {PageEvent} from "@angular/material";
import {ConferenceService} from "../conference/conference.service";

@Component({
  selector: 'app-presentationdraft',
  templateUrl: './presentation-draft.component.html',
  styleUrls: ['./presentation-draft.component.css']
})
export class PresentationDraftComponent implements OnInit {

  page: Page;
  conferenceId: number;

  breakpoint: number;

  subjectFilter: string = "";
  labelFilter: number;
  categoryFilter: string;

  availableCategories: string[];
  pageSizeOptions: number[] = [25,50,100];

  constructor(private presentationDraftService: PresentationDraftService,
              private route: ActivatedRoute, private conferenceService: ConferenceService) {

  }

  ngOnInit() {
    let screenWidth: number = window.innerWidth;
    if (screenWidth >= 711 && screenWidth <= 1150) {
      this.breakpoint = 6;
    } else if (screenWidth <= 710) {
      this.breakpoint = 4;
    } else if (screenWidth >= 1151 && screenWidth <= 1600) {
      this.breakpoint = 10;
    } else {
      this.breakpoint = 15;
    }

    this.conferenceId = +this.route.snapshot.paramMap.get('id');

    this.showAll();
    this.getCategories();

  }

  applyFilter() {
    if(this.categoryFilter != null && this.labelFilter != undefined){
      this.presentationDraftService.getPresentationDraftsByConferenceIdAndCategoryAndLabelId(this.conferenceId, this.categoryFilter.toLowerCase(), this.labelFilter, 1, 25)
        .subscribe(page => this.page = page);
    }else if(this.categoryFilter != null){
      this.presentationDraftService.getPresentationDraftsByConferenceIdAndCategory(this.conferenceId, this.categoryFilter.toLowerCase(), 1, 25)
        .subscribe(page => this.page = page);
    }else if(this.labelFilter != undefined){
      this.presentationDraftService.getPresentationDraftsByConferenceIdAndLabelId(this.conferenceId, this.labelFilter, 1, 25)
        .subscribe(page => this.page = page)
    }
  }

  getCategories() {
    this.conferenceService.getConference(this.conferenceId).subscribe(conference => this.availableCategories = conference.categories);
  }

  showAll() {
    this.presentationDraftService.getPresentationDraftByConferenceId(this.conferenceId, 1, 25).subscribe(page => {
      this.page = page;
    });
  }

  resetFilters() {
    this.labelFilter = undefined;
    this.categoryFilter = null;
    this.subjectFilter = "";
    this.showAll()
  }

  onResize(event) {
    let screenWidth: number = event.target.innerWidth;
    if (screenWidth >= 711 && screenWidth <= 1150) {
      this.breakpoint = 6;
    } else if (screenWidth <= 710) {
      this.breakpoint = 4;
    } else if (screenWidth >= 1151 && screenWidth <= 1600) {
      this.breakpoint = 10;
    } else {
      this.breakpoint = 15;
    }
  }

  paginate(pageEvent: PageEvent) {
    this.presentationDraftService.getPresentationDraftByConferenceId(this.conferenceId, pageEvent.pageIndex + 1, pageEvent.pageSize)
      .subscribe(page => this.page = page);
  }

}
