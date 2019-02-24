import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup, EmailValidator, FormArray } from '@angular/forms';
import { forbiddenConferenceNameValidator } from './shared/conferenceName.validator';
import { ConferenceDateValidator } from './shared/conferenceDate.validator';
import { ConferenceService } from './conferenceForm.service';
import { Conference } from './conferenceForm';
import { Time } from '@angular/common'
import { Stage } from './stages/stage';


@Component({
  selector: 'conferenceForm',
  templateUrl: './conferenceForm.component.html',
  styleUrls: ['./conferenceForm.component.css']
})
export class ConferenceFormComponent implements OnInit {

  conferenceForm: FormGroup;
  conferences: Conference[] = [];
  stages: Stage;
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

  constructor(private fb: FormBuilder, private conferenceService: ConferenceService) { }

  ngOnInit() {
    this.conferenceForm = this.fb.group({
      'name': ['', [Validators.required, Validators.minLength(3), forbiddenConferenceNameValidator(/javiel/)]],
      'startDate': [''],
      'startTime': [''],
      'endDate': [''],
      'endTime': [''],
      'deadlineDate': [''],
      'deadlineTime': [''],
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
    this.addConference(conference);
  }

  addConference(conference) {
    this.conferenceService.postConference(conference)
      .subscribe(conference => this.conferences.push(conference));
  }

  loadApi() {
    this.conferenceForm.patchValue({ //patchValue / setValue
      name: 'Topiconf',
      startDate: '2019-01-09',
      startTime: '23:00',
      endDate: '2019-01-09',
      endTime: '23:00',
      deadlineDate: '2019-01-09',
      deadlineTime: '23:00',
    });
  }

  getConferences() {
    this.conferenceService.getConferences().subscribe(conference => this.conferences = conference);
  }

  deleteConference(id: number) {
    this.conferenceService.deleteConference(id).subscribe(conference => this.getConferences());
  }
}
