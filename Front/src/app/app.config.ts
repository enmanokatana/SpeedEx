import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient} from '@angular/common/http';
import {HttpErrorInterceptor} from "./core/interceptors/http-error-interceptor.interceptor";


export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
    provideHttpClient(),
    {provide:HTTP_INTERCEPTORS,useClass:HttpErrorInterceptor,multi:true}]
};
