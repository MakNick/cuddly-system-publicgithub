import { Component, OnInit } from '@angular/core';
import { NgModule } from '@angular/core';
import { FormBuilder, Validators, FormGroup, EmailValidator, FormArray, FormControl } from '@angular/forms';
import { Applicant } from '../objects/applicant';
import { draftAanmeldService} from '../aanmeldformulier/aanmeldformulier.service';
import { PresentationDraftApplicant } from '../objects/presentationDraftApplicant';
import { PresentationDraft } from '../objects/presentation-draft';
import { PresentationDraftForm } from '../objects/presentationDraftForm';

@Component({
  selector: 'app-aanmeldformulier',
  templateUrl: './aanmeldformulier.component.html',
  styleUrls: ['./aanmeldformulier.component.css']
})
export class AanmeldformulierComponent implements OnInit{
  public title = 'Aanmeldformulier';
  public labelnaam = 'naam';
  public labels = ['Naam','E-mailadres','Onderwerp', 'Omschrijving', 'Type presentatie', 'Categorie', 'Tijdsduur presentatie', 'Beroep/achtergrond', 'Geslacht', 'Overige opmerkingen', 'Wil je mede-presentatoren aanmelden?'];
  public isZichtbaar = true;
  public feedbackBerichten = ['Voer a.u.b. een naam in die 3 tekens of langer is.','Voer a.u.b. een geldig e-mailadres in.'];
  public numberCohosts: number;
  public geslacht: string;

  public presentationDraftApplicant: PresentationDraftApplicant;
  public presentationDraft: PresentationDraft;
  public applicant: Applicant;
  public submitted: boolean = false;

  presentationdraftForm: FormGroup;

  constructor(private draftAanmeldService: draftAanmeldService){
  }

  ngOnInit(): void {
    this.numberCohosts = 0;
    this.presentationdraftForm = new FormGroup({
      'naam': new FormControl('', [       
        Validators.required,
        Validators.minLength(3)
      ]),
      'email': new FormControl('', [
        Validators.email                    
      ]),
      'subject': new FormControl('',[
      ]),
      'description': new FormControl('',[
      ]),
      'type': new FormControl('',[
      ]),
      'category': new FormControl('',[
      ]),
      'duration': new FormControl('',[
      ]),
      'occupation': new FormControl('',[
      ]),
      'gender': new FormControl('',[
      ]),
      'extra': new FormControl('',[
      ]),
      'naamCohost1': new FormControl(null, [       
        Validators.required,
        Validators.minLength(3)
      ]),
      'emailCohost1': new FormControl(null, [
        Validators.email                    
      ]),
      'naamCohost2': new FormControl(null, [       
        Validators.required,
        Validators.minLength(3)
      ]),
      'emailCohost2': new FormControl(null, [
        Validators.email                    
      ]),
      'naamCohost3': new FormControl(null, [       
        Validators.required,
        Validators.minLength(3)
      ]),
      'emailCohost3': new FormControl(null, [
        Validators.email                   
      ])
    });
  }

  checkFormControl(x){
    if(x.status == 'INVALID'){
      return true;
    }else{
      return false;
    }
  }

  get naam(){
    return this.presentationdraftForm.get('naam');
  }

  get email(){
    return this.presentationdraftForm.get('email');
  }

  get subject(){
    return this.presentationdraftForm.get('subject');
  }

  get description(){
    return this.presentationdraftForm.get('description');
  }

  get type(){
    return this.presentationdraftForm.get('type');
  }

  get category(){
    return this.presentationdraftForm.get('category');
  }

  get duration(){
    return this.presentationdraftForm.get('duration');
  }

  get occupation(){
    return this.presentationdraftForm.get('occupation');
  }

  get gender(){
    return this.presentationdraftForm.get('gender');
  }
  get extra(){
    return this.presentationdraftForm.get('extra');
  }

  get naamCohost1(){
    return this.presentationdraftForm.get('naamCohost1');
  }

  get emailCohost1(){
    return this.presentationdraftForm.get('emailCohost1');
  }

  get naamCohost2(){
    return this.presentationdraftForm.get('naamCohost2');
  }

  get emailCohost2(){
    return this.presentationdraftForm.get('emailCohost2');
  }

  get naamCohost3(){
    return this.presentationdraftForm.get('naamCohost3');
  }

  get emailCohost3(){
    return this.presentationdraftForm.get('emailCohost3');
  }

  checkNumberCohosts(){
    return this.numberCohosts;
  }

  setNumberCohosts(event: any){
    this.numberCohosts = event.target.value;
  }

  setGeslacht(event: any){
    this.geslacht = event.target.value;
  }

  setDuration(event: any){
    this.duration.setValue = event.target.value;
  }

  submit(){
    console.log(this.presentationdraftForm); // Dit werkt

    

    this.presentationDraftApplicant.applicant = this.applicant;
    this.presentationDraftApplicant.presentationDraft = this.presentationDraft; 
    
    // this.draftAanmeldService.postPresentationDraftApplicant(this.presentationDraftApplicant).subscribe(presentationDraftApplicant => presentationDraftApplicant.push(presentationDraftApplicant));

    // this.submitted = true;
  }
}