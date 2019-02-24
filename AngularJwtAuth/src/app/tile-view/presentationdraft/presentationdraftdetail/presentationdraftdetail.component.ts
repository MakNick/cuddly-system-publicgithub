import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { Conference } from 'src/app/objects/conference/conference';
import { PsDetailService } from '../psDetail.service';
import { PresentationdraftService } from '../presentationdraft.service';
import { MatDialog } from '@angular/material';
import { SaveDialog } from './savedialog.component';
import { DeleteDialog } from './deletedialog.component';

@Component({
  selector: 'app-presentationdraftdetail',
  templateUrl: './presentationdraftdetail.component.html',
  styleUrls: ['./presentationdraftdetail.component.css']
})

export class PresentationdraftdetailComponent implements OnInit {

  PsDetail: PresentationDraft;

  PsCompare: String;

  conferenceDetail: Conference;

  labels: String[] = ["ACCEPTED", "DENIED", "RESERVED"];

  constructor(private dialog: MatDialog,
    private location: Location,
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

  deletePresentationDraft() {
    this.openDeleteDialog();
  }

  downloadSinglePdf(conferenceDetail, PsDetail) {
    this.presentationDraftService.downloadSinglePdf(PsDetail, conferenceDetail).subscribe((response) => {
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
        conference: this.conferenceDetail,
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