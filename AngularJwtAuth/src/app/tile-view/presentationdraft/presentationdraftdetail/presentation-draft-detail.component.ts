import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { Conference } from 'src/app/objects/conference/conference';
import { PresentationDraftDetailService } from './presentation-draft-detail.service';
import { PresentationDraftService } from '../presentation-draft.service';
import { MatDialog } from '@angular/material';
import { SaveDialog } from './savedialog.component';
import { DeleteDialog } from './deletedialog.component';
import { ConferenceService } from '../../conference/conference.service';

@Component({
  selector: 'app-presentationdraftdetail',
  templateUrl: './presentation-draft-detail.component.html',
  styleUrls: ['./presentation-draft-detail.component.css']
})

export class PresentationdraftdetailComponent implements OnInit {

  PsDetail: PresentationDraft;

  PsCompare: String;

  conferenceId: number;
  availableCategories: string[];
  numberOfDrafts: number;

  conferenceDetail: Conference;

  labels: String[] = ["ACCEPTED", "DENIED", "RESERVED"];

  constructor(private dialog: MatDialog,
    private location: Location, private conferenceService: ConferenceService,
    private psDetailService: PresentationDraftDetailService,
    private presentationDraftService: PresentationDraftService) { }

  ngOnInit() {
    this.PsDetail = this.psDetailService.selectedPresentationDraft;
    this.PsCompare = JSON.stringify(this.psDetailService.selectedPresentationDraft);
    this.conferenceId = this.psDetailService.activeConferenceId;
    this.availableCategories = this.psDetailService.categories;
    this.numberOfDrafts = this.psDetailService.numberOfDrafts;
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
    this.presentationDraftService.updatePresentationDraft(this.conferenceId, PsDetail).subscribe();
    this.PsCompare = JSON.stringify(this.psDetailService.selectedPresentationDraft);
  }

  deletePresentationDraft() {
    this.openDeleteDialog();
  }

  downloadSinglePdf(PsDetail) {
    this.presentationDraftService.downloadSinglePdf(PsDetail, this.conferenceId).subscribe((response) => {
      let blob = new Blob([response], { type: 'application/pdf' });
      var fileUrl = window.document.createElement('a');
      fileUrl.href = window.URL.createObjectURL(blob);
      fileUrl.download = 'PresentationDraft' + PsDetail.id + '.pdf';
      fileUrl.click();
    })
  }

  closeAndComparePresentationdraft(PsDetail) {
    if (this.PsCompare === JSON.stringify(PsDetail)) {
      this.location.back();
    } else {
      this.openSaveDialog();
    }
  }

  openSaveDialog(): void {
    const dialogRef = this.dialog.open(SaveDialog, {
      width: '250px',
      height: '150px',
      data: {
        number: this.conferenceId,
        presentationDraft: this.PsDetail
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("dialog closed");
    });
  }

  openDeleteDialog(): void {
    const dialogRef = this.dialog.open(DeleteDialog, {
      width: '270px',
      height: '150px',
      data: {
        presentationDraft: this.PsDetail
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("dialog closed");
    });
  }
}