import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogActions, MAT_DIALOG_DATA, MatDialogTitle, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';


export interface DialogData{
  popUpTitel: string;
  popUpInhoud: string;
}

@Component({
  selector: 'app-conferenceformulier-dialog',
  templateUrl: './conferenceForm-dialog.component.html',
  styleUrls: ['./conferenceForm-dialog.component.css']
})
export class ConferenceFormDialogComponent implements OnInit {
categorieForm: FormGroup;
stageForm: FormGroup;
description: string;

forms: FormGroup[];

popUpTitel: MatDialogTitle;
popUpInhoud: any;
popUpType: number;

public iconUnhappy = "sentiment_very_dissatisfied";
public iconHappy = "sentiment_very_satisfied";

public iconNameFeedback: string;
public iconEmailFeedback: string;

  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<ConferenceFormDialogComponent>, @Inject(MAT_DIALOG_DATA) private data) {
    this.description = data.description;
  }

  ngOnInit(): void {
    this.iconNameFeedback = "sentiment_dissatisfied";
    this.iconEmailFeedback = "sentiment_dissatisfied";

    this.popUpTitel = this.data.popUpTitel;
    this.popUpInhoud = this.data.popUpInhoud;
    this.popUpType = this.data.popUpType;

    this.categorieForm = new FormGroup({
      'category': new FormControl(null, [Validators.required, Validators.minLength(1)])
    });

    this.stageForm = new FormGroup({
      'stage': new FormControl(null, [Validators.required, Validators.minLength(1)])
    });

    this.forms = [this.categorieForm, this.stageForm];
  }

  save(){
    this.dialogRef.close(this.forms[this.popUpType].value);
  }

  close(){
    this.dialogRef.close();
  }

  getPopUpType(){
    return this.popUpType;
  }
 
}

