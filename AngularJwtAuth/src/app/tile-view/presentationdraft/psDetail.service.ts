import { Injectable } from '@angular/core';
import { PresentationDraft } from 'src/app/objects/presentation-draft';
import { Conference } from 'src/app/objects/conference/conference';

@Injectable()
export class PsDetailService {

  selectedPresentationDraft: PresentationDraft;
  activeConference: Conference;
  
  constructor() { }


  downloadSinglePdf(conferenceId, presentationDraftId){
    let xhr = new XMLHttpRequest();
    xhr.responseType = 'arraybuffer';
    xhr.open("GET","http://localhost:8082/api/" + conferenceId + "/download/pdf/" + presentationDraftId,true);
    xhr.onload = function() {
        console.log(xhr.response);
        var res = xhr.response;

        let blob = new Blob([new Uint8Array(res)]);
            
        console.log(blob);
        var a = window.document.createElement('a');
        a.href = window.URL.createObjectURL(blob);
        a.download = 'PresentationDraft' + presentationDraftId + '.pdf';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    }
    xhr.send();
}

}