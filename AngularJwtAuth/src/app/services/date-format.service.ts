import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DateFormatService {

  constructor() { }

  public showCorrectDate(date: Date) {
    let arrayOfDate: string[] = String(date).split(",");
    let formattedDate: string = "";
    for (let i = 2; i >= 0; i--) {
      if (arrayOfDate[i] !== ",") {
        if (i == 0) {
          +arrayOfDate[i] < 10 ? formattedDate += ("0" + arrayOfDate[i]) : formattedDate += arrayOfDate[i];
        } else {
          // formattedDate +=correctDate + "-";
          +arrayOfDate[i] < 10 ? formattedDate += ("0" + arrayOfDate[i] + "-") : formattedDate += arrayOfDate[i] + "-";
        }
      }
    }
    return formattedDate;
  }
}
