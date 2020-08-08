import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { RouteMapComponent } from './route-map/route-map.component';
import {RouteMapService} from './route-map/route-map.service';
import { PurchaseWindowComponent } from './purchase-window/purchase-window.component';

@NgModule({
  declarations: [
    AppComponent,
    RouteMapComponent,
    PurchaseWindowComponent
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
