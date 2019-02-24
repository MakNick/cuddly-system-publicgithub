import { Component, Inject } from '@angular/core';
import { Location } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { PresentationDraftService } from '../presentation-draft.service';

@Component({
  selector: 'savedialog',
  templateUrl: 'savedialog.component.html',
})
export class SaveDialog {

  constructor(private location: Location,
    private presentationdraftService: PresentationDraftService,
    public dialogRef: MatDialogRef<SaveDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

  onCancel(): void {
    this.location.back();
    this.dialogRef.close();
  }

  onSave(): void {
    this.presentationdraftService.updatePresentationDraft(this.data.conference.id, this.data.presentationDraft).subscribe(this.data.presentationDraft);
    this.location.back();
  }

}