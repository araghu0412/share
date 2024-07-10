import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { MoneyManagerComponent } from './components/money-manager/money-manager.component';
import { DemoComponent } from './components/demo/demo.component';
import { MoneyManagerButtonComponent } from './components/common/money-manager-button/money-manager-button.component';
import { RoutingModule } from './common/routing/routing.module';
import { MoneyManagerRadioComponent } from './components/common/money-manager-radio/money-manager-radio.component';
import { FormsModule } from '@angular/forms';
import { Max15CharsPipe } from './common/pipes/max15Chars/max15-chars.pipe';
import { MoneyManagerDropdownComponent } from './components/common/money-manager-dropdown/money-manager-dropdown.component';
import { MoneyManagerCheckBoxComponent } from './components/common/money-manager-check-box/money-manager-check-box.component';
import { MoneyManagerHeaderComponent } from './components/money-manager-header/money-manager-header.component';
import { HttpInterceptorService } from './common/http/interceptor/http-interceptor.service';
import { MoneyManagerLoadingComponent } from './components/common/money-manager-loading/money-manager-loading.component';
import { ErrorModalComponent } from './components/common/error-modal/error-modal.component';
import { SuccessModalComponent } from './components/common/success-modal/success-modal.component';
import { InputFieldErrorComponent } from './components/common/input-field-error/input-field-error.component';
import { LoginComponent } from './components/pre-login/login/login.component';
import { RegisterUserComponent } from './components/pre-login/register-user/register-user.component';
import { UsersHeaderComponent } from './components/users/users-header/users-header.component';
import { BhaarathaSharesComponent } from './components/users/BHA/EQ/bhaaratha-shares/bhaaratha-shares.component';
import { BhaarathaSharesAnalysisComponent } from './components/users/BHA/EQ/bhaaratha-shares-analysis/bhaaratha-shares-analysis.component';
import { BhaarathaSharesBoughtComponent } from './components/users/BHA/EQ/bhaaratha-shares-bought/bhaaratha-shares-bought.component';
import { BhaarathaSharesHomeComponent } from './components/users/BHA/EQ/bhaaratha-shares-home/bhaaratha-shares-home.component';
import { BhaarathaSharesSoldComponent } from './components/users/BHA/EQ/bhaaratha-shares-sold/bhaaratha-shares-sold.component';
import { BhaarathaHomeComponent } from './components/users/BHA/home/bhaaratha-home/bhaaratha-home.component';
import { BhaarathaMutualFundsComponent } from './components/users/BHA/MF/bhaaratha-mutual-funds/bhaaratha-mutual-funds.component';
import { BhaarathaMutualFundsAnalysisComponent } from './components/users/BHA/MF/bhaaratha-mutual-funds-analysis/bhaaratha-mutual-funds-analysis.component';
import { BhaarathaMutualFundsBoughtComponent } from './components/users/BHA/MF/bhaaratha-mutual-funds-bought/bhaaratha-mutual-funds-bought.component';
import { BhaarathaMutualFundsHomeComponent } from './components/users/BHA/MF/bhaaratha-mutual-funds-home/bhaaratha-mutual-funds-home.component';
import { BhaarathaMutualFundsSoldComponent } from './components/users/BHA/MF/bhaaratha-mutual-funds-sold/bhaaratha-mutual-funds-sold.component';
import { BhaarathaComponent } from './components/users/BHA/bhaaratha/bhaaratha.component';
import { UsersComponent } from './components/users/users/users.component';
import { UsersHomeComponent } from './components/users/home/users-home/users-home.component';
import { RefreshTokenComponent } from './components/users/RefreshToken/refresh-token/refresh-token.component';
import { ConfirmationModalComponent } from './components/common/confirmation-modal/confirmation-modal.component';
import { HighlightByNumbersDirective } from './common/directives/highlight-by-numbers/highlight-by-numbers.directive';
import { BhaarathaSharesAddBoughtComponent } from './components/users/BHA/EQ/bhaaratha-shares-add-bought/bhaaratha-shares-add-bought.component';
import { BhaarathaSharesOneShareComponent } from './components/users/BHA/EQ/bhaaratha-shares-one-share/bhaaratha-shares-one-share.component';
import { BhaarathaSharesBoughtSoldHeaderComponent } from './components/users/BHA/EQ/BoughtSold/bhaaratha-shares-bought-sold-header/bhaaratha-shares-bought-sold-header.component';
import { BhaarathaSharesBoughtAndSoldDataComponent } from './components/users/BHA/EQ/BoughtSold/bhaaratha-shares-bought-and-sold-data/bhaaratha-shares-bought-and-sold-data.component';
import { BhaarathaSharesAddSoldComponent } from './components/users/BHA/EQ/bhaaratha-shares-add-sold/bhaaratha-shares-add-sold.component';
import { MoneyManagerDatePickerComponent } from './components/common/money-manager-date-picker/money-manager-date-picker.component';
import { MoneyManagerDatePickerDateViewComponent } from './components/common/DatePicker/money-manager-date-picker-date-view/money-manager-date-picker-date-view.component';
import { MoneyManagerDatePickerMonthViewComponent } from './components/common/DatePicker/money-manager-date-picker-month-view/money-manager-date-picker-month-view.component';
import { MoneyManagerDatePickerYearViewComponent } from './components/common/DatePicker/money-manager-date-picker-year-view/money-manager-date-picker-year-view.component';
import { BhaarathaSharesAddSharesHeaderComponent } from './components/users/BHA/EQ/BoughtSold/bhaaratha-shares-add-shares-header/bhaaratha-shares-add-shares-header.component';
import { BhaarathaSharesBoughtSettingsModalComponent } from './components/users/BHA/EQ/BoughtSold/bhaaratha-shares-bought-settings-modal/bhaaratha-shares-bought-settings-modal.component';
import { MoneyManagerMinMaxBarComponent } from './components/common/money-manager-min-max-bar/money-manager-min-max-bar.component';
import { BhaarathaSharesOneShareDetailsHeaderComponent } from './components/users/BHA/EQ/OneShareDetails/bhaaratha-shares-one-share-details-header/bhaaratha-shares-one-share-details-header.component';
import { BhaarathaSharesOneShareDetailsDataComponent } from './components/users/BHA/EQ/OneShareDetails/bhaaratha-shares-one-share-details-data/bhaaratha-shares-one-share-details-data.component';
import { BhaarathaSharesOneShareDetailsBseNseDataComponent } from './components/users/BHA/EQ/OneShareDetails/bhaaratha-shares-one-share-details-bse-nse-data/bhaaratha-shares-one-share-details-bse-nse-data.component';
import { NgApexchartsModule } from 'ng-apexcharts';
import { BhaarathaSharesOneAnalysisHeaderComponent } from './components/users/BHA/EQ/Analysis/bhaaratha-shares-one-analysis-header/bhaaratha-shares-one-analysis-header.component';
import { BhaarathaSharesOneAnalysisComponent } from './components/users/BHA/EQ/bhaaratha-shares-one-analysis/bhaaratha-shares-one-analysis.component';
import { BhaarathaSharesAddShortTermInvestmentComponent } from './components/users/BHA/EQ/bhaaratha-shares-add-short-term-investment/bhaaratha-shares-add-short-term-investment.component';
import { BhaarathaSharesOneShareShortTermInvestmentComponent } from './components/users/BHA/EQ/bhaaratha-shares-one-share-short-term-investment/bhaaratha-shares-one-share-short-term-investment.component';
import { BhaarathaSharesShortTermInvestmentComponent } from './components/users/BHA/EQ/bhaaratha-shares-short-term-investment/bhaaratha-shares-short-term-investment.component';
import { BhaarathaSharesAddShortTermInvestmentHeaderComponent } from './components/users/BHA/EQ/ShortTermInvestment/bhaaratha-shares-add-short-term-investment-header/bhaaratha-shares-add-short-term-investment-header.component';
import { BhaarathaSharesShortTermInvestmentDataComponent } from './components/users/BHA/EQ/ShortTermInvestment/bhaaratha-shares-short-term-investment-data/bhaaratha-shares-short-term-investment-data.component';
import { BhaarathaSharesShortTermInvestmentHeaderComponent } from './components/users/BHA/EQ/ShortTermInvestment/bhaaratha-shares-short-term-investment-header/bhaaratha-shares-short-term-investment-header.component';
import { BhaarathaSharesBulkUploadComponent } from './components/users/BHA/EQ/bhaaratha-shares-bulk-upload/bhaaratha-shares-bulk-upload.component';
import { BhaarathaSharesHomeHeaderComponent } from './components/users/BHA/EQ/Home/bhaaratha-shares-home-header/bhaaratha-shares-home-header.component';
import { BhaarathaSharesAnalysisServicesHeaderComponent } from './components/users/BHA/EQ/Analysis/bhaaratha-shares-analysis-services-header/bhaaratha-shares-analysis-services-header.component';
import { BhaarathaSharesSectorCategoryAnalysisDataComponent } from './components/users/BHA/EQ/Analysis/bhaaratha-shares-sector-category-analysis-data/bhaaratha-shares-sector-category-analysis-data.component';
import { BhaarathaSharesSectorCategoryAnalysisHeaderComponent } from './components/users/BHA/EQ/Analysis/bhaaratha-shares-sector-category-analysis-header/bhaaratha-shares-sector-category-analysis-header.component';
import { BhaarathaSharesSectorCategoryAnalysisSettingsModalComponent } from './components/users/BHA/EQ/Analysis/bhaaratha-shares-sector-category-analysis-settings-modal/bhaaratha-shares-sector-category-analysis-settings-modal.component';
import { BhaarathaSharesCompleteAnalysisComponent } from './components/users/BHA/EQ/bhaaratha-shares-complete-analysis/bhaaratha-shares-complete-analysis.component';
import { BhaarathaSharesDividendYieldAnalysisComponent } from './components/users/BHA/EQ/bhaaratha-shares-dividend-yield-analysis/bhaaratha-shares-dividend-yield-analysis.component';
import { UserProfileComponent } from './components/users/Profile/user-profile/user-profile.component';
import { MoneyManagerLoadingModalComponent } from './components/common/money-manager-loading-modal/money-manager-loading-modal.component';
import { BhaarathaSharesInvestmentResearchComponent } from './components/users/BHA/EQ/bhaaratha-shares-investment-research/bhaaratha-shares-investment-research.component';
import { BhaarathaSharesInvestmentResearchDataComponent } from './components/users/BHA/EQ/InvestmentResearch/bhaaratha-shares-investment-research-data/bhaaratha-shares-investment-research-data.component';
import { BhaarathaSharesInvestmentResearchHeaderComponent } from './components/users/BHA/EQ/InvestmentResearch/bhaaratha-shares-investment-research-header/bhaaratha-shares-investment-research-header.component';
import { UnitedStatesOfAmericaComponent } from './components/users/USA/united-states-of-america/united-states-of-america.component';
import { UnitedStatesOfAmericaHomeComponent } from './components/users/USA/Home/united-states-of-america-home/united-states-of-america-home.component';
import { UnitedStatesOfAmericaSharesSoldComponent } from './components/users/USA/EQ/united-states-of-america-shares-sold/united-states-of-america-shares-sold.component';
import { UnitedStatesOfAmericaSharesHomeComponent } from './components/users/USA/EQ/united-states-of-america-shares-home/united-states-of-america-shares-home.component';
import { UnitedStatesOfAmericaSharesBoughtComponent } from './components/users/USA/EQ/united-states-of-america-shares-bought/united-states-of-america-shares-bought.component';
import { UnitedStatesOfAmericaSharesAnalysisComponent } from './components/users/USA/EQ/united-states-of-america-shares-analysis/united-states-of-america-shares-analysis.component';
import { UnitedStatesOfAmericaSharesComponent } from './components/users/USA/EQ/united-states-of-america-shares/united-states-of-america-shares.component';
import { UnitedStatesOfAmericaSharesAddSharesHeaderComponent } from './components/users/USA/EQ/BoughtSold/united-states-of-america-shares-add-shares-header/united-states-of-america-shares-add-shares-header.component';
import { UnitedStatesOfAmericaSharesAddBoughtComponent } from './components/users/USA/EQ/united-states-of-america-shares-add-bought/united-states-of-america-shares-add-bought.component';
import { UnitedStatesOfAmericaSharesBoughtAndSoldDataComponent } from './components/users/USA/EQ/BoughtSold/united-states-of-america-shares-bought-and-sold-data/united-states-of-america-shares-bought-and-sold-data.component';
import { UnitedStatesOfAmericaSharesBoughtSettingsModalComponent } from './components/users/USA/EQ/BoughtSold/united-states-of-america-shares-bought-settings-modal/united-states-of-america-shares-bought-settings-modal.component';
import { UnitedStatesOfAmericaSharesBoughtSoldHeaderComponent } from './components/users/USA/EQ/BoughtSold/united-states-of-america-shares-bought-sold-header/united-states-of-america-shares-bought-sold-header.component';
import { UnitedStatesOfAmericaSharesOneShareComponent } from './components/users/USA/EQ/united-states-of-america-shares-one-share/united-states-of-america-shares-one-share.component';
import { UnitedStatesOfAmericaSharesAddSoldComponent } from './components/users/USA/EQ/united-states-of-america-shares-add-sold/united-states-of-america-shares-add-sold.component';
import { UnitedStatesOfAmericaSharesAnalysisServicesHeaderComponent } from './components/users/USA/EQ/Analysis/united-states-of-america-shares-analysis-services-header/united-states-of-america-shares-analysis-services-header.component';
import { UnitedStatesOfAmericaSharesOneAnalysisHeaderComponent } from './components/users/USA/EQ/Analysis/united-states-of-america-shares-one-analysis-header/united-states-of-america-shares-one-analysis-header.component';
import { UnitedStatesOfAmericaSharesSectorCategoryAnalysisDataComponent } from './components/users/USA/EQ/Analysis/united-states-of-america-shares-sector-category-analysis-data/united-states-of-america-shares-sector-category-analysis-data.component';
import { UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeaderComponent } from './components/users/USA/EQ/Analysis/united-states-of-america-shares-sector-category-analysis-header/united-states-of-america-shares-sector-category-analysis-header.component';
import { UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModalComponent } from './components/users/USA/EQ/Analysis/united-states-of-america-shares-sector-category-analysis-settings-modal/united-states-of-america-shares-sector-category-analysis-settings-modal.component';
import { UnitedStatesOfAmericaSharesCompleteAnalysisComponent } from './components/users/USA/EQ/united-states-of-america-shares-complete-analysis/united-states-of-america-shares-complete-analysis.component';
import { UnitedStatesOfAmericaSharesOneAnalysisComponent } from './components/users/USA/EQ/united-states-of-america-shares-one-analysis/united-states-of-america-shares-one-analysis.component';
import { UnitedStatesOfAmericaSharesDividendYieldAnalysisComponent } from './components/users/USA/EQ/united-states-of-america-shares-dividend-yield-analysis/united-states-of-america-shares-dividend-yield-analysis.component';
import { UnitedStatesOfAmericaSharesInvestmentResearchComponent } from './components/users/USA/EQ/united-states-of-america-shares-investment-research/united-states-of-america-shares-investment-research.component';
import { UnitedStatesOfAmericaSharesInvestmentResearchDataComponent } from './components/users/USA/EQ/InvestmentResearch/united-states-of-america-shares-investment-research-data/united-states-of-america-shares-investment-research-data.component';
import { UnitedStatesOfAmericaSharesInvestmentResearchHeaderComponent } from './components/users/USA/EQ/InvestmentResearch/united-states-of-america-shares-investment-research-header/united-states-of-america-shares-investment-research-header.component';
import { UnitedStatesOfAmericaSharesHomeHeaderComponent } from './components/users/USA/EQ/Home/united-states-of-america-shares-home-header/united-states-of-america-shares-home-header.component';
import { UnitedStatesOfAmericaSharesShortTermInvestmentComponent } from './components/users/USA/EQ/united-states-of-america-shares-short-term-investment/united-states-of-america-shares-short-term-investment.component';
import { UnitedStatesOfAmericaSharesShortTermInvestmentDataComponent } from './components/users/USA/EQ/ShortTermInvestment/united-states-of-america-shares-short-term-investment-data/united-states-of-america-shares-short-term-investment-data.component';
import { UnitedStatesOfAmericaSharesShortTermInvestmentHeaderComponent } from './components/users/USA/EQ/ShortTermInvestment/united-states-of-america-shares-short-term-investment-header/united-states-of-america-shares-short-term-investment-header.component';
import { UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent } from './components/users/USA/EQ/united-states-of-america-shares-one-share-short-term-investment/united-states-of-america-shares-one-share-short-term-investment.component';
import { UnitedStatesOfAmericaSharesAddShortTermInvestmentHeaderComponent } from './components/users/USA/EQ/ShortTermInvestment/united-states-of-america-shares-add-short-term-investment-header/united-states-of-america-shares-add-short-term-investment-header.component';
import { UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent } from './components/users/USA/EQ/united-states-of-america-shares-add-short-term-investment/united-states-of-america-shares-add-short-term-investment.component';

