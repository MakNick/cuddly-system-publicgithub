import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogActions, MAT_DIALOG_DATA, MatDialogTitle, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';


export interface DialogData{
  popUpTitel: string;
  popUpInhoud: string;
}

@Component({
  selector: 'app-dialogwindow',
  templateUrl: './dialogWindow.component.html',
  styleUrls: ['./dialogWindow.component.css']
})
export class DialogWindowComponent implements OnInit {
cohostForm: FormGroup;
description: string;
cohostNaam: string;
cohostEmail: string;

popUpTitel: MatDialogTitle;
popUpInhoud: any;

public iconUnhappy = "sentiment_very_dissatisfied";
public iconHappy = "sentiment_very_satisfied";

public iconNameFeedback: string;
public iconEmailFeedback: string;


  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<DialogWindowComponent>, @Inject(MAT_DIALOG_DATA) private data) {
    this.description = data.description;
  }

  ngOnInit(): void {
    this.iconNameFeedback = "sentiment_dissatisfied";
    this.iconEmailFeedback = "sentiment_dissatisfied";

    this.popUpTitel = this.data.popUpTitel;
    this.popUpInhoud = this.data.popUpInhoud;

    this.cohostForm = new FormGroup({
      'cohostNaam': new FormControl(null, [Validators.required, Validators.minLength(3)]),
      'cohostEmail': new FormControl(null, [Validators.email]),
    });
  }

  save(){
    this.dialogRef.close(this.cohostForm.value);
  }

  close(){
    this.dialogRef.close();
  }

  getIconFeedback(event: any){
    let formcontrolname = event.target.getAttribute('formcontrolname');
    console.log(event.target);

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

