import { Component } from '@angular/core';

@Component({
  selector: 'app-loader',
  template: `
      <div class="h-screen flex justify-center items-center">
          <div class="lds-ring scale-150"><div></div><div></div><div></div><div></div></div>
      </div>
  `,
  styleUrl: './loader.component.scss'
})
export class LoaderComponent {

}
