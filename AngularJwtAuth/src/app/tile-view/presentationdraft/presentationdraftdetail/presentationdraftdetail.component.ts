import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { Conference } from 'src/app/objects/conference/conference';
import { PsDetailService } from '../psDetail.service';
import { PresentationdraftService } from '../presentationdraft.service';

@Component({
  selector: 'app-presentationdraftdetail',
  templateUrl: './presentationdraftdetail.component.html',
  styleUrls: ['./presentationdraftdetail.component.css']
})
export class PresentationdraftdetailComponent implements OnInit {

  PsDetail: PresentationDraft;

  PsCompare : String;

  conferenceDetail: Conference;

  labels: String[] = [ "ACCEPTED", "DENIED", "RESERVED" ];

  constructor(private location: Location,
    private psDetailService: PsDetailService,
    private presentationDraftService: PresentationdraftService) { }

  ngOnInit() {
    this.PsDetail = this.psDetailService.selectedPresentationDraft;
    this.conferenceDetail = this.psDetailService.activeConference;
    this.PsCompare = JSON.stringify(this.psDetailService.selectedPresentationDraft);
  }

  goBack(event): void {
    if (event.target !== event.currentTarget) {
      return;
    }
  }

  changeLabel(value) {
    this.PsDetail.label = value;
  }

  changeCategory(value) {
    this.PsDetail.category = value;
  }

  updatePresentationDraft(PsDetail) {
    this.presentationDraftService.updatePresentationDraft(this.conferenceDetail.id, PsDetail).subscribe(PresentationDraft => console.log("Opslaan gelukt"));
    this.PsCompare = JSON.stringify(this.psDetailService.selectedPresentationDraft);
  }

  deletePresentationDraft(PsDetail) {
    let conf = confirm("Weet je zeker dat je de presentatie wilt verwijderen?");
    if (conf) {
      this.presentationDraftService.deletePresentationDraft(PsDetail).subscribe();
      this.location.back();
    }
  }

  downloadSinglePdf(conferenceDetail, PsDetail) {
    this.presentationDraftService.downloadSinglePdf(PsDetail, conferenceDetail).subscribe((response)=>{
      let blob = new Blob([response], { type: 'application/pdf' });
      var fileUrl = window.document.createElement('a');
      fileUrl.href = window.URL.createObjectURL(blob);
      fileUrl.download = 'PresentationDraft' + PsDetail.id + '.pdf';
      fileUrl.click();
    })
  }

  closeAndComparePresentationdraft(PsDetail) {
    if (this.PsCompare === JSON.stringify(PsDetail)) {
      console.log("Geen wijzigingen");
    } else {
      let conf = confirm("Er zijn wijzigingen, wil je deze opslaan?");
      if (conf) {
        this.updatePresentationDraft(PsDetail);
      }
    }
    this.location.back();
  } 
}
