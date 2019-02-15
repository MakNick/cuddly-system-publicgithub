import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormControl } from '@angular/forms';
import { Applicant } from '../../objects/applicant';
import { draftAanmeldService } from '../../aanmeldformulier/presentationdraftForm/aanmeldformulier.service';
import { PresentationDraftApplicant } from '../../objects/presentationDraftApplicant';
import { Conference } from '../../objects/conference/conference';

@Component({
  selector: 'app-aanmeldformulier',
  templateUrl: './aanmeldformulier.component.html',
  styleUrls: ['./aanmeldformulier.component.css']
})
export class AanmeldformulierComponent implements OnInit {
  public title = 'Aanmeldformulier';
  public labelnaam = 'naam';
  public labels = ['Naam', 'E-mailadres', 'Onderwerp', 'Omschrijving', 'Type presentatie', 'Categorie', 'Tijdsduur presentatie', 'Beroep/achtergrond', 'Geslacht', 'Overige opmerkingen', 'Wil je mede-presentatoren aanmelden?'];
  public isZichtbaar = true;
  public feedbackBerichten = ['Voer a.u.b. een naam in die 3 tekens of langer is.', 'Voer a.u.b. een geldig e-mailadres in.', 'Voer a.u.b. een geldig onderwerp in.', 'Voeg a.u.b. een beschrijving toe.'];
  public numberCohosts: number;
  public geslacht: string;
  public conference: Conference;

  public presentationDraftApplicant: PresentationDraftApplicant;

  public presentationDraft: FormGroup;
  public applicants: FormGroup;
  public validFields: boolean[] = [false, false, false, false];
  public submittable: boolean;
  public submitted: boolean = false;

  presentationdraftForm: FormGroup;

  constructor(private draftAanmeldService: draftAanmeldService) {
  }

  ngOnInit(): void {
    this.submittable = false;
    this.conference = new Conference;
    this.conference.id = 1;     //Aanpassen - Via sessionStorage?
    this.numberCohosts = 0;
    this.presentationDraftApplicant = new PresentationDraftApplicant();

    this.presentationdraftForm = new FormGroup({
      'indiener': new FormGroup({
        'name': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'email': new FormControl(null, [Validators.email]),
        'occupation': new FormControl(null, []),
        'gender': new FormControl(null, []),
        'requests': new FormControl(null, [])
      }),
      'cohost1': new FormGroup({
        'name': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'email': new FormControl(null, [Validators.email])
      }),
      'cohost2': new FormGroup({
        'name': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'email': new FormControl(null, [Validators.email])
      }),
      'cohost3': new FormGroup({
        'name': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'email': new FormControl(null, [Validators.email])
      }),
      'presentationDraft': new FormGroup({
        'subject': new FormControl(null, [Validators.required, Validators.minLength(1)]),
        'summary': new FormControl(null, [Validators.required, Validators.minLength(1)]),
        'type': new FormControl(null, []),
        'category': new FormControl(null, []),
        'duration': new FormControl(null, [])
      })
    });
  }

  checkFormControl(x) {

    if (x.status == 'INVALID') {
      return true;
    } else {
      return false;
    }
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

  get name1() {
    return this.presentationdraftForm.get('cohost1.name');
  }

  get email1() {
    return this.presentationdraftForm.get('cohost1.email');
  }

  get name2() {
    return this.presentationdraftForm.get('cohost2.name');
  }

  get email2() {
    return this.presentationdraftForm.get('cohost2.email');
  }

  get name3() {
    return this.presentationdraftForm.get('cohost3.name');
  }

  get email3() {
    return this.presentationdraftForm.get('cohost3.email');
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

  setGeslacht(event: any) {
    this.geslacht = event.target.value;
  }

  setDuration(event: any) {
    this.duration.setValue = event.target.value;
  }

  setApplicants(x) {
    this.presentationDraftApplicant.applicants = [];
    let indiener = Object.assign(this.presentationdraftForm.get('indiener').value);
    this.presentationDraftApplicant.applicants.push(indiener);

    let counter = 0;
    while (counter < x) {
      counter++;
      let applicant = new Applicant;
      if (counter == 1) {
        applicant = Object.assign(this.presentationdraftForm.get('cohost1').value);
        console.log(applicant);
      } else if (counter == 2) {
        applicant = Object.assign(this.presentationdraftForm.get('cohost2').value);
        console.log(applicant);
      } else if (counter == 3) {
        applicant = Object.assign(this.presentationdraftForm.get('cohost3').value);
        console.log(applicant);
      } else {
      }
      this.presentationDraftApplicant.applicants.push(applicant);
    }
  }

  checkIfSubmittable() {
    this.validFields = [false, false, false, false];
    this.validFields[0] = (this.presentationdraftForm.get('indiener').status == 'INVALID') ? false : true;
    this.validFields[1] = (this.presentationdraftForm.get('cohost1').status == 'INVALID') ? false : true;
    this.validFields[2] = (this.presentationdraftForm.get('cohost2').status == 'INVALID') ? false : true;
    this.validFields[3] = (this.presentationdraftForm.get('cohost3').status == 'INVALID') ? false : true;

    if (this.validFields[0] == true && this.presentationdraftForm.get('presentationDraft').status == 'VALID') {
      if (this.checkNumberCohosts() == 0){
        return true;
      }
      if (this.checkNumberCohosts() == 1 && this.validFields[1] == true){
        return true;
      }else if(this.checkNumberCohosts() == 2 && this.validFields[1] == true && this.validFields[2] == true){
        return true;
      }else if(this.checkNumberCohosts() == 3 && this.validFields[1] == true && this.validFields[2] == true && this.validFields[3] == true){
        return true;
      }else{
        return false;
      }
    }else{
      return false;
    }
  }

  presubmit() {
    this.setApplicants(this.checkNumberCohosts());
    this.presentationDraftApplicant.presentationDraft = Object.assign(this.presentationdraftForm.get('presentationDraft').value);

    if (this.checkIfSubmittable()) {
      this.submit();
    } else {
      alert("Er zijn nog onvolledige en/of onjuiste gegevens. Probeer opnieuw.");
    }
  }

  submit() {
    this.draftAanmeldService.postPresentationDraftApplicant(this.presentationDraftApplicant)
      .subscribe(presentationDraftApplicant => console.log(presentationDraftApplicant),
        error => function (error: Error) {
          alert(error.message);
        }, function () {
          console.log("Succesvol!");
        });

    this.submitted = true;
  }
}