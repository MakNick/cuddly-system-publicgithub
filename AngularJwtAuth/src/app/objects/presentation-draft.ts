import { Applicant } from "./applicant";

export class PresentationDraft{

  private presentationDraftUrl: string = "presentationDraftDetail";
  
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
