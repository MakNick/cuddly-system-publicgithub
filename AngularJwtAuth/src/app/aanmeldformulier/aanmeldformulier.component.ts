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

}
