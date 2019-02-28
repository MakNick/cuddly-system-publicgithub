import { AbstractControl } from "@angular/forms";

export function ConferenceDateValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const conferenceStartDate = control.get('conferenceDate.startDate');
    const conferenceEndDate = control.get('conferenceDate.endDate');
    return conferenceStartDate && conferenceEndDate && conferenceStartDate.value != conferenceEndDate.value ?
        { 'misMatch': true } :
        null;
}