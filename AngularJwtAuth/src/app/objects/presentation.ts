import { Applicant } from "./applicant";

export class Presentation{
    id: number;
    subject: string;
    category: string;
    summary: string;
    type: string;
    duration: number;

    applicants: Applicant[];
}