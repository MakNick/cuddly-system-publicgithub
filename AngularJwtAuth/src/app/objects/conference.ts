import { PresentationDraft } from './presentation-draft';

export class Conference{
    id: number;
    name: string;
    startDate: Date;

    presentationDrafts: PresentationDraft[];
}