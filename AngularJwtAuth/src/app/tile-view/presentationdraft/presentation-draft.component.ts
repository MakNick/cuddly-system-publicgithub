import {Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";

import {ActivatedRoute} from '@angular/router';

import {PageEvent} from "@angular/material";

import {Page} from "../../objects/paging/page";
import {PresentationDraftService} from './presentation-draft.service';
import {ConferenceService} from "../conference/conference.service";
import {PresentationDraft} from "../../objects/presentation-draft";
import {PresentationDraftDetailService} from "./presentationdraftdetail/presentation-draft-detail.service";
import {fadeOut} from "../../animations/presentation-draft-tile-view";
import {DateFormatService} from "../../services/date-format.service";

@Component({
  selector: 'app-presentationdraft',
  templateUrl: './presentation-draft.component.html',
  styleUrls: ['./presentation-draft.component.css'],
  animations: [fadeOut]
})
export class PresentationDraftComponent implements OnInit {

  page: Page;
  conferenceId: number;

  breakpoint: number;

  searchToken: string = "";
  labelFilter: number;
  categoryFilter: string;

  availableCategories: string[];
  numberOfDrafts: number;

  currentPageIndex: number;
  pageSizeOptions: number[] = [25, 50, 100];

  constructor(private presentationDraftService: PresentationDraftService,
              private route: ActivatedRoute, private conferenceService: ConferenceService,
              private presentationDraftDetailService: PresentationDraftDetailService,
              private location: Location) {

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

  showAll() {
    this.presentationDraftService.getPresentationDraftByConferenceId(this.conferenceId, this.currentPageIndex ? this.currentPageIndex : 1, this.page ? this.page.size : 25).subscribe(page => {
      this.page = page;
    });
  }

  paginate(pageEvent: PageEvent) {
    this.savePageConfigs(pageEvent)
    this.presentationDraftService.getPresentationDraftByConferenceId(this.conferenceId, pageEvent.pageIndex + 1, pageEvent.pageSize)
      .subscribe(page => this.page = page);
  }

  savePageConfigs(event) {
    this.page.size = event.pageSize;
    event.pageIndex == 1 ? this.currentPageIndex = 2 : this.currentPageIndex = event.pageIndex;
  }

  back() {
    this.location.back();
  }

  showPresentationDraftDetail(ps: PresentationDraft): void {
    this.presentationDraftDetailService.selectedPresentationDraft = ps;
    this.presentationDraftDetailService.activeConferenceId = this.conferenceId;
    this.presentationDraftDetailService.categories = this.availableCategories;
    this.presentationDraftDetailService.numberOfDrafts = this.numberOfDrafts;
  }

  getCategories() {
    this.conferenceService.getConference(this.conferenceId).subscribe(conference => this.availableCategories = conference.categories);
    this.conferenceService.getConference(this.conferenceId).subscribe(conference => this.numberOfDrafts = conference.presentationDrafts.length);
  }

  applyFilter() {
    if (this.categoryFilter != null && this.labelFilter != undefined) {
      this.presentationDraftService.getPresentationDraftsByConferenceIdAndCategoryAndLabelId(this.conferenceId, this.categoryFilter.toLowerCase(), this.labelFilter, 1, 25)
        .subscribe(page => this.page = page);
    } else if (this.categoryFilter != null) {
      this.presentationDraftService.getPresentationDraftsByConferenceIdAndCategory(this.conferenceId, this.categoryFilter.toLowerCase(), 1, 25)
        .subscribe(page => this.page = page);
    } else if (this.labelFilter != undefined) {
      this.presentationDraftService.getPresentationDraftsByConferenceIdAndLabelId(this.conferenceId, this.labelFilter, 1, 25)
        .subscribe(page => this.page = page)
    }
  }

  resetFilters() {
    this.labelFilter = undefined;
    this.categoryFilter = null;
    this.searchToken = "";
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

  downloadAllePDF() {
    this.conferenceService.getAllPdfByConferenceId(this.conferenceId).subscribe((response) => {let blob = new Blob([response], { type: 'application/pdf' });
    var fileUrl = window.document.createElement('a');
    fileUrl.href = window.URL.createObjectURL(blob);
    fileUrl.download = 'PresentationDrafts.pdf';
    fileUrl.click()});
  }

  downloadExcelOverzicht() {
    this.conferenceService.getExcelByConferenceId(this.conferenceId).subscribe((response) => {let blob = new Blob([response], { type: 'application/pdf' });
    var fileUrl = window.document.createElement('a');
    fileUrl.href = window.URL.createObjectURL(blob);
    fileUrl.download = 'PresentationDrafts.xlsx';
    fileUrl.click()});
  }
}