import { Component, Inject } from '@angular/core';
import { Location } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { PresentationDraftService } from '../presentation-draft.service';

@Component({
  selector: 'deletedialog',
  templateUrl: 'deletedialog.component.html',
})
export class DeleteDialog {

  constructor(private location: Location,
    private presentationdraftService: PresentationDraftService,
    public dialogRef: MatDialogRef<DeleteDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

  onCancel(): void {
    this.dialogRef.close();
  }

  onDelete(): void {
    this.presentationdraftService.deletePresentationDraft(this.data.presentationDraft).subscribe(this.data.presentationDraft);
    this.location.back();
  }

}