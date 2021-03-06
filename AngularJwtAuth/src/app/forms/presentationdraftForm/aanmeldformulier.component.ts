import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormControl } from '@angular/forms';
import { Applicant } from '../../objects/applicant';
import { draftAanmeldService } from '../presentationdraftForm/aanmeldformulier.service';
import { PresentationDraftApplicant } from '../../objects/presentationDraftApplicant';
import { Conference } from '../../objects/conference/conference';
import { MatDialog, MatDialogRef, MatDialogConfig, MatSnackBar } from '@angular/material';
import { DialogWindowComponent } from '../dialogWindow/dialogWindow.component';
import {dialogWindowService} from '../dialogWindow/dialogWindow.service';

@Component({
  selector: 'app-aanmeldformulier',
  templateUrl: './aanmeldformulier.component.html',
  styleUrls: ['./aanmeldformulier.component.css']
})
export class AanmeldformulierComponent implements OnInit {
  public title = 'Aanmeldformulier';
  public isZichtbaar = true;
  public feedbackBerichten = ['Voer a.u.b. een naam in die 3 tekens of langer is.', 'Voer a.u.b. een geldig e-mailadres in.', 'Voer a.u.b. een geldig onderwerp in.', 'Voeg a.u.b. een beschrijving toe.'];
  public numberCohosts: number;
  public conferenceID: number;
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

  public expandOptions: boolean;

  presentationdraftForm: FormGroup;

  fileNameDialogRef: MatDialogRef<AanmeldformulierDialogComponent>;

  constructor(private draftAanmeldService: draftAanmeldService, public dialog: MatDialog, private snackBar: MatSnackBar, private dialogwindowservice: dialogWindowService) {
  }

  ngOnInit(): void {
    this.submittable = false;
    this.route.params.subscribe(params => {
        this.conferenceID = +params['id'];       
        console.log(this.conferenceID);
      });
    this.numberCohosts = 0;
    this.presentationDraftApplicant = new PresentationDraftApplicant();

    this.tableApplicants = [];
    this.iconInit();

    this.presentationdraftForm = new FormGroup({
      'indiener': new FormGroup({
        'name': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'email': new FormControl(null, [Validators.email]),
        'occupation': new FormControl(null, []),
        'gender': new FormControl(null, []),
        'requests': new FormControl(null, [])
      }),
      'presentationDraft': new FormGroup({
        'subject': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'summary': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'type': new FormControl(null, []),
        'category': new FormControl(null, []),
        'duration': new FormControl(null, [])
      })
    });
  }

  iconInit(){
    this.iconNameFeedback = "sentiment_dissatisfied";
    this.iconEmailFeedback = "sentiment_dissatisfied";
    this.iconSubjectFeedback = "sentiment_dissatisfied";
    this.iconSummaryFeedback = "sentiment_dissatisfied";
  }

  get name() {
    return this.presentationdraftForm.get('indiener.name');
  }

  get email() {
    return this.presentationdraftForm.get('indiener.email');
  }

  get occupation() {
    return this.presentationdraftForm.get('indiener.occupation');
  }

  get gender() {
    return this.presentationdraftForm.get('indiener.gender');
  }

  get requests() {
    return this.presentationdraftForm.get('indiener.requests');
  }

  get subject() {
    return this.presentationdraftForm.get('presentationDraft.subject');
  }

  get summary() {
    return this.presentationdraftForm.get('presentationDraft.summary');
  }

  get type() {
    return this.presentationdraftForm.get('presentationDraft.type');
  }

  get category() {
    return this.presentationdraftForm.get('presentationDraft.category');
  }

  get duration() {
    return this.presentationdraftForm.get('presentationDraft.duration');
  }

  checkNumberCohosts() {
    return this.numberCohosts;
  }

  setNumberCohosts(event: any) {
    this.numberCohosts = event.target.value;
  }

  setDuration(event: any) {
    this.duration.setValue = event.target.value;
  }

  getIconFeedback(event: any) {
    let formcontrolname = event.target.getAttribute('formcontrolname');

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

  popUpAddCohosts() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      popUpTitel: 'Mede-presentator aanmelden',
      popUpInhoud: '',
      popUpType: 0
    }
    
    this.dialog.open(DialogWindowComponent, dialogConfig);
  }

  getApplicants() {
    for (const applicant of this.dialogwindowservice.savedApplicants){
      applicant.requests = 'Ik ben een cohost';
    }
    return this.dialogwindowservice.savedApplicants;
  }

  deleteCohost(applicant: Applicant){
    this.getApplicants().splice(this.tableApplicants.indexOf(applicant), 1);
  }

  prepareApplicants() {
    this.presentationDraftApplicant.applicants = this.getApplicants();
    const indiener = Object.assign(this.presentationdraftForm.get('indiener').value);
    this.presentationDraftApplicant.applicants.unshift(indiener);
  }
    
  submit() {
    this.prepareApplicants();
    this.presentationDraftApplicant.presentationDraft = Object.assign(this.presentationdraftForm.get('presentationDraft').value);
    this.draftAanmeldService.postPresentationDraftApplicant(this.presentationDraftApplicant, this.conferenceID)
      .subscribe(presentationDraftApplicant => console.log(presentationDraftApplicant),
        error => function (error: Error) {
          alert(error.message);
        }, function () {
        });

    this.submitted = true;
    this.expandOptions = false;
    this.dialogwindowservice.savedApplicants = [];
    this.openSnackBar();
  }

  openSnackBar() {
    this.presentationdraftForm.reset();
    this.iconInit();
    this.tableApplicants = [];
    this.snackBar.openFromComponent(AanmeldformulierFeedbackComponent, {
      duration: 5000,
      panelClass: ['snackbar-color']
    });
  }
}

@Component({
  selector: 'aanmeldingsformulier-feedback-snack',
  template: `
  <span>De aanmelding is verstuurd. Bedankt voor het indienen!</span>
`,
})
export class AanmeldformulierFeedbackComponent {}
