import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import services from '../../../assets/services/services.json';
import { PreDataStoreService } from 'src/app/common/store/pre-data/pre-data-store.service';

@Component({
  selector: 'app-money-manager-header',
  templateUrl: './money-manager-header.component.html',
  styleUrls: ['./money-manager-header.component.css']
})
export class MoneyManagerHeaderComponent implements OnInit {

  sarathiAppDetails: string = "";
  anukyaAppDetails: string = "";

  constructor(
    private http: HttpClient,
    private preDataStoreService: PreDataStoreService
  ) { }

  ngOnInit(): void {
    this.http.get<any>(services.preData).subscribe(response => {
      // Store - Country Details
      this.preDataStoreService.setCountryDetailsMap(response.countryDetailsMap);

      // Store - Services Offered Details
      this.preDataStoreService.setServicesOfferedDetailsMap(response.servicesOfferedDetailsMap);

      // Store - Countries and Services Offered Details
      this.preDataStoreService.setCountriesAndServicesOfferedDetailsMap(response.countriesAndServicesOfferedDetailsMap);

      // Store - Gender Details
      this.preDataStoreService.setGenderDetailsMap(response.genderDetailsMap);

      // Store - Sub Services Details
      this.preDataStoreService.setSubServicesDetailsMap(response.subServicesDetailsMap);

      // Store - Sub Services by Service Code Details
      this.preDataStoreService.setSubServicesByServiceCodeDetailsMap(response.subServicesByServiceCodeDetailsMap);
    });

    this.sarathiAppDetails = "SARATHI (" + environment.ACTIVE_PROFILE + "): " + "v" + environment.APP_VERSION;

    this.http.get<any>(services.appDetails, { responseType: 'text' as 'json' }).subscribe(response => {
      this.anukyaAppDetails = response;
    }, error => {
      console.log(error);
      this.anukyaAppDetails = "ERROR";
    })
  }
}
