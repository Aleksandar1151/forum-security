import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ForumComponent } from './components/forum/forum.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ModeratorPanelComponent } from './components/moderator-panel/moderator-panel.component';
import { VerificationComponent } from './components/verification/verification.component';
import { TopicComponent } from './components/topic/topic.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AppComponent } from './app.component';


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'forum', component: ForumComponent },
  { path: 'admin-panel', component: AdminPanelComponent },
  { path: 'moderator-panel', component: ModeratorPanelComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'verification', component: VerificationComponent },
  { path: 'topics/:id', component: TopicComponent } 
]

@NgModule({
  
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  bootstrap: [AppComponent]
})
export class AppRoutingModule { }
