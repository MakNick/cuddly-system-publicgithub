import { Applicant } from "./applicant";
import {Conference} from "./conference/conference";

export class PresentationDraft{
  
  id: number;
  subject: string;
  category: string;
  summary: string;
  type: string;
  duration: number;
  timeOfCreation: Date;
  label: string;

  applicants: Applicant[];

}
