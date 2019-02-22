import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormControl } from '@angular/forms';
import { Applicant } from '../../objects/applicant';
import { draftAanmeldService } from '../presentationdraftForm/aanmeldformulier.service';
import { PresentationDraftApplicant } from '../../objects/presentationDraftApplicant';
import { Conference } from '../../objects/conference/conference';
import { MatDialog, MatDialogActions, MatDialogRef, MatDialogConfig } from '@angular/material';
import { DialogWindowComponent } from '../dialogWindow/dialogWindow.component';


@Component({
  selector: 'app-aanmeldformulierMaterial',
  templateUrl: './aanmeldformulierMaterial.component.html',
  styleUrls: ['./aanmeldformulierMaterial.component.css']
})
export class AanmeldformulierMaterialComponent implements OnInit {
  public title = 'Aanmeldformulier';
  public isZichtbaar = true;
  public feedbackBerichten = ['Voer a.u.b. een naam in die 3 tekens of langer is.', 'Voer a.u.b. een geldig e-mailadres in.', 'Voer a.u.b. een geldig onderwerp in.', 'Voeg a.u.b. een beschrijving toe.'];
  public numberCohosts: number;
  public conference: Conference;
  public tableApplicants: Applicant[];

  public presentationDraftApplicant: PresentationDraftApplicant;

  public presentationDraft: FormGroup;
  public applicants: FormGroup;
  public validFields: boolean[] = [false, false, false, false];
  public submittable: boolean;
  public submitted: boolean = false;
  public iconUnhappy = "sentiment_very_dissatisfied";
  public iconHappy = "sentiment_very_satisfied";

  public iconNameFeedback: string;
  public iconEmailFeedback: string;
  public iconSubjectFeedback: string;
  public iconSummaryFeedback: string;

  presentationdraftForm: FormGroup;

  fileNameDialogRef: MatDialogRef<DialogWindowComponent>;

  constructor(private draftAanmeldService: draftAanmeldService, public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.tableApplicants = [];
    this.iconNameFeedback = "sentiment_dissatisfied";
    this.iconEmailFeedback = "sentiment_dissatisfied";
    this.iconSubjectFeedback = "sentiment_dissatisfied";
    this.iconSummaryFeedback = "sentiment_dissatisfied";

    this.presentationdraftForm = new FormGroup({
      'indiener': new FormGroup({
        'name': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'email': new FormControl(null, [Validators.email]),
        'occupation': new FormControl(null, []),
        'gender': new FormControl(null, []),
        'requests': new FormControl(null, [])
      }),
      // 'cohost1': new FormGroup({
      //   'name': new FormControl(null, [Validators.required, Validators.minLength(3)]),
      //   'email': new FormControl(null, [Validators.email])
      // }),
      // 'cohost2': new FormGroup({
      //   'name': new FormControl(null, [Validators.required, Validators.minLength(3)]),
      //   'email': new FormControl(null, [Validators.email])
      // }),
      // 'cohost3': new FormGroup({
      //   'name': new FormControl(null, [Validators.required, Validators.minLength(3)]),
      //   'email': new FormControl(null, [Validators.email])
      // }),
      'presentationDraft': new FormGroup({
        'subject': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'summary': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'type': new FormControl(null, []),
        'category': new FormControl(null, []),
        'duration': new FormControl(null, [])
      })
    });
  }

  getIconFeedback(event: any){
    let formcontrolname = event.target.getAttribute('formcontrolname');
    console.log(event.target);

    switch(formcontrolname){
      case "name":
      this.iconNameFeedback = (this.presentationdraftForm.get('indiener').get('name').invalid == true) ? this.iconUnhappy : this.iconHappy;
      break;

      case "email":
      this.iconEmailFeedback = (this.presentationdraftForm.get('indiener').get('email').invalid == true) ? this.iconUnhappy : this.iconHappy;
      break;

      case "subject":
      this.iconSubjectFeedback = (this.presentationdraftForm.get('presentationDraft').get('subject').invalid == true) ? this.iconUnhappy : this.iconHappy;
      break;

      case "summary":
      this.iconSummaryFeedback = (this.presentationdraftForm.get('presentationDraft').get('summary').invalid == true) ? this.iconUnhappy : this.iconHappy;
      break;
    }
  }

  popUpAddCohosts(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      popUpTitel: 'Mede-presentator aanmelden',
      popUpInhoud: ''
    }
    
    const dialogRef = this.dialog.open(DialogWindowComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => {
        console.log("Dialog output: ", data);
        this.maakCohost([data.cohostNaam, data.cohostEmail]);
      }
    );

    console.log("Werkt");
  }

  maakCohost(applicantInfo: string[]){
    let applicant = new Applicant();
    applicant.name = applicantInfo[0];
    applicant.email = applicantInfo[1];
    applicant.requests = 'Ik ben een cohost';
    this.tableApplicants.push(applicant);
    console.log(this.tableApplicants);
  }

  deleteCohost(applicant: Applicant){
    this.tableApplicants.splice(this.tableApplicants.indexOf(applicant), 1);
    console.log(this.tableApplicants);
  }
}