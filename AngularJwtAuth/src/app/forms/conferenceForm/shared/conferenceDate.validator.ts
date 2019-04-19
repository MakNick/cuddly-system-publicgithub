import { ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';

export const ConferenceDateValidator: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
  let beginDatumObject = new Date(control.get('startDate').value);
  let eindDatumObject = new Date(control.get('endDate').value);
  let beginTijdObject = control.get('startTime').value;
  let eindTijdObject = control.get('endTime').value;

  //Nog geen deadline tijd en datum validatie op dit moment. Kan later worden toegevoegd.
  let deadlineDatumObject = new Date(control.get('deadlineDate').value);
  let deadlineTijdObject = control.get('deadlineTime').value;

  if (control.get('startDate').value == '' || control.get('startDate').value == '') {
    return { 'dateIncorrect': { invalid: true } };
  } else if (beginDatumObject < eindDatumObject) {
    return null;
  } else if (beginDatumObject > eindDatumObject) {
    return { 'dateIncorrect': { invalid: true } };
  } else {
    return (beginTijdObject < eindTijdObject) ? null : { 'timeIncorrect': { invalid: true } };
  }
}