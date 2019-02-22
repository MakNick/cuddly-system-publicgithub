import { NgModule } from "@angular/core";
import { MatFormFieldModule, MatSelectModule, MatOptionModule, MatInputModule, MatExpansionModule, MatButtonModule, MatDialogModule } from "@angular/material";
import {MatIconModule} from '@angular/material/icon';
import { DialogWindowComponent } from "./app/aanmeldformulier/dialogWindow/dialogWindow.component";

@NgModule({
    exports: [
        MatFormFieldModule,
        MatButtonModule,
        MatSelectModule,
        MatOptionModule,
        MatIconModule,
        MatInputModule,
        MatExpansionModule,
        MatDialogModule
    ]

})

export class MaterialsModule{}
