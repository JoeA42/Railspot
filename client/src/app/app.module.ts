import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { RouteMapComponent } from './route-map/route-map.component';
import {RouteMapService} from './route-map/route-map.service';
import { PurchaseWindowComponent } from './purchase-window/purchase-window.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {FlexLayoutModule} from '@angular/flex-layout';
import 'hammerjs';

@NgModule({
  declarations: [
    AppComponent,
    RouteMapComponent,
    PurchaseWindowComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatToolbarModule
  ],
  providers: [
    RouteMapService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
