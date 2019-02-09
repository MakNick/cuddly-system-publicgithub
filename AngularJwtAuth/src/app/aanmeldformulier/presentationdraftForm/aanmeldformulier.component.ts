import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormControl } from '@angular/forms';
import { Applicant } from '../../objects/applicant';
import { draftAanmeldService} from '../../aanmeldformulier/presentationdraftForm/aanmeldformulier.service';
import { PresentationDraftApplicant } from '../../objects/presentationDraftApplicant';
import { PresentationDraft } from '../../objects/presentation-draft';

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
  public presentationDraft: FormGroup;
  public applicants: FormGroup;
  public submitted: boolean = false;

  presentationdraftForm: FormGroup;

  constructor(private draftAanmeldService: draftAanmeldService){
  }

  ngOnInit(): void {
    this.numberCohosts = 0;
    this.presentationDraftApplicant = new PresentationDraftApplicant();
    this.presentationdraftForm = new FormGroup({
      'applicants': new FormGroup({
        'naam': new FormControl('', [ Validators.required, Validators.minLength(3)]),
        'email': new FormControl('', [Validators.email]),
        'occupation': new FormControl('',[]),
        'gender': new FormControl('',[]),
        'naamCohost1': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'emailCohost1': new FormControl(null, [Validators.email]),
        'naamCohost2': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'emailCohost2': new FormControl(null, [Validators.email]),
        'naamCohost3': new FormControl(null, [Validators.required, Validators.minLength(3)]),
        'emailCohost3': new FormControl(null, [Validators.email])
      }),
      'presentationDraft': new FormGroup({
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
        'extra': new FormControl('',[
        ])
      })
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
    return this.presentationdraftForm.get('applicants.naam');
  }

  get email(){
    return this.presentationdraftForm.get('applicants.email');
  }

  get subject(){
    return this.presentationdraftForm.get('presentationDraft.subject');
  }

  get description(){
    return this.presentationdraftForm.get('presentationDraft.description');
  }

  get type(){
    return this.presentationdraftForm.get('presentationDraft.type');
  }

  get category(){
    return this.presentationdraftForm.get('presentationDraft.category');
  }

  get duration(){
    return this.presentationdraftForm.get('presentationDraft.duration');
  }

  get occupation(){
    return this.presentationdraftForm.get('applicants.occupation');
  }

  get gender(){
    return this.presentationdraftForm.get('applicants.gender');
  }
  get extra(){
    return this.presentationdraftForm.get('presentationDraft.extra');
  }

  get naamCohost1(){
    return this.presentationdraftForm.get('applicants.naamCohost1');
  }

  get emailCohost1(){
    return this.presentationdraftForm.get('applicants.emailCohost1');
  }

  get naamCohost2(){
    return this.presentationdraftForm.get('applicants.naamCohost2');
  }

  get emailCohost2(){
    return this.presentationdraftForm.get('applicants.emailCohost2');
  }

  get naamCohost3(){
    return this.presentationdraftForm.get('applicants.naamCohost3');
  }

  get emailCohost3(){
    return this.presentationdraftForm.get('applicants.emailCohost3');
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

  presubmit(){
    this.presentationDraftApplicant.applicants = [];
      //new Applicant( ), new Applicant, new Applicant, new Applicant);      //wijs formwaarden toe
    this.presentationDraftApplicant.presentationDraft = Object.assign(this.presentationdraftForm.get('presentationDraft'));
    //console.log(applicants);
  }

  submit(){
    alert("Dit is form");
    console.log(this.presentationdraftForm); // Dit werkt
    alert("Dit is DTO");
    console.log(this.presentationDraftApplicant);
    alert("einde");
    
    this.draftAanmeldService.postPresentationDraftApplicant(this.presentationDraftApplicant)
    .subscribe(presentationDraftApplicant => console.log(presentationDraftApplicant), 
    error => function(error: Error){
      alert(error.message);
    }, function(){
      alert("HOI!");
    });

    this.submitted = true;
  }
}