import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';

import {PresentationDraft} from 'src/app/objects/presentation-draft';
import {PresentationDraftDetailService} from './presentation-draft-detail.service';
import {PresentationDraftService} from '../presentation-draft.service';
import {MatSnackBar} from "@angular/material";

@Component({

  selector: 'app-presentationdraftdetail',
  templateUrl: './presentation-draft-detail.component.html',
  styleUrls: ['./presentation-draft-detail.component.css']
})

export class PresentationDraftDetailComponent implements OnInit {

  public presentationDraftDetail: PresentationDraft;
  public categories: string[];
  private presentationDraftCompare;

  private conferenceId: number;

  constructor(private location: Location,
              private presentationDraftDetailService: PresentationDraftDetailService,
              private presentationDraftService: PresentationDraftService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    if (!localStorage.getItem("psDetail")) {
      this.presentationDraftDetail = this.presentationDraftDetailService.selectedPresentationDraft;
      this.presentationDraftCompare = this.presentationDraftDetailService.selectedPresentationDraft;
      this.saveToStorage(this.presentationDraftDetail, "psDetail");

      this.conferenceId = this.presentationDraftDetailService.activeConferenceId;
      localStorage.setItem("conferenceId", "" + this.conferenceId);

      this.categories = this.presentationDraftDetailService.categories;
      this.saveToStorage(this.categories, "categories");
    } else {
      this.presentationDraftDetail = JSON.parse(localStorage.getItem("psDetail"));
      this.presentationDraftCompare = JSON.parse(localStorage.getItem("psDetail"));
      this.categories = JSON.parse(localStorage.getItem("categories"));
      this.conferenceId = JSON.parse(localStorage.getItem("conferenceId"));
    }
  }

  saveToStorage(item: object, key: string) {
    localStorage.setItem(key, JSON.stringify(item))
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
    // this.popup();
  }

  showSucces() {
    this.snackBar.open("Succes!", "The presentation draft has been saved", {
      duration: 2000
    });
  }

  showFail(error: Error) {
    this.snackBar.open("Could not save", error.name, {
      duration: 2000
    });
  }

  deletePresentationDraft(PsDetail) {
    let conf = confirm("Weet je zeker dat je de presentatie wilt verwijderen?");
    if (conf) {
      this.presentationDraftService.deletePresentationDraft(PsDetail).subscribe();
      this.location.back();
    }
  }

  public showCorrectDate(date: Date) {
    let arrayOfDate: string[] = String(date).split(",");
    let formattedDate: string = "";
    for (let i = 2; i >= 0; i--) {
      if (arrayOfDate[i] !== ",") {
        if (i == 0) {
          +arrayOfDate[i] < 10 ? formattedDate += ("0" + arrayOfDate[i]) : formattedDate += arrayOfDate[i];
        } else {
          // formattedDate +=correctDate + "-";
          +arrayOfDate[i] < 10 ? formattedDate += ("0" + arrayOfDate[i] + "-") : formattedDate += arrayOfDate[i] + "-";
        }
      }
    }
    return formattedDate;
  }

  downloadSinglePdf(PsDetail) {
    this.presentationDraftService.downloadSinglePdf(PsDetail, this.conferenceId).subscribe((response) => {
      let blob = new Blob([response], {type: 'application/pdf'});
      var fileUrl = window.document.createElement('a');
      fileUrl.href = window.URL.createObjectURL(blob);
      fileUrl.download = 'PresentationDraft' + PsDetail.id + '.pdf';
      fileUrl.click();
    })
  }

  closeAndComparePresentationdraft(PsDetail) {
    if (JSON.stringify(this.presentationDraftCompare) === JSON.stringify(PsDetail)) {
      localStorage.clear();
      console.log("niks gewijzigd");
      console.log(this.presentationDraftCompare.summary);
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
