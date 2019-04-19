import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';

import {PresentationDraft} from 'src/app/objects/presentation-draft';
import {PresentationDraftDetailService} from './presentation-draft-detail.service';
import {PresentationDraftService} from '../presentation-draft.service';
import {MatSnackBar} from "@angular/material";
import {DateFormatService} from "../../../services/date-format.service";

import { Conference } from 'src/app/objects/conference/conference';
import { MatDialog } from '@angular/material';
import { SaveDialog } from './savedialog.component';
import { DeleteDialog } from './deletedialog.component';
import { ConferenceService } from '../../conference/conference.service';
import { MailDialog } from './maildialog.component';

@Component({
  selector: 'app-presentationdraftdetail',
  templateUrl: './presentation-draft-detail.component.html',
  styleUrls: ['./presentation-draft-detail.component.css']
})

export class PresentationdraftdetailComponent implements OnInit {

  presentationDraftDetail: PresentationDraft;

  PsCompare: String;
  conferenceId: number;
  availableCategories: string[];
  numberOfDrafts: number;

  conferenceDetail: Conference;

  labels: String[] = ["ACCEPTED", "DENIED", "RESERVED"];

  constructor(private dialog: MatDialog,
    private location: Location,
    private psDetailService: PresentationDraftDetailService,
    private presentationDraftService: PresentationDraftService,
    private snackBar: MatSnackBar,
    private dateFormatService: DateFormatService) { }

  ngOnInit() {
    this.presentationDraftDetail = this.psDetailService.selectedPresentationDraft;
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
    this.presentationDraftDetail.label = value;
  }

  changeCategory(value) {
    this.presentationDraftDetail.category = value;
  }

  updatePresentationDraft(PsDetail) {
    let validConferenceId = +(this.conferenceId == null ? localStorage.getItem("conferenceId") : this.conferenceId);
    this.presentationDraftService.updatePresentationDraft(validConferenceId, PsDetail)
      .subscribe(
        presentationDraft => this.presentationDraftDetail = presentationDraft,
        error => this.showFail(error),
        () => this.showSucces());
  }

  showSucces() {
    this.snackBar.open("Succes!", "Wijzingen zijn opgeslagen", {
      duration: 3500,
      panelClass: ['snackbar-color']
    });
  }
  
  showFail(error: Error) {
    this.snackBar.open("Opslaan niet gelukt", error.name, {
      duration: 3500,
      panelClass: ['snackbar-color']
    });
  }

  public showCorrectDate(date: Date) {
    return this.dateFormatService.showCorrectDate(date);
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
      height: '180px',
      data: {
        number: this.conferenceId,
        presentationDraft: this.presentationDraftDetail
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("dialog closed");
    });
  }

  openDeleteDialog(): void {
    const dialogRef = this.dialog.open(DeleteDialog, {
      width: '270px',
      height: '180px',
      data: {
        presentationDraft: this.presentationDraftDetail
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("dialog closed");
    });
  }

  openMailDialog(): void {
    const dialogRef = this.dialog.open(MailDialog, {
      width: '400px',
      height: '600px',
      data: {
        presentationDraft: this.presentationDraftDetail
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("dialog closed");
    });
  }
}