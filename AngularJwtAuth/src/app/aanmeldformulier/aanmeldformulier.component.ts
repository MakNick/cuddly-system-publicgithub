import { Component, OnInit } from '@angular/core';

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
  constructor() { }

  ngOnInit() {
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

    if(event.target.value)

    //for loop: maak aantal keren aan: 
    // label naam, tekstveld naam, label e-mail, tekstveld e-mail
    // vul div id="extra"
  }

}
