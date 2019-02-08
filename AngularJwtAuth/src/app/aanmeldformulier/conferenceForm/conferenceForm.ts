import { Stage } from './stages/stage';

export class Conference {
    id?: number;
    name: String;
    startDate: String;
    endDate: String;
    deadlinePresentationDraft: String;
    categories: Array<String>;
    stages: Array<Stage>;
}
