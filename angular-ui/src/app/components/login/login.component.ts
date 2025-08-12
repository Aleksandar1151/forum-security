import { ForumUser } from './../../interfaces/user';
import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms'; // Import FormsModule
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  user?: ForumUser;

  constructor(private authService: AuthService, private router: Router) { }

  login() {
    this.authService.login({ username: this.username, password: this.password }).subscribe(response => {

      if (response.user.role == "banned") return;

      sessionStorage.setItem("token", response.token)
      sessionStorage.setItem("username", response.user.username);
      sessionStorage.setItem("role", response.user.role);

      AuthService.UserPermission = response.user.permission;



      this.router.navigate(['/verification'])
        .then(() => {
          window.location.reload();
        });

    });


  }
}
