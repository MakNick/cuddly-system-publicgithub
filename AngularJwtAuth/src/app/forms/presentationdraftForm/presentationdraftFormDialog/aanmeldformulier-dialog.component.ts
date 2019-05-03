import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogActions, MAT_DIALOG_DATA, MatDialogTitle, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import {dialogWindowService} from './dialogWindow.service';


export interface DialogData {
  popUpTitel: string;
  popUpInhoud: string;
  popupType: string;
}

@Component({
  selector: 'app-aanmeldformulier-dialog',
  templateUrl: './aanmeldformulier-dialog.component.html',
  styleUrls: ['./aanmeldformulier-dialog.component.css']
})
export class AanmeldformulierDialogComponent implements OnInit {
cohostForm: FormGroup;
description: string;
cohostNaam: string;
cohostEmail: string;

form: FormGroup;

popUpTitel: MatDialogTitle;
popUpInhoud: any;
popUpType: number;

public iconUnhappy = "sentiment_very_dissatisfied";
public iconHappy = "sentiment_very_satisfied";

public iconNameFeedback: string;
public iconEmailFeedback: string;

  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<DialogWindowComponent>, @Inject(MAT_DIALOG_DATA) private data, private dialogwindowservice: dialogWindowService) {
    this.description = data.description;
  }

  ngOnInit(): void {
    this.popUpType = this.data.popUpType;
    this.iconNameFeedback = "sentiment_dissatisfied";
    this.iconEmailFeedback = "sentiment_dissatisfied";

    this.popUpTitel = this.data.popUpTitel;
    this.popUpInhoud = this.data.popUpInhoud;
    this.popUpType = this.data.popUpType;

    this.cohostForm = new FormGroup({
      'name': new FormControl(null, [Validators.required, Validators.minLength(3)]),
      'email': new FormControl(null, [Validators.email]),
    });

    this.categorieForm = new FormGroup({
      'category': new FormControl(null, [Validators.required, Validators.minLength(1)])
    });

    this.stageForm = new FormGroup({
      'name': new FormControl(null, [Validators.required, Validators.minLength(1)])
    });

    this.forms = [this.cohostForm, this.categorieForm, this.stageForm];
  }

  save(value) {
    switch (value) {
      case 0:
        return this.dialogwindowservice.savedApplicants.push(this.forms[this.popUpType].value);
      case 1:
        return this.dialogwindowservice.savedCategories.push(this.forms[this.popUpType].value);
      case 2:
        return this.dialogwindowservice.savedStages.push(this.forms[this.popUpType].value);
    }
  }

  close() {
    this.dialogRef.close();
  }

  getPopUpType() {
    return this.popUpType;
  }

  getIconFeedback(event: any) {
    let formcontrolname = event.target.getAttribute('formcontrolname');

    switch(formcontrolname) {
      case 'name':
      this.iconNameFeedback = (this.cohostForm.get('name').invalid == true) ? this.iconUnhappy : this.iconHappy;
      break;

      case 'email':
      this.iconEmailFeedback = (this.cohostForm.get('email').invalid == true) ? this.iconUnhappy : this.iconHappy;
      break;
    }
  } 
}

