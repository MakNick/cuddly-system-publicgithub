import { NgModule } from "@angular/core";
import { MatFormFieldModule, MatSelectModule, MatOptionModule, MatInputModule, MatExpansionModule, MatButtonModule, MatDialogModule, MatSnackBarModule, MatToolbarModule, MatGridListModule } from "@angular/material";
import {MatIconModule} from '@angular/material/icon';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatRippleModule} from '@angular/material/core';

@NgModule({
    exports: [
        MatFormFieldModule,
        MatButtonModule,
        MatSelectModule,
        MatOptionModule,
        MatIconModule,
        MatInputModule,
        MatExpansionModule,
        MatDialogModule,
        MatSnackBarModule,
        MatPaginatorModule,
        MatRippleModule,
        MatButtonModule,
    MatToolbarModule,
    MatGridListModule,
    ]

})

export class MaterialsModule{}
