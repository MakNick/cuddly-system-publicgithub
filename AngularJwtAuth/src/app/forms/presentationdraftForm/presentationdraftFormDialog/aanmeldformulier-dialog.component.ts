import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogActions, MAT_DIALOG_DATA, MatDialogTitle, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';

export interface DialogData{
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

  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<AanmeldformulierDialogComponent>, @Inject(MAT_DIALOG_DATA) private data) {
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
      'cohostNaam': new FormControl(null, [Validators.required, Validators.minLength(3)]),
      'cohostEmail': new FormControl(null, [Validators.email])
    });
    this.form = this.cohostForm;
  }

  save(){
    this.dialogRef.close(this.form.value);
  }

  close(){
    this.dialogRef.close();
  }

  getPopUpType(){
    return this.popUpType;
  }

  getIconFeedback(event: any){
    let formcontrolname = event.target.getAttribute('formcontrolname');

    switch(formcontrolname){
      case "cohostNaam":
      this.iconNameFeedback = (this.cohostForm.get('cohostNaam').invalid == true) ? this.iconUnhappy : this.iconHappy;
      break;

      case "cohostEmail":
      this.iconEmailFeedback = (this.cohostForm.get('cohostEmail').invalid == true) ? this.iconUnhappy : this.iconHappy;
      break;
    }
  } 
}

