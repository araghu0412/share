import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../services/local-storage/local-storage.service';
import { BhaarathaSharesCompleteAnalysisStoreService } from '../bhaaratha-shares-complete-analysis/bhaaratha-shares-complete-analysis-store.service';
import { UserDetailsStoreService } from '../user-details/user-details-store.service';
import { BhaarathaSharesDividendYieldAnalysisStoreService } from '../bhaaratha-shares-dividend-yield-analysis/bhaaratha-shares-dividend-yield-analysis-store.service';
import { BhaarathaSharesInvestmentResearchStoreService } from '../bhaaratha-shares-investment-research/bhaaratha-shares-investment-research-store.service';
import { UnitedStatesOfAmericaSharesCompleteAnalysisStoreService } from '../united-states-of-america-shares-complete-analysis/united-states-of-america-shares-complete-analysis-store.service';
import { UnitedStatesOfAmericaSharesDividendYieldAnalysisStoreService } from '../united-states-of-america-shares-dividend-yield-analysis/united-states-of-america-shares-dividend-yield-analysis-store.service';
import { UnitedStatesOfAmericaSharesInvestmentResearchStoreService } from '../united-states-of-america-shares-investment-research/united-states-of-america-shares-investment-research-store.service';

@Injectable({
  providedIn: 'root'
})
export class LogoutStoreService {

  constructor(
    private userDetailsStoreService: UserDetailsStoreService,
    private localStorageService: LocalStorageService,
    private router: Router,
    private bhaarathaSharesCompleteAnalysisStoreService: BhaarathaSharesCompleteAnalysisStoreService,
    private bhaarathaSharesDividendYieldAnalysisStoreService : BhaarathaSharesDividendYieldAnalysisStoreService,
    private bhaarathaSharesInvestmentResearchStoreService: BhaarathaSharesInvestmentResearchStoreService,
    private unitedStatesOfAmericaSharesCompleteAnalysisStoreService: UnitedStatesOfAmericaSharesCompleteAnalysisStoreService,
    private unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService: UnitedStatesOfAmericaSharesDividendYieldAnalysisStoreService,
    private unitedStatesOfAmericaSharesInvestmentResearchStoreService: UnitedStatesOfAmericaSharesInvestmentResearchStoreService
  ) { }

  clearStore() {
    this.userDetailsStoreService.clearTokenDetails();
    this.userDetailsStoreService.clearUserDetails();

    this.bhaarathaSharesCompleteAnalysisStoreService.clearBhaarathaSharesSectorMap();
    this.bhaarathaSharesCompleteAnalysisStoreService.clearBhaarathaSharesSectorLongTermOnlyMap();
    this.bhaarathaSharesCompleteAnalysisStoreService.clearBhaarathaSharesCategoryMap();
    this.bhaarathaSharesCompleteAnalysisStoreService.clearBhaarathaSharesCategoryLongTermOnlyMap();
    this.bhaarathaSharesCompleteAnalysisStoreService.clearBhaarathaSharesOneSectorDetailsMap();
    this.bhaarathaSharesCompleteAnalysisStoreService.clearBhaarathaSharesOneSectorDetailsLongTermOnlyMap();
    this.bhaarathaSharesCompleteAnalysisStoreService.clearBhaarathaSharesOneCategoryDetailsMap();
    this.bhaarathaSharesCompleteAnalysisStoreService.clearBhaarathaSharesOneCategoryDetailsLongTermOnlyMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesSectorMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesSectorLongTermOnlyMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesCategoryMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesCategoryLongTermOnlyMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesOneSectorDetailsMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesOneSectorDetailsLongTermOnlyMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesOneCategoryDetailsMap();
    this.bhaarathaSharesDividendYieldAnalysisStoreService.clearBhaarathaSharesOneCategoryDetailsLongTermOnlyMap();
    this.bhaarathaSharesInvestmentResearchStoreService.clearBhaarathaSharesInvestmentResearchList();

    this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.clearUnitedStatesOfAmericaSharesSectorMap();
    this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.clearUnitedStatesOfAmericaSharesSectorLongTermOnlyMap();
    this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.clearUnitedStatesOfAmericaSharesCategoryMap();
    this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.clearUnitedStatesOfAmericaSharesCategoryLongTermOnlyMap();
    this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.clearUnitedStatesOfAmericaSharesOneSectorDetailsMap();
    this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.clearUnitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap();
    this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.clearUnitedStatesOfAmericaSharesOneCategoryDetailsMap();
    this.unitedStatesOfAmericaSharesCompleteAnalysisStoreService.clearUnitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap();
    this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.clearUnitedStatesOfAmericaSharesSectorMap();
    this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.clearUnitedStatesOfAmericaSharesSectorLongTermOnlyMap();
    this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.clearUnitedStatesOfAmericaSharesCategoryMap();
    this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.clearUnitedStatesOfAmericaSharesCategoryLongTermOnlyMap();
    this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.clearUnitedStatesOfAmericaSharesOneSectorDetailsMap();
    this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.clearUnitedStatesOfAmericaSharesOneSectorDetailsLongTermOnlyMap();
    this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.clearUnitedStatesOfAmericaSharesOneCategoryDetailsMap();
    this.unitedStatesOfAmericaSharesDividendYieldAnalysisStoreService.clearUnitedStatesOfAmericaSharesOneCategoryDetailsLongTermOnlyMap();
    this.unitedStatesOfAmericaSharesInvestmentResearchStoreService.clearUnitedStatesOfAmericaSharesInvestmentResearchList();

    localStorage.removeItem("refreshTokenTimer");
    localStorage.removeItem("accessTokenTimer");
  }

  clearStoreAndLogout() {
    this.clearStore();
    clearInterval(this.localStorageService.timer);
    this.router.navigate(["/"]);
  }
}
