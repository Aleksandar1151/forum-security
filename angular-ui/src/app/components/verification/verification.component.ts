import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrl: './verification.component.css'
})
export class VerificationComponent {
  verificationCode: string = '';
  verificationFailed: boolean = false;

  constructor(private router: Router, private authService: AuthService) { }

  verify() {


    this.authService.verifyCode(sessionStorage["username"], this.verificationCode).subscribe(data => {

      console.log("verify code respone --- ");

      if ("Login successful." == data) {
        this.router.navigate(['/forum']);

        this.authService.updateVerificationStatus(true);

      }
      else {
        this.verificationFailed = true;
      }


    });
  }
}
