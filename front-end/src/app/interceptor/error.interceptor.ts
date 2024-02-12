import { HttpInterceptorFn } from '@angular/common/http';
import {catchError} from "rxjs";
import {ToastrService} from "ngx-toastr";
import {inject} from "@angular/core";

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const toastService = inject(ToastrService);
  return next(req).pipe(
    catchError(err => {
      toastService.error('Something went wrong<br> Please try again!');
      throw err;
    })
  );
};
