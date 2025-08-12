import { AuthService } from '../../services/auth.service';
import { AdminPanelComponent } from './../admin-panel/admin-panel.component';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {


  public ForumVisible: boolean = false;
  public AdminPanelVisible: boolean = false;
  public ModeratorPanelVisible: boolean = false;


  constructor(private authService: AuthService) { }
  ngOnInit(): void {
    // Initial visibility check based on session storage
    //this.updateVisibility();

    // Subscribe to verification status updates
    this.authService.verificationStatus$.subscribe(isVerified => {
      if (isVerified) {
        // Update visibility based on role after verification
        this.updateVisibility();
      }
    });
  }

  private updateVisibility(): void {
    if (sessionStorage === undefined) return;

    const role = sessionStorage["role"];

    if (role === "admin") {
      this.ForumVisible = this.AdminPanelVisible = this.ModeratorPanelVisible = true;
    } else if (role === "moderator") {
      this.ForumVisible = this.ModeratorPanelVisible = true;
    } else if (role === "member") {
      this.ForumVisible = true;
    }
  }


  /*
    ngOnInit(): void {
  
  
  
      if (sessionStorage === undefined) return;
      //if (localStorage === undefined) return;
      //if (localStorage["verified"] != "true") return;
  
  
      if (sessionStorage["role"] == "admin") {
        this.ForumVisible = this.AdminPanelVisible = this.ModeratorPanelVisible = true;
      }
  
      if (sessionStorage["role"] == "moderator") {
        this.ForumVisible = this.ModeratorPanelVisible = true;
      }
  
      if (sessionStorage["role"] == "member") {
        this.ForumVisible = true;
      }
    }*/
}
