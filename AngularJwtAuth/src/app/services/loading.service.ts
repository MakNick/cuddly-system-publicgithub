import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  private loading: boolean = false;

  constructor() { }

  isLoading(){
    return this.loading;
  }

  setLoading(value: boolean){
    this.loading = value;
  }
}
