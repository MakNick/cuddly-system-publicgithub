import { Component, OnInit } from '@angular/core';
import {ConfigService} from '../config/config.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Component({
  selector: 'app-aanmeldformulier',
  templateUrl: './aanmeldformulier.component.html',
  styleUrls: ['./aanmeldformulier.component.css']
})
export class AanmeldformulierComponent implements OnInit {
  title = 'aanmeldformulier';
  

    changeTitle($event){
      this.title = $event.target.value;
    }
  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.checkAantalCategorieen();
  }

  toonMedepresentatoren(event: any){
    document.getElementById("extra").innerHTML = "";

    for(let i = 0; i < event.target.value ; i++){
      //naam
      var naamtekst = document.createTextNode("Naam " + (i+1) + ":");
      document.getElementById("extra").appendChild(naamtekst);
      document.getElementById("extra").innerHTML += "<br />";

      var naaminputveld = document.createElement("input");
      naaminputveld.setAttribute("id", "naam"+event.target.value);
      naaminputveld.setAttribute("class", "form-control");
      naaminputveld.setAttribute("required", "required");
      naaminputveld.setAttribute("oninvalid", "setCustomValidity('Dit veld is verplicht.')");
      naaminputveld.setAttribute("oninput", "setCustomValidity('')");
      document.getElementById("extra").appendChild(naaminputveld);
      document.getElementById("extra").innerHTML += "<br />";

      //e-mail
      var mailtekst = document.createTextNode("E-mailadres "+ (i+1) + ":");
      document.getElementById("extra").appendChild(mailtekst);
      document.getElementById("extra").innerHTML += "<br />";

      var mailinputveld = document.createElement("input");
      mailinputveld.setAttribute("id", "naam"+event.target.value);
      mailinputveld.setAttribute("class", "form-control");
      mailinputveld.setAttribute("required", "required");
      mailinputveld.setAttribute("oninvalid", "setCustomValidity('Dit veld is verplicht.')");
      mailinputveld.setAttribute("oninput", "setCustomValidity('')");
      document.getElementById("extra").appendChild(mailinputveld);
      document.getElementById("extra").innerHTML += "<br />";
    }
  }

  checkAantalCategorieen(){
    localStorage.setItem("conferenceID","5");

    let conf_id = localStorage.conferenceID;
    
    //wat zijn de categorieen bij deze conference?
    if(conf_id != 0){
      this.leesCategorieen(conf_id);
    }
  }

  leesCategorieen(x: Number){
    alert("a");
      // const url = "http://localhost:8082/api/conference/"+x;
      // let currentList = this.http.get(url);
      // var categorieen = currentList.categories;
      // alert(categorieen);
    



    
    // //xhr.onreadystatechange = function(){
    //   //if(this.readyState == 4 && this.status == 200){
    //     alert("b");
    //     let currentList = JSON.parse(this.responseText);
    //     var categorieen = currentList.categories;
    //     for(let i=0; i<categorieen.length; i++){
    //       alert("Cat" + i);
    //       alert(categorieen[i]);
    //     //}
      //}
    
    
    alert(x);
  }

}
