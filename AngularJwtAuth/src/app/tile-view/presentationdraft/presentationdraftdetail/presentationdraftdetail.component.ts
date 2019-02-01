import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { viewParentEl } from '@angular/core/src/view/util';
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
    console.log(this.PsDetail);
  }

  updatePresentationDraft(PsDetail) {
    this.presentationDraftService.updatePresentationDraft(this.conferenceDetail.id, PsDetail).subscribe(PresentationDraft => alert("Opslaan gelukt"));
  }

  deletePresentationDraft(PsDetail) {
    this.presentationDraftService.updatePresentationDraft(this.conferenceDetail.id, PsDetail).subscribe(PresentationDraft => alert("Opslaan gelukt"));

  }

  downloadSinglePdf(conferenceId, presentationDraftId) {
    let xhr = new XMLHttpRequest();
    xhr.responseType = 'arraybuffer';
    xhr.open("GET", "http://localhost:8082/api/" + conferenceId + "/download/pdf/" + presentationDraftId, true);
    xhr.onload = function () {
      console.log(xhr.response);
      var res = xhr.response;

      let blob = new Blob([new Uint8Array(res)]);

      console.log(blob);
      var a = window.document.createElement('a');
      a.href = window.URL.createObjectURL(blob);
      a.download = 'PresentationDraft' + presentationDraftId + '.pdf';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    }
    xhr.send();
  }
}
