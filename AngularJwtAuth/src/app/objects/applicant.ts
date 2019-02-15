import { PresentationDraft } from "./presentation-draft";
import { Presentation } from "./presentation";

export class Applicant{
    id: number;
    name: string;
    email: string;
    phonenumber: string;
    occupation: string;
    gender: string;
    dateOfBirth: Date;
    requests: string;

    presentationDrafts: PresentationDraft[];

    presentations: Presentation[];
}