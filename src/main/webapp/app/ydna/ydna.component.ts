import {Component, ElementRef, ViewChild} from '@angular/core';

@Component({
  selector: 'jhi-ydna',
  templateUrl: './ydna.component.html',
  styleUrls: ['./ydna.component.scss']
})
export class YdnaComponent{
  @ViewChild('navmapstab') navmapstab:ElementRef;
  @ViewChild('navtemperaturetab') navtemperaturetab:ElementRef;
  @ViewChild('navadditionaltab') navadditionaltab:ElementRef;
  @ViewChild('mapscontainer') mapscontainer:ElementRef;
  @ViewChild('temperaturecontainer') temperaturecontainer:ElementRef;
  @ViewChild('additionalcontainer') additionalcontainer:ElementRef;


  constructor(
    navmapstab: ElementRef,
    navtemperaturetab: ElementRef,
    navadditionaltab: ElementRef,
    mapscontainer: ElementRef,
    temperaturecontainer: ElementRef,
    additionalcontainer: ElementRef
  ) {
    this.navmapstab = navmapstab;
    this.navadditionaltab = navadditionaltab;
    this.navtemperaturetab = navtemperaturetab;
    this.mapscontainer = mapscontainer;
    this.temperaturecontainer = temperaturecontainer;
    this.additionalcontainer = additionalcontainer;


  }
  toggleTab(tabName: string): void {
    switch (tabName) {
      case 'maps':
        if(!this.navmapstab.nativeElement.getAttribute('class').includes('active')){
          this.navmapstab.nativeElement.setAttribute('class','nav-link active');
          this.navtemperaturetab.nativeElement.setAttribute('class','nav-link');
          this.navadditionaltab.nativeElement.setAttribute('class','nav-link');
          this.mapscontainer.nativeElement.setAttribute('class','tab-pane fade show active');
          this.temperaturecontainer.nativeElement.setAttribute('class','tab-pane fade');
          this.additionalcontainer.nativeElement.setAttribute('class','tab-pane fade');
        }
        break;
      case 'temperature':
        if(!this.navtemperaturetab.nativeElement.getAttribute('class').includes('active')){
          this.navmapstab.nativeElement.setAttribute('class','nav-link');
          this.navtemperaturetab.nativeElement.setAttribute('class','nav-link active');
          this.navadditionaltab.nativeElement.setAttribute('class','nav-link');
          this.mapscontainer.nativeElement.setAttribute('class','tab-pane fade');
          this.temperaturecontainer.nativeElement.setAttribute('class','tab-pane fade show active');
          this.additionalcontainer.nativeElement.setAttribute('class','tab-pane fade');
        }
        break;
      case 'additional':
        if(!this.navadditionaltab.nativeElement.getAttribute('class').includes('active')){
          this.navmapstab.nativeElement.setAttribute('class','nav-link');
          this.navtemperaturetab.nativeElement.setAttribute('class','nav-link');
          this.navadditionaltab.nativeElement.setAttribute('class','nav-link active');
          this.mapscontainer.nativeElement.setAttribute('class','tab-pane fade');
          this.temperaturecontainer.nativeElement.setAttribute('class','tab-pane fade');
          this.additionalcontainer.nativeElement.setAttribute('class','tab-pane fade show active');

        }
        break;
      default:

    }
  }
}
