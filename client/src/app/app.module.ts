import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {PurchaseWindowComponent} from './Purchase-window.component';
import { RouteMapComponent } from './route-map/route-map.component';
import {RouteMapService} from './route-map/route-map.service';

@NgModule({
  declarations: [
    AppComponent,
    PurchaseWindowComponent,
    RouteMapComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [
    RouteMapService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
