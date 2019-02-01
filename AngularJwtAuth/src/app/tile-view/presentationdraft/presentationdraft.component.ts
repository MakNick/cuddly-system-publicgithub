import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

import { ConferenceService } from '../conference/conference.service';
import { Conference } from 'src/app/objects/conference/conference';
import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { PresentationdraftService } from './presentationdraft.service';
import { PagerService } from 'src/app/pager/pager.service';
import { injectComponentFactoryResolver } from '@angular/core/src/render3';

@Component({
  selector: 'app-presentationdraft',
  templateUrl: './presentationdraft.component.html',
  styleUrls: ['./presentationdraft.component.css']
})
export class PresentationdraftComponent implements OnInit {

  selectedPresentationdraft : PresentationDraft;

  conference : Conference = new Conference();

  pager: any = {};
  currentPageNumber: number;
  pagedItems:any[];
  sortedPagedItems: any[] = [];

  constructor(private conferenceService: ConferenceService,
              private presentationDraftService: PresentationdraftService,
              private pagerService: PagerService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router) { }

  ngOnInit() {
    this.getConferences();
  }

  goBack(): void{
    this.location.back();
  }

  getTimeOfCreation(presentationDraft: PresentationDraft): string{
    let dateToString: string = "" + presentationDraft.timeOfCreation;
    let arrayOfDate: string[] = dateToString.split(",");
    let formattedDate: string = "";
    for(let i = 2; i >= 0; i--){
      if(arrayOfDate[i] !== ","){
        
        if(i == 0){
          formattedDate += arrayOfDate[i];
        }else{
          formattedDate += arrayOfDate[i]+"-";
        }
      }
    }
    return formattedDate;
  }

  showAllPresentationDrafts(): void{
    this.sortedPagedItems = [];
    this.setPage(1);
  }

  getConferences(): void{
    const id = +this.route.snapshot.paramMap.get('id');
    this.conferenceService.getConference(id)
    .subscribe(conference =>{
      this.conference = conference;
      this.setPage(1);
    });
  }

  showPresentationDraftByCategory(event): void{
    this.sortedPagedItems = [];
    for(let pres of this.conference.presentationDrafts){
      if(pres.category === event.target[event.target.selectedIndex].text){
        this.sortedPagedItems.push(pres);
        console.log(pres.subject + " added");
      }
    }
    this.paginate(1);
  }

  getPresentationDraftByLabel(id: number){
    this.presentationDraftService
    .getPresentationDraftsByLabel(this.conference.id, id)
    .subscribe(presentationDraft => {
      this.sortedPagedItems = presentationDraft
      this.setSortedPage(1);
    });
  }

  emptySelected(){
    alert();
    this.selectedPresentationdraft = null;
  }

  showPresentationDetail(ps:PresentationDraft): void {
    this.selectedPresentationdraft = ps;
    // this.router.navigate([{ outlets: { presentationDraftDetail : [ 'presentationDraftDetail' ] } }])
  }

  talkBack(PsDetail: PresentationDraft) {
    this.selectedPresentationdraft = PsDetail;
  }

  paginate(page: number): void{
    if(this.sortedPagedItems.length == 0){
      this.setPage(page);
    }else{
      this.setSortedPage(page);
    }
  }

  setPage(page: number){
    this.currentPageNumber = page;
    this.pager = this.pagerService.getPager(this.conference.presentationDrafts.length, page);
    this.pagedItems = this.conference.presentationDrafts.slice(this.pager.startIndex, this.pager.endIndex+1);
  }

  setSortedPage(page: number){
    this.currentPageNumber = page;
    this.pager = this.pagerService.getPager(this.sortedPagedItems.length, page);
    this.pagedItems = this.sortedPagedItems.slice(this.pager.startIndex, this.pager.endIndex+1);
  }

}
