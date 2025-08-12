import { ForumUser } from './../../interfaces/user';
import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { AuthService } from '../../services/auth.service';
import { empty } from 'rxjs';
import { ForumService } from '../../services/forum.service';
import { Topic } from '../../interfaces/topic';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html'
})
export class AdminPanelComponent implements OnInit {
  displayUsers: ForumUser[] = [];
  roles: string[] = ['created', 'member', 'admin', 'moderator', 'banned'];
  // Initialize newTopic with default values
  newTopic: Topic = {
    title: '',
    description: '',
  };

  constructor(private adminService: AdminService,
    private authService: AuthService, private forumService: ForumService, private router: Router) { }


  fetchUsers() {
    this.adminService.getUsers().subscribe(data => {
      console.log(data);
      this.displayUsers = data;

    });
  }


  saveUser(user: ForumUser) {
    user.password = '';
    this.authService.register(user).subscribe(response => {
    });

  }

  onRoleChange(user: ForumUser, event: any) {
    console.log("User onRoleChange");
    user.role = event.target.value;
  }

  onPermissionChange(user: ForumUser, permissionIndex: number, event: any) {
    let permissionsArray = user.permission.split('');
    permissionsArray[permissionIndex] = event.target.checked ? '1' : '0';
    user.permission = permissionsArray.join('');
  }

  ngOnInit() {
    /* this.adminService.getUsers().subscribe(data => {
       this.users = data;
     });*/

    const userRole = sessionStorage.getItem('role');

    // Redirect to /forum if sessionStorage doesn't have a role
    if (!userRole) {
      this.router.navigate(['/login']);
      return;
    }

    // Redirect to /403 if the role is not 'admin'
    if (userRole !== 'admin') {
      this.router.navigate(['/login']);
      return;
    }


    this.fetchUsers();



  }

  approveUser(userId: string) {
    /*c this.adminService.approveUser(userId).subscribe(() => {
       // Handle approval logic
     });*/
  }

  assignRole(userId: string, role: string) {
    this.adminService.assignRole(userId, role).subscribe(() => {
      // Handle role assignment logic
    });
  }

  createNewTopic() {
    this.forumService.createTopic(this.newTopic).subscribe(() => {

    });
  }
}
