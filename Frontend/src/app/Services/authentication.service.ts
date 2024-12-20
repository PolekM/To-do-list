import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginReponse } from '../models/authentication/LoginResponse';
import { BehaviorSubject, catchError, Observable, throwError } from 'rxjs';
import { LoginDto } from '../models/authentication/LoginDto';
import { RegisterDto } from '../models/authentication/RegisterDto';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  loginBaseUrl: string = "http://localhost:8080/auth"
  private tokenSubject = new BehaviorSubject<String>(this.getToken()) ;
  token$ = this.tokenSubject.asObservable();

  constructor(private http:HttpClient) { }

  
  public authenticate(loginDto: LoginDto):Observable<LoginReponse>{
    return this.http.post<LoginReponse>(`${this.loginBaseUrl}/login`,loginDto)
  }
  
  public register(registerDto: RegisterDto):Observable<String>{
    return this.http.post<String>(`${this.loginBaseUrl}/register`,registerDto,{ responseType: 'text' as 'json' }).pipe(
      catchError (error => {return throwError(() => new Error(error.error?.message))} ))
  }

  public storageTokenToLocalStorage(token:String){
    localStorage.setItem("Authorization",`Bearer ${token}`)
    this.tokenSubject.next(token);
  }
  
  public removeLocalStorageToken(){
    localStorage.removeItem("Authorization")
    this.tokenSubject.next('');
  }

  public getToken(): string{
    return localStorage.getItem("Authorization") || ''
  }
}
