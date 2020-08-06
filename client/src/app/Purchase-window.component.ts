import { Component } from '@angular/core';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'purchase-window',
  template: '<h2>{{title}}</h2>'
})
export class PurchaseWindowComponent{
  title = 'buy tickets!';
  options = ['cedula: ', 'cantidad:', 'inicio', 'destino'];

}

