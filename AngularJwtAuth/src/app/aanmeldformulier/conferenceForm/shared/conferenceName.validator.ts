import { AbstractControl, ValidatorFn } from '@angular/forms';

export function forbiddenConferenceNameValidator(forbiddenConferenceName: RegExp): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
        const forbidden = forbiddenConferenceName.test(control.value);
        return forbidden ? {
            'forbiddenConferenceName': {
                value: control.value
            }
        } : null;
    };
}