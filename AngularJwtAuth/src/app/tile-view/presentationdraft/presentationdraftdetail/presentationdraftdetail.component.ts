import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { PresentationDraftDetailService } from './presentation-draft-detail.service';
import { PresentationDraftService } from '../presentation-draft.service';

@Component({

  selector: 'app-presentationdraftdetail',
  templateUrl: './presentationdraftdetail.component.html',
  styleUrls: ['./presentationdraftdetail.component.css']
})

export class PresentationdraftdetailComponent implements OnInit {

  PsDetail: PresentationDraft;

  PsCompare = this.psDetailService.selectedPresentationDraft;

  conferenceId: number;

  constructor(private location: Location,
    private psDetailService: PresentationDraftDetailService,
    private presentationDraftService: PresentationDraftService) { }

  ngOnInit() {
    this.PsDetail = this.psDetailService.selectedPresentationDraft;
    this.conferenceId = this.psDetailService.activeConferenceId;
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
    this.presentationDraftService.updatePresentationDraft(this.conferenceId, PsDetail).subscribe(PresentationDraft => console.log("Opslaan gelukt"));
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
