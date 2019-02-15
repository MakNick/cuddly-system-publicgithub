import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class ConfigService {
    //SERVER: string = "http://localhost:8082/api";
    //PORT: string = "8082";

  constructor(private http: HttpClient) { }
}