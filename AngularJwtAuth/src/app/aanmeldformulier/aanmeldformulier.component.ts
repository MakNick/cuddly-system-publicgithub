import { Component, OnInit } from '@angular/core';
import { NgModule } from '@angular/core';
import { FormBuilder, Validators, FormGroup, EmailValidator, FormArray, FormControl } from '@angular/forms';
import { Applicant } from '../objects/applicant';

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
  public applicant: Applicant;
  public numberCohosts: number;
  public duration: number;
  public category: String;
  public geslacht: String;

  presentationdraftForm: FormGroup;
  //public presentationdraftForm = new FormGroup('');
  
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
      'naamCohost1': new FormControl('', [       
        Validators.required,
        Validators.minLength(3)
      ]),
      'emailCohost1': new FormControl('', [
        Validators.email                    
      ]),
      'naamCohost2': new FormControl('', [       
        Validators.required,
        Validators.minLength(3)
      ]),
      'emailCohost2': new FormControl('', [
        Validators.email                    
      ]),
      'naamCohost3': new FormControl('', [       
        Validators.required,
        Validators.minLength(3)
      ]),
      'emailCohost3': new FormControl('', [
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

  setDuration(event: any){
    this.duration = event.target.value;
  }

  setCategory(event: any){
    this.category = event.target.value;
  }

  setGeslacht(event: any){
    this.geslacht = event.target.value;
  }
}