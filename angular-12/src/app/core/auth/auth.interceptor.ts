import { AuthUtils } from './auth.utils';
import { UserService } from './../user/user.service';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor
{
    /**
     * Constructor
     */
    constructor(private userService: UserService)
    {
    }

    /**
     * Intercept
     *
     * @param req
     * @param next
     */
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>
    {
        // Clone the request object
        let newReq = req.clone();

        // Request
        //
        // If the access token didn't expire, add the Authorization header.
        // We won't add the Authorization header if the access token expired.
        // This will force the server to return a "401 Unauthorized" response
        // for the protected API routes which our response interceptor will
        // catch and delete the access token from the local storage while logging
        // the user out from the app.
        const token = this.userService.getAuthentication();

        if (token && token.token  && !AuthUtils.isTokenExpired(token.token, token.expireAt))
        {
            newReq = req.clone({
                headers: req.headers.set('Authorization', 'Bearer ' + token.token)
            });
        }

        // Response
        return next.handle(newReq);
    }
}
