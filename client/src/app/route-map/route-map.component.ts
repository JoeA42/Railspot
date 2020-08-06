import { Component, OnInit } from '@angular/core';
import {RouteMapService} from './route-map.service';

@Component({
  selector: 'app-route-map',
  templateUrl: './route-map.component.html',
  styleUrls: ['./route-map.component.css']
})
export class RouteMapComponent implements OnInit {

  title = 'Mapa de rutas';
  stops;

  constructor(service: RouteMapService) {
    this.stops = service.getTrainStops();
  }


  ngOnInit(): void {
  }

}
