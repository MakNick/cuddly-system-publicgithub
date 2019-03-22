import { NgModule } from "@angular/core";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatTooltipModule } from '@angular/material/tooltip';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatMenuModule,
  MatNativeDateModule,
  MatOptionModule,
  MatPaginatorModule,
  MatRippleModule,
  MatSelectModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatBadgeModule,
  MatCardModule,
  MatDividerModule,
  MAT_DATE_LOCALE
} from "@angular/material";

@NgModule({
  exports: [
    BrowserAnimationsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatMenuModule,
    MatIconModule,
    MatToolbarModule,
    MatGridListModule,
    MatPaginatorModule,
    MatSelectModule,
    MatInputModule,
    MatRippleModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatOptionModule,
    MatExpansionModule,
    MatDialogModule,
    MatSnackBarModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatBadgeModule,
    MatCardModule,
    MatDividerModule
  ],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'en-GB'},
  ]
})
export class MaterialModule { }
