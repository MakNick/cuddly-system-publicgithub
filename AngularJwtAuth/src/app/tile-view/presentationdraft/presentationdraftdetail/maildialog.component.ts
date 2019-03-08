import { Component, Inject } from '@angular/core';
import { Location } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'maildialog',
  templateUrl: 'maildialog.component.html',
})
export class MailDialog {

  constructor(private location: Location,
    public dialogRef: MatDialogRef<MailDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

  onCancel(): void {
    this.dialogRef.close();
  }

  onSend(): void {
    console.log("Verstuur mail");
    this.location.back();
  }

}