@NgModule({
  declarations: [
    AppComponent,
    MoneyManagerComponent,
    DemoComponent,
    MoneyManagerButtonComponent,
    MoneyManagerRadioComponent,
    Max15CharsPipe,
    MoneyManagerDropdownComponent,
    MoneyManagerCheckBoxComponent,
    MoneyManagerHeaderComponent,
    MoneyManagerLoadingComponent,
    ErrorModalComponent,
    SuccessModalComponent,
    InputFieldErrorComponent,
    LoginComponent,
    RegisterUserComponent,
    UsersHeaderComponent,
    BhaarathaSharesComponent,
    BhaarathaSharesAnalysisComponent,
    BhaarathaSharesBoughtComponent,
    BhaarathaSharesHomeComponent,
    BhaarathaSharesSoldComponent,
    BhaarathaHomeComponent,
    BhaarathaMutualFundsComponent,
    BhaarathaMutualFundsAnalysisComponent,
    BhaarathaMutualFundsBoughtComponent,
    BhaarathaMutualFundsHomeComponent,
    BhaarathaMutualFundsSoldComponent,
    BhaarathaComponent,
    UsersComponent,
    UsersHomeComponent,
    RefreshTokenComponent,
    ConfirmationModalComponent,
    HighlightByNumbersDirective,
    BhaarathaSharesAddBoughtComponent,
    BhaarathaSharesOneShareComponent,
    BhaarathaSharesBoughtSoldHeaderComponent,
    BhaarathaSharesBoughtAndSoldDataComponent,
    BhaarathaSharesAddSoldComponent,
    MoneyManagerDatePickerComponent,
    MoneyManagerDatePickerDateViewComponent,
    MoneyManagerDatePickerMonthViewComponent,
    MoneyManagerDatePickerYearViewComponent,
    BhaarathaSharesAddSharesHeaderComponent,
    BhaarathaSharesBoughtSettingsModalComponent,
    MoneyManagerMinMaxBarComponent,
    BhaarathaSharesOneShareDetailsHeaderComponent,
    BhaarathaSharesOneShareDetailsDataComponent,
    BhaarathaSharesOneShareDetailsBseNseDataComponent,
    BhaarathaSharesOneAnalysisHeaderComponent,
    BhaarathaSharesOneAnalysisComponent,
    BhaarathaSharesAddShortTermInvestmentComponent,
    BhaarathaSharesOneShareShortTermInvestmentComponent,
    BhaarathaSharesShortTermInvestmentComponent,
    BhaarathaSharesAddShortTermInvestmentHeaderComponent,
    BhaarathaSharesShortTermInvestmentDataComponent,
    BhaarathaSharesShortTermInvestmentHeaderComponent,
    BhaarathaSharesBulkUploadComponent,
    BhaarathaSharesHomeHeaderComponent,
    BhaarathaSharesAnalysisServicesHeaderComponent,
    BhaarathaSharesSectorCategoryAnalysisDataComponent,
    BhaarathaSharesSectorCategoryAnalysisHeaderComponent,
    BhaarathaSharesSectorCategoryAnalysisSettingsModalComponent,
    BhaarathaSharesCompleteAnalysisComponent,
    BhaarathaSharesDividendYieldAnalysisComponent,
    UserProfileComponent,
    MoneyManagerLoadingModalComponent,
    BhaarathaSharesInvestmentResearchComponent,
    BhaarathaSharesInvestmentResearchDataComponent,
    BhaarathaSharesInvestmentResearchHeaderComponent,
    UnitedStatesOfAmericaComponent,
    UnitedStatesOfAmericaHomeComponent,
    UnitedStatesOfAmericaSharesSoldComponent,
    UnitedStatesOfAmericaSharesHomeComponent,
    UnitedStatesOfAmericaSharesBoughtComponent,
    UnitedStatesOfAmericaSharesAnalysisComponent,
    UnitedStatesOfAmericaSharesComponent,
    UnitedStatesOfAmericaSharesAddSharesHeaderComponent,
    UnitedStatesOfAmericaSharesAddBoughtComponent,
    UnitedStatesOfAmericaSharesBoughtAndSoldDataComponent,
    UnitedStatesOfAmericaSharesBoughtSettingsModalComponent,
    UnitedStatesOfAmericaSharesBoughtSoldHeaderComponent,
    UnitedStatesOfAmericaSharesOneShareComponent,
    UnitedStatesOfAmericaSharesAddSoldComponent,
    UnitedStatesOfAmericaSharesAnalysisServicesHeaderComponent,
    UnitedStatesOfAmericaSharesOneAnalysisHeaderComponent,
    UnitedStatesOfAmericaSharesSectorCategoryAnalysisDataComponent,
    UnitedStatesOfAmericaSharesSectorCategoryAnalysisHeaderComponent,
    UnitedStatesOfAmericaSharesSectorCategoryAnalysisSettingsModalComponent,
    UnitedStatesOfAmericaSharesCompleteAnalysisComponent,
    UnitedStatesOfAmericaSharesOneAnalysisComponent,
    UnitedStatesOfAmericaSharesDividendYieldAnalysisComponent,
    UnitedStatesOfAmericaSharesInvestmentResearchComponent,
    UnitedStatesOfAmericaSharesInvestmentResearchDataComponent,
    UnitedStatesOfAmericaSharesInvestmentResearchHeaderComponent,
    UnitedStatesOfAmericaSharesHomeHeaderComponent,
    UnitedStatesOfAmericaSharesShortTermInvestmentComponent,
    UnitedStatesOfAmericaSharesShortTermInvestmentDataComponent,
    UnitedStatesOfAmericaSharesShortTermInvestmentHeaderComponent,
    UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent,
    UnitedStatesOfAmericaSharesAddShortTermInvestmentHeaderComponent,
    UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RoutingModule,
    HttpClientModule,
    NgApexchartsModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: HttpInterceptorService,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
