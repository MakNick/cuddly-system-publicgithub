import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { viewParentEl } from '@angular/core/src/view/util';
import { Conference } from 'src/app/objects/conference/conference';
import { PsDetailService } from '../psDetail.service';
import { PresentationdraftService } from '../presentationdraft.service';
import { AngularWaitBarrier } from 'blocking-proxy/built/lib/angular_wait_barrier';

@Component({

  selector: 'app-presentationdraftdetail',
  templateUrl: './presentationdraftdetail.component.html',
  styleUrls: ['./presentationdraftdetail.component.css']
})
export class PresentationdraftdetailComponent implements OnInit {

  PsDetail: PresentationDraft;

  PsCompare = this.psDetailService.selectedPresentationDraft;

  conferenceDetail: Conference;

  constructor(private location: Location,
    private psDetailService: PsDetailService,
    private presentationDraftService: PresentationdraftService) { }

  ngOnInit() {
    this.PsDetail = this.psDetailService.selectedPresentationDraft;
    this.conferenceDetail = this.psDetailService.activeConference;
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

  jojoganster() {
    console.log(this.PsDetail.summary);
    console.log(this.PsCompare.summary);
  }

  updatePresentationDraft(PsDetail) {
    this.presentationDraftService.updatePresentationDraft(this.conferenceDetail.id, PsDetail).subscribe(PresentationDraft => console.log("Opslaan gelukt"));
    this.popup();
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
    if (JSON.stringify(this.PsCompare) === JSON.stringify(PsDetail)) {
      console.log("niks gewijzigd");
      console.log(this.PsCompare.summary);
      console.log(PsDetail.summary);
    } else {
      console.log("wijzigingen");
    }
    this.location.back();
  }

  popup() {
    var popup = document.getElementById("myPopup");
    popup.classList.toggle("show");
  }
}
