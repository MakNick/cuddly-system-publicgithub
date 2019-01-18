import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  // private userUrl = 'http://localhost:8082/api/test/user';
  private userUrl = 'http://localhost:8082/api/applicant/3/presentationdrafts';
  private pmUrl = 'http://localhost:8082/api/test/pm';
  // private adminUrl = 'http://localhost:8082/api/test/admin';
  private adminUrl = 'http://localhost:8082/api/conference';



  constructor(private http: HttpClient) { }

  getUserBoard(): Observable<string> {
    console.log(this.http.get(this.userUrl, { responseType: 'text' }));
    console.log(this.userUrl);
    return this.http.get(this.userUrl, { responseType: 'text' });
  }

  getPMBoard(): Observable<string> {
    return this.http.get(this.pmUrl, { responseType: 'text' });
  }

  getAdminBoard(): Observable<string> {
    return this.http.get(this.adminUrl, { responseType: 'text' });
  }
}
