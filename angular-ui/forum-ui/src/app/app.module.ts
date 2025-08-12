import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule } from '@angular/forms';
import { RegisterComponent } from './components/register/register.component';
import { ForumComponent } from './components/forum/forum.component';
import { TopicComponent } from './components/topic/topic.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ModeratorPanelComponent } from './components/moderator-panel/moderator-panel.component';
import { VerificationComponent } from './components/verification/verification.component'; // Use provideHttpClient instead

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ForumComponent,
    TopicComponent,
    AdminPanelComponent,
    NavbarComponent,
    ModeratorPanelComponent,
    VerificationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch())
  ],
  bootstrap: [AppComponent]
  //bootstrap: [LoginComponent]
})
export class AppModule { }
