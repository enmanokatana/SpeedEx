import {AbstractControl, ValidatorFn} from "@angular/forms";

export function forbiddenCharsValidator(forbiddenChars:RegExp):ValidatorFn{
  return (control:AbstractControl):{[key:string]:any} | null =>{
    const forbidden = forbiddenChars.test(control.value);
    return  forbidden ? {'forbiddenChars' : { value:control.value}} :null
  }
}
