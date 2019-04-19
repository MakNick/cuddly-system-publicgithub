import { Component, OnInit, LOCALE_ID, Inject } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormArray, AbstractControl } from '@angular/forms';
import { forbiddenConferenceNameValidator } from './shared/conferenceName.validator';
import { ConferenceService } from './conferenceForm.service';
import { Conference } from './conferenceForm';
import { MatDialogConfig, MatDialog, MatSnackBar } from '@angular/material';
import { ConferenceFormDialogComponent } from './conferenceFormDialog/conferenceForm-dialog.component';
import { ConferenceDateValidator } from './shared/conferenceDate.validator';

@Component({
  selector: 'conferenceForm',
  templateUrl: './conferenceForm.component.html',
  styleUrls: ['./conferenceForm.component.css']
})
export class ConferenceFormComponent implements OnInit {
  conferenceForm: FormGroup;
  dateGroup: FormGroup;
  conferences: Conference[] = [];
  categories: string[] = [];
  stages: string[] = [];
  minDate = new Date(Date.now());

  defaultDateTime: string = "2099-01-01T01:00";
  dateOfToday = new Date(Date.now());
  dateOfTomorrow = new Date(this.dateOfToday.setDate(this.dateOfToday.getDate() + 1));
  selectedDate = new Date();

  isValid: string;

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

  get startDate() {
    return this.conferenceForm.get('dateGroup').get('startDate');
  }
  
  get endDate() {
    return this.conferenceForm.get('dateGroup').get('endDate');
  }

  get startTime() {
    return this.conferenceForm.get('dateGroup').get('startTime');
  }
  
  get endTime() {
    return this.conferenceForm.get('dateGroup').get('endTime');
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

  constructor(private fb: FormBuilder, private conferenceService: ConferenceService, private dialog: MatDialog, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.conferenceForm = this.fb.group({
      'name': [null, [Validators.required, Validators.minLength(3), forbiddenConferenceNameValidator(/javiel/)]],
      'dateGroup': this.fb.group({
        'startDate': [{ disabled: true, value: ''}],
        'startTime': [null],
        'endDate': [{ disabled: true, value: ''} ],
        'endTime': [null],
        'deadlineDate': [{ disabled: true, value: '' }],
        'deadlineTime': [null],
      }, { validator: ConferenceDateValidator }),    
      'stages': this.fb.array([]),
      'categories': this.fb.array([])
    });
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

  popUpAddCategories() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      popUpTitel: 'Categorie toevoegen',
      popUpInhoud: '',
      popUpType: 0
    }

    const dialogRef = this.dialog.open(ConferenceFormDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => {
        this.categories.push(data.category);
      }
    );
  }

  deleteCategory(x: string) {
    let category = x;
    this.categories.splice(this.categories.indexOf(category), 1);
  }

  setMaxDate(){
    console.log("Jaja");
  }

  popUpAddStages() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data = {
      popUpTitel: 'Stage toevoegen',
      popUpInhoud: '',
      popUpType: 1
    }

    const dialogRef = this.dialog.open(ConferenceFormDialogComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => {
        this.stages.push(data.stage);
      }
    );
  }

  deleteStage(x: string) {
    let stage = x;
    this.stages.splice(this.stages.indexOf(stage), 1);
  }

  submit() {
    //this.validateDate();
    this.createConference();
    this.openSnackBar();
  }

  openSnackBar() {
    this.conferenceForm.reset();
    this.categories = [];
    this.stages = [];

    this.snackBar.openFromComponent(AanmeldformulierConferenceFeedbackComponent, {
      duration: 5000
    });
  }
}

@Component({
  selector: 'conferentieformulier-feedback-snack',
  template: `
<span>De conferentie is aangemaakt.</span>
`,
  styles: [`
  span {
    color: white;
  }
`]
})
export class AanmeldformulierConferenceFeedbackComponent { }
