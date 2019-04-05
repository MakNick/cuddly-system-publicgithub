import { PresentationDraft } from '../presentation-draft';
import { Stage } from './stage';
import { Extra } from './extra';
import { Day } from '../day';
import { PresentationDraftForm } from '../presentationDraftForm';

export class Conference{
    id: number;
    name: string;
    startDate: Date;
    endDate: Date;

    categories: string[];
    stages: string[];
    extras: Extra[];
    days: Day[]
    presentationDrafts: PresentationDraft[];

    presentationDraftForm: PresentationDraftForm;

}
