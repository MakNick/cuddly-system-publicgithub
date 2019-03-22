import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup, EmailValidator, FormArray } from '@angular/forms';
import { forbiddenConferenceNameValidator } from './shared/conferenceName.validator';
import { ConferenceDateValidator } from './shared/conferenceDate.validator';
import { ConferenceService } from './conferenceForm.service';
import { Conference } from './conferenceForm';
import { MatDialogConfig, MatDialog, MatSnackBar } from '@angular/material';
import { DialogWindowComponent } from '../dialogWindow/dialogWindow.component';
import {dialogWindowService} from '../dialogWindow/dialogWindow.service';


@Component({
  selector: 'conferenceForm',
  templateUrl: './conferenceForm.component.html',
  styleUrls: ['./conferenceForm.component.css']
})
export class ConferenceFormComponent implements OnInit {

  conferenceForm: FormGroup;
  conferences: Conference[] = [];
  categories: string[] = [];
  stages: string[] = [];
  defaultDateTime: string = "2099-01-01T01:00";
  dateOfToday = new Date(Date.now());
  dateOfTomorrow = new Date(this.dateOfToday.setDate(this.dateOfToday.getDate() + 1));
  selectedDate = new Date();
  
  get alternateCategoryMethod() {
    return this.conferenceForm.get('categories') as FormArray;
  }
  
  addAlternateCategory() {
    this.alternateCategoryMethod.push(this.fb.control(''));
  }
  
  removeAlternateCategory(i: number) {
    this.alternateCategoryMethod.removeAt(i);
  }
  
  get name() {
    return this.conferenceForm.get('name');
  }

  get stageName() {
    return this.conferenceForm.get('stage');
  }

  get alternateStagesMethod() {
    return this.conferenceForm.get('stages') as FormArray;
  }

  addAlternateStages() {
    this.alternateStagesMethod.push(this.fb.control(''));
  }

  removeAlternateStages(i: number) {
    this.alternateStagesMethod.removeAt(i);
  }

  get category() {
    return this.conferenceForm.get('category');
  }

  minDateEndDate(event) {
    event.target.min = this.selectedDate;
  }

  maxDateDeadlineDate(event) {
    let date: Date = new Date(this.selectedDate);
    let validDate = new Date(date.setDate(date.getDate() - 1));
    let validMonth = (validDate.getMonth() + 1) < 10 ? "0" + (validDate.getMonth() + 1) : (validDate.getMonth() + 1);
    let validDay = validDate.getDate() < 10 ? "0" + validDate.getDate() : validDate.getDate();
    event.target.max = validDate.getFullYear() + "-" + validMonth + "-" + validDay;
  }

  minTimeEndDate(event) {
    // event.target.min = this.selectedTime;
  }

  constructor(private fb: FormBuilder, private conferenceService: ConferenceService, private dialog: MatDialog, private snackBar: MatSnackBar, private dialogwindowservice: dialogWindowService) { }

  ngOnInit() {
    this.conferenceForm = this.fb.group({
      'name': [null, [Validators.required, Validators.minLength(3), forbiddenConferenceNameValidator(/javiel/)]],
      'startDate': [{ disabled: true, value: '' }],
      'startTime': [null],
      'endDate': [{ disabled: true, value: '' }],
      'endTime': [null],
      'deadlineDate': [{ disabled: true, value: '' }],
      'deadlineTime': [null],
      'stages': this.fb.array([]),
      'categories': this.fb.array([])
    },
      { validator: ConferenceDateValidator });
  }

  createConference() {
    let conference: Conference = this.conferenceForm.value;
    if (this.conferenceForm.get('startDate') != null) {
      if (this.conferenceForm.get('startDate').value == "" || this.conferenceForm.get('startTime').value == "") {
        conference.startDate = this.defaultDateTime;
      } else {
        conference.startDate = this.conferenceForm.get('startDate').value + "T" + this.conferenceForm.get('startTime').value;
      }
    }
    if (this.conferenceForm.get('endDate') != null) {
      if (this.conferenceForm.get('endDate').value == "" || this.conferenceForm.get('endTime').value == "") {
        conference.endDate = this.defaultDateTime;
      } else {
        conference.endDate = this.conferenceForm.get('endDate').value + "T" + this.conferenceForm.get('endTime').value;
      }
    }
    if (this.conferenceForm.get('deadlineDate') != null) {
      if (this.conferenceForm.get('deadlineDate').value == "" || this.conferenceForm.get('deadlineTime').value == "") {
        conference.deadlinePresentationDraft = this.defaultDateTime;
      } else {
        conference.deadlinePresentationDraft = this.conferenceForm.get('deadlineDate').value + "T" + this.conferenceForm.get('deadlineTime').value;
      }
    }
    console.log(conference);
    this.addConference(conference);
  }

  addConference(conference) {
    this.conferenceService.postConference(conference)
      .subscribe(conference => this.conferences.push(conference));
  }

  getConferences() {
    this.conferenceService.getConferences().subscribe(conference => this.conferences = conference);
  }

  deleteConference(id: number) {
    this.conferenceService.deleteConference(id).subscribe(conference => this.getConferences());
  }

  popUpAddCategories(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      popUpTitel: 'Categorie toevoegen',
      popUpInhoud: '',
      popUpType: 1
    }
    
    const dialogRef = this.dialog.open(DialogWindowComponent, dialogConfig);
  }

  getCategories(){
    this.categories = this.dialogwindowservice.savedCategories;
    return this.dialogwindowservice.savedCategories;
  }

  deleteCategory(x: string) {
    let category = x;
    this.dialogwindowservice.savedCategories.splice(this.dialogwindowservice.savedCategories.indexOf(category), 1);
    this.categories = this.dialogwindowservice.savedCategories;
  }

  popUpAddStages(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      popUpTitel: 'Stage toevoegen',
      popUpInhoud: '',
      popUpType: 2
    }
    
    this.dialog.open(DialogWindowComponent, dialogConfig);
  }

  getStages() {
    this.stages = this.dialogwindowservice.savedStages;
    return this.dialogwindowservice.savedStages;
  }

  deleteStage(x: string) {
    let stage = x;
    this.dialogwindowservice.savedStages.splice(this.dialogwindowservice.savedStages.indexOf(stage), 1);
    this.stages = this.dialogwindowservice.savedStages;
  }
 
  submit() {
    this.createConference();
    this.openSnackBar();
}

openSnackBar() {
  this.conferenceForm.reset();
  this.categories = [];
  this.stages = [];
  this.snackBar.openFromComponent(AanmeldformulierConferenceFeedbackComponent, {
    duration: 5000,
  });
}
}

@Component({
selector: 'snack-bar-component-example-snack',
template: `
<span>De conferentie is aangemaakt.</span>
`,
styles: [`
  span {
    color: white;
  }
`],
})
export class AanmeldformulierConferenceFeedbackComponent {}

