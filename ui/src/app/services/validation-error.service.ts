import { Injectable } from '@angular/core';
import { ValidationErrors } from '@angular/forms';
import { FormGroup} from '@angular/forms';

@Injectable({ providedIn: 'root' })
export class ValidationErrorService {
  showValidationErrors(form: FormGroup, violations: { field: string; message: string }[]) {
    for (const violation of violations) {
      const control = form.get(violation.field);
      if (control) {
        control.setErrors({ serverError: violation.message });
      }
    }
  }
}
