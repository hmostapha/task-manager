import { RouterOutlet } from '@angular/router';
import {Component} from '@angular/core';
import {AppHeaderComponent} from './core/shared/components/app-header/app-header.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AppHeaderComponent],
  templateUrl: './app.component.html'
})
export class AppComponent {}
