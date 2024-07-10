import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { DemoComponent } from '../../components/demo/demo.component';
import { LoginComponent } from 'src/app/components/pre-login/login/login.component';
import { RegisterUserComponent } from 'src/app/components/pre-login/register-user/register-user.component';
import { UsersHomeComponent } from 'src/app/components/users/home/users-home/users-home.component';
import { BhaarathaComponent } from 'src/app/components/users/BHA/bhaaratha/bhaaratha.component';
import { BhaarathaHomeComponent } from 'src/app/components/users/BHA/home/bhaaratha-home/bhaaratha-home.component';
import { BhaarathaSharesComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares/bhaaratha-shares.component';
import { BhaarathaMutualFundsComponent } from 'src/app/components/users/BHA/MF/bhaaratha-mutual-funds/bhaaratha-mutual-funds.component';
import { BhaarathaSharesHomeComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-home/bhaaratha-shares-home.component';
import { BhaarathaSharesBoughtComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-bought/bhaaratha-shares-bought.component';
import { BhaarathaSharesSoldComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-sold/bhaaratha-shares-sold.component';
import { BhaarathaSharesAnalysisComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-analysis/bhaaratha-shares-analysis.component';
import { BhaarathaMutualFundsHomeComponent } from 'src/app/components/users/BHA/MF/bhaaratha-mutual-funds-home/bhaaratha-mutual-funds-home.component';
import { BhaarathaMutualFundsBoughtComponent } from 'src/app/components/users/BHA/MF/bhaaratha-mutual-funds-bought/bhaaratha-mutual-funds-bought.component';
import { BhaarathaMutualFundsSoldComponent } from 'src/app/components/users/BHA/MF/bhaaratha-mutual-funds-sold/bhaaratha-mutual-funds-sold.component';
import { BhaarathaMutualFundsAnalysisComponent } from 'src/app/components/users/BHA/MF/bhaaratha-mutual-funds-analysis/bhaaratha-mutual-funds-analysis.component';
import { UsersComponent } from 'src/app/components/users/users/users.component';
import { BhaarathaSharesAddBoughtComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-add-bought/bhaaratha-shares-add-bought.component';
import { BhaarathaSharesOneShareComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-one-share/bhaaratha-shares-one-share.component';
import { BhaarathaSharesAddSoldComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-add-sold/bhaaratha-shares-add-sold.component';
import { BhaarathaSharesOneAnalysisComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-one-analysis/bhaaratha-shares-one-analysis.component';
import { BhaarathaSharesShortTermInvestmentComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-short-term-investment/bhaaratha-shares-short-term-investment.component';
import { BhaarathaSharesAddShortTermInvestmentComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-add-short-term-investment/bhaaratha-shares-add-short-term-investment.component';
import { BhaarathaSharesOneShareShortTermInvestmentComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-one-share-short-term-investment/bhaaratha-shares-one-share-short-term-investment.component';
import { BhaarathaSharesBulkUploadComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-bulk-upload/bhaaratha-shares-bulk-upload.component';
import { BhaarathaSharesCompleteAnalysisComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-complete-analysis/bhaaratha-shares-complete-analysis.component';
import { BhaarathaSharesDividendYieldAnalysisComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-dividend-yield-analysis/bhaaratha-shares-dividend-yield-analysis.component';
import { UserProfileComponent } from 'src/app/components/users/Profile/user-profile/user-profile.component';
import { BhaarathaSharesInvestmentResearchComponent } from 'src/app/components/users/BHA/EQ/bhaaratha-shares-investment-research/bhaaratha-shares-investment-research.component';
import { UnitedStatesOfAmericaComponent } from 'src/app/components/users/USA/united-states-of-america/united-states-of-america.component';
import { UnitedStatesOfAmericaHomeComponent } from 'src/app/components/users/USA/Home/united-states-of-america-home/united-states-of-america-home.component';
import { UnitedStatesOfAmericaSharesComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares/united-states-of-america-shares.component';
import { UnitedStatesOfAmericaSharesHomeComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-home/united-states-of-america-shares-home.component';
import { UnitedStatesOfAmericaSharesBoughtComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-bought/united-states-of-america-shares-bought.component';
import { UnitedStatesOfAmericaSharesSoldComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-sold/united-states-of-america-shares-sold.component';
import { UnitedStatesOfAmericaSharesAnalysisComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-analysis/united-states-of-america-shares-analysis.component';
import { UnitedStatesOfAmericaSharesAddBoughtComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-add-bought/united-states-of-america-shares-add-bought.component';
import { UnitedStatesOfAmericaSharesOneShareComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-one-share/united-states-of-america-shares-one-share.component';
import { UnitedStatesOfAmericaSharesAddSoldComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-add-sold/united-states-of-america-shares-add-sold.component';
import { UnitedStatesOfAmericaSharesCompleteAnalysisComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-complete-analysis/united-states-of-america-shares-complete-analysis.component';
import { UnitedStatesOfAmericaSharesOneAnalysisComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-one-analysis/united-states-of-america-shares-one-analysis.component';
import { UnitedStatesOfAmericaSharesDividendYieldAnalysisComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-dividend-yield-analysis/united-states-of-america-shares-dividend-yield-analysis.component';
import { UnitedStatesOfAmericaSharesInvestmentResearchComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-investment-research/united-states-of-america-shares-investment-research.component';
import { UnitedStatesOfAmericaSharesShortTermInvestmentComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-short-term-investment/united-states-of-america-shares-short-term-investment.component';
import { UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-one-share-short-term-investment/united-states-of-america-shares-one-share-short-term-investment.component';
import { UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent } from 'src/app/components/users/USA/EQ/united-states-of-america-shares-add-short-term-investment/united-states-of-america-shares-add-short-term-investment.component';

const appRoutes: Routes = [
  {
    path: "",
    pathMatch: "full",
    redirectTo: "/login"
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "register-user",
    component: RegisterUserComponent
  },
  {
    path: "users",
    component: UsersComponent,
    children: [
      {
        path: "home",
        component: UsersHomeComponent
      },
      {
        path: "profile",
        component: UserProfileComponent
      },
      {
        path: "BHA",
        component: BhaarathaComponent,
        children: [
          {
            path: "home",
            component: BhaarathaHomeComponent
          },
          {
            path: "EQ",
            component: BhaarathaSharesComponent,
            children: [
              {
                path: "home",
                component: BhaarathaSharesHomeComponent
              },
              {
                path: "bought",
                component: BhaarathaSharesBoughtComponent
              },
              {
                path: "sold",
                component: BhaarathaSharesSoldComponent
              },
              {
                path: "analysis",
                component: BhaarathaSharesAnalysisComponent
              },
              {
                path: "bought-shares",
                component: BhaarathaSharesAddBoughtComponent
              },
              {
                path: "sold-shares",
                component: BhaarathaSharesAddSoldComponent
              },
              {
                path: "one-share",
                component: BhaarathaSharesOneShareComponent
              },
              {
                path: "one-analysis",
                component: BhaarathaSharesOneAnalysisComponent
              },
              {
                path: "short-term-investment",
                component: BhaarathaSharesShortTermInvestmentComponent
              },
              {
                path: "add-short-term-investment",
                component: BhaarathaSharesAddShortTermInvestmentComponent
              },
              {
                path: "one-share-short-term-investment",
                component: BhaarathaSharesOneShareShortTermInvestmentComponent
              },
              {
                path: "bulk-upload",
                component: BhaarathaSharesBulkUploadComponent
              },
              {
                path: "complete-analysis",
                component: BhaarathaSharesCompleteAnalysisComponent
              },
              {
                path: "dividend-yield-analysis",
                component: BhaarathaSharesDividendYieldAnalysisComponent
              },
              {
                path: "investment-research",
                component: BhaarathaSharesInvestmentResearchComponent
              }
            ]
          },
          {
            path: "MF",
            component: BhaarathaMutualFundsComponent,
            children: [
              {
                path: "home",
                component: BhaarathaMutualFundsHomeComponent
              },
              {
                path: "bought",
                component: BhaarathaMutualFundsBoughtComponent
              },
              {
                path: "sold",
                component: BhaarathaMutualFundsSoldComponent
              },
              {
                path: "analysis",
                component: BhaarathaMutualFundsAnalysisComponent
              }
            ]
          }
        ]
      },
      {
        path: "USA",
        component: UnitedStatesOfAmericaComponent,
        children:
        [
          {
            path: "home",
            component: UnitedStatesOfAmericaHomeComponent
          },
          {
            path: "EQ",
            component: UnitedStatesOfAmericaSharesComponent,
            children:
            [
              {
                path: "home",
                component: UnitedStatesOfAmericaSharesHomeComponent
              },
              {
                path: "bought",
                component: UnitedStatesOfAmericaSharesBoughtComponent
              },
              {
                path: "sold",
                component: UnitedStatesOfAmericaSharesSoldComponent
              },
              {
                path: "analysis",
                component: UnitedStatesOfAmericaSharesAnalysisComponent
              },
              {
                path: "bought-shares",
                component: UnitedStatesOfAmericaSharesAddBoughtComponent
              },
              {
                path: "sold-shares",
                component: UnitedStatesOfAmericaSharesAddSoldComponent
              },
              {
                path: "one-share",
                component: UnitedStatesOfAmericaSharesOneShareComponent
              },
              {
                path: "one-analysis",
                component: UnitedStatesOfAmericaSharesOneAnalysisComponent
              },
              {
                path: "complete-analysis",
                component: UnitedStatesOfAmericaSharesCompleteAnalysisComponent
              },
              {
                path: "dividend-yield-analysis",
                component: UnitedStatesOfAmericaSharesDividendYieldAnalysisComponent
              },
              {
                path: "investment-research",
                component: UnitedStatesOfAmericaSharesInvestmentResearchComponent
              },
              {
                path: "short-term-investment",
                component: UnitedStatesOfAmericaSharesShortTermInvestmentComponent
              },
              {
                path: "one-share-short-term-investment",
                component: UnitedStatesOfAmericaSharesOneShareShortTermInvestmentComponent
              },
              {
                path: "add-short-term-investment",
                component: UnitedStatesOfAmericaSharesAddShortTermInvestmentComponent
              }
            ]
          }
        ]
      }
    ]
  },
  {
    path: "demo",
    component: DemoComponent
  }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class RoutingModule { }
