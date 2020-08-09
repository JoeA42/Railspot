import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AgmCoreModule } from '@agm/core';
import { AppComponent } from './app.component';
import { RouteMapComponent } from './route-map/route-map.component';
import {RouteMapService} from './route-map/route-map.service';
import { PurchaseWindowComponent } from './purchase-window/purchase-window.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {FlexLayoutModule} from '@angular/flex-layout';
import 'hammerjs';
import { BannerComponent } from './banner/banner.component';
import {MatListModule} from '@angular/material/list';

@NgModule({
  declarations: [
    AppComponent,
    RouteMapComponent,
    PurchaseWindowComponent,
    BannerComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MatToolbarModule,
    MatListModule,
    AgmCoreModule.forRoot({apiKey: 'AIzaSyAdnL8N9J7e_umUAMAn9eyWGjax4Nbuh-Y'})
  ],
  providers: [
    RouteMapService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
