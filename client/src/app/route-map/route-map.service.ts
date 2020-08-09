import {TrainStop} from '../shared/trainStop';

export class RouteMapService{
  // tslint:disable-next-line:typedef no-unused-expression
  // @ts-ignore
  stops = TrainStop[new TrainStop()];
  // tslint:disable-next-line:typedef
  getTrainStops(){
      return this.stops;
  }
}
