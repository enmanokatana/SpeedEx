import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import {provideHighlightOptions} from "ngx-highlightjs";


export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),provideHttpClient(),provideHighlightOptions({
    fullLibraryLoader:()=> import('ngx-highlightjs'),
    lineNumbersLoader:()=> import('ngx-highlightjs/line-numbers')

  })]
};
