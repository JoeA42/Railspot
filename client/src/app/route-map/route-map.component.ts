import { Component, OnInit } from '@angular/core';
import {RouteMapService} from './route-map.service';
import {TrainStop} from '../shared/trainStop';

@Component({
  selector: 'app-route-map',
  templateUrl: './route-map.component.html',
  styleUrls: ['./route-map.component.css']
})
export class RouteMapComponent implements OnInit {

  title = 'Mapa de rutas';
  stops: TrainStop[];
  latitude = 10.010060  ;
  longitude = -84.018430;

  constructor(service: RouteMapService) {
    this.stops = service.getTrainStops();
  }


  ngOnInit(): void {
  }

}
