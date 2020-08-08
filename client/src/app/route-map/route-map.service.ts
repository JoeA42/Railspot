
export class RouteMapService{
  // tslint:disable-next-line:typedef
  stops = ['parada1', 'parada2', 'parada3', 'parada4', 'parada5'];
  // tslint:disable-next-line:typedef
  getTrainStops(){
      return this.stops;
  }
}
