import { AbstractControl, ValidatorFn } from '@angular/forms';

// Custom validator function
export function noSpacesValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    if (control.value && /\s/.test(control.value)) {
      return { 'noSpaces': true }; // Validation failed
    }
    return null; // Validation passed
  };
}
