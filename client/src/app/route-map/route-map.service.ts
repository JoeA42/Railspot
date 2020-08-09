
export class RouteMapService{
  // tslint:disable-next-line:typedef
  stops = ['parada 1', 'parada 2', 'parada 3', 'parada 4', 'parada 5'];
  // tslint:disable-next-line:typedef
  getTrainStops(){
      return this.stops;
  }
}
