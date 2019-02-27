import {NgModule} from "@angular/core";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatTooltipModule} from '@angular/material/tooltip';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatGridListModule,
  MatIconModule, MatInputModule,
  MatMenuModule, MatPaginatorModule, MatRippleModule, MatSelectModule,
  MatToolbarModule
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
    MatTooltipModule

  ]
})
export class MaterialModule{}
