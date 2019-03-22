import { AbstractControl, ValidatorFn } from '@angular/forms';

export function ConferenceDateValidator(begindatum: Date, einddatum: Date): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const forbidden = false; //nameRe.test(control.value);
      return forbidden ? {'startDate': {value: control.value}} : null;
    };
  }