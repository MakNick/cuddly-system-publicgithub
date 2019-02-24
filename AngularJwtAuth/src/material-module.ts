import { NgModule } from "@angular/core";
import { MatFormFieldModule, MatSelectModule, MatOptionModule, MatInputModule, MatExpansionModule, MatButtonModule, MatDialogModule, MatSnackBarModule } from "@angular/material";
import {MatIconModule} from '@angular/material/icon';

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
        MatSnackBarModule
    ]

})

export class MaterialsModule{}
