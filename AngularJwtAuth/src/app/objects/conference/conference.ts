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
    deadlinePresentationDraft: Date;

    categories: string[];
    stages: Stage[];
    extras: Extra[];
    days: Day[]
    presentationDrafts: PresentationDraft[];

    presentationDraftForm: PresentationDraftForm;

}
