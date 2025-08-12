import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { ForumUser } from '../../interfaces/user';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  email: string = '';
  role: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  register() {

    var newUser = {
      username: this.username,
      password: this.password,
      email: this.email,
      role: "created",
      permission: "000",
    } as ForumUser


    this.authService.register(newUser).subscribe(response => {




      this.authService.sendWelcomeMail(newUser.email).subscribe(response2 => {
        console.log(response2);

        this.router.navigate(['/login'])
          .then(() => {
            window.location.reload();
          });



      });



    });
  }
}
