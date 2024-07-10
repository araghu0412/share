import { createRouter, createWebHistory } from 'vue-router';
import Demo from '@/components/Demo.vue';
import Login from '@/components/PreLogin/Login.vue';
import RegisterUser from '@/components/PreLogin/RegisterUser.vue';
import Users from "@/components/Users/Users.vue";
import UsersHome from "@/components/Users/Home/UsersHome.vue";
import Bhaaratha from "@/components/Users/BHA/Bhaaratha.vue";
import BhaarathaHome from "@/components/Users/BHA/Home/BhaarathaHome.vue";
import BhaarathaShares from "@/components/Users/BHA/EQ/BhaarathaShares.vue";
import BhaarathaSharesHome from "@/components/Users/BHA/EQ/BhaarathaSharesHome.vue";
import BhaarathaSharesBought from "@/components/Users/BHA/EQ/BhaarathaSharesBought.vue";
import BhaarathaSharesSold from "@/components/Users/BHA/EQ/BhaarathaSharesSold.vue";
import BhaarathaSharesAnalysis from "@/components/Users/BHA/EQ/BhaarathaSharesAnalysis.vue";
import BhaarathaMutualFunds from "@/components/Users/BHA/MF/BhaarathaMutualFunds.vue";
import BhaarathaMutualFundsHome from "@/components/Users/BHA/MF/BhaarathaMutualFundsHome.vue";
import BhaarathaMutualFundsBought from "@/components/Users/BHA/MF/BhaarathaMutualFundsBought.vue";
import BhaarathaMutualFundsSold from "@/components/Users/BHA/MF/BhaarathaMutualFundsSold.vue";
import BhaarathaMutualFundsAnalysis from "@/components/Users/BHA/MF/BhaarathaMutualFundsAnalysis.vue";
import BhaarathaSharesAddBought from "@/components/Users/BHA/EQ/BhaarathaSharesAddBought.vue";
import BhaarathaSharesAddSold from "@/components/Users/BHA/EQ/BhaarathaSharesAddSold.vue";
import BhaarathaSharesOneShare from "@/components/Users/BHA/EQ/BhaarathaSharesOneShare.vue";
import BhaarathaSharesOneAnalysis from "@/components/Users/BHA/EQ/BhaarathaSharesOneAnalysis.vue";
import BhaarathaSharesShortTermInvestment from "@/components/Users/BHA/EQ/BhaarathaSharesShortTermInvestment.vue";
import BhaarathaSharesAddShortTermInvestment from "@/components/Users/BHA/EQ/BhaarathaSharesAddShortTermInvestment.vue";
import BhaarathaSharesOneShareShortTermInvestment from "@/components/Users/BHA/EQ/BhaarathaSharesOneShareShortTermInvestment.vue";
import BhaarathaSharesBulkUpload from "@/components/Users/BHA/EQ/BhaarathaSharesBulkUpload.vue";
import BhaarathaSharesCompleteAnalysis from "@/components/Users/BHA/EQ/BhaarathaSharesCompleteAnalysis.vue";
import BhaarathaSharesDividendYieldAnalysis from "@/components/Users/BHA/EQ/BhaarathaSharesDividendYieldAnalysis.vue";
import UserProfile from "@/components/Users/Profile/UserProfile.vue";
import BhaarathaSharesInvestmentResearch from "@/components/Users/BHA/EQ/BhaarathaSharesInvestmentResearch.vue";
import UnitedStatesOfAmerica from "@/components/Users/USA/UnitedStatesOfAmerica.vue";
import UnitedStatesOfAmericaHome from "@/components/Users/USA/Home/UnitedStatesOfAmericaHome.vue";
import UnitedStatesOfAmericaShares from "@/components/Users/USA/EQ/UnitedStatesOfAmericaShares.vue";
import UnitedStatesOfAmericaSharesHome from "@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesHome.vue";
import UnitedStatesOfAmericaSharesBought from "@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesBought.vue";
import UnitedStatesOfAmericaSharesSold from "@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesSold.vue";
import UnitedStatesOfAmericaSharesAnalysis from "@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesAnalysis.vue";
import UnitedStatesOfAmericaSharesAddBought from "@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesAddBought.vue";
import UnitedStatesOfAmericaSharesAddSold from "@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesAddSold.vue";
import UnitedStatesOfAmericaSharesOneShare from "@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesOneShare.vue";
import UnitedStatesOfAmericaSharesOneAnalysis from "@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesOneAnalysis.vue";
import UnitedStatesOfAmericaSharesCompleteAnalysis from "@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesCompleteAnalysis.vue";
import UnitedStatesOfAmericaSharesDividendYieldAnalysis from '@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesDividendYieldAnalysis.vue';
import UnitedStatesOfAmericaSharesInvestmentResearch from '@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesInvestmentResearch.vue';
import UnitedStatesOfAmericaSharesShortTermInvestment from '@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesShortTermInvestment.vue';
import UnitedStatesOfAmericaSharesOneShareShortTermInvestment from '@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesOneShareShortTermInvestment.vue';
import UnitedStatesOfAmericaSharesAddShortTermInvestment from "@/components/Users/USA/EQ/UnitedStatesOfAmericaSharesAddShortTermInvestment.vue";

const routes =
[
  {
    path: "",
    redirect: "/login"
},
{
    path: "/login",
    name: "Login",
    component: Login
},
{
  path: "/register-user",
  name: "RegisterUser",
  component: RegisterUser
},
{
  path: "/users",
  name: "Users",
  component: Users,
  children:
  [
    {
      path: "home",
      name: "UsersHome",
      component: UsersHome
    },
    {
      path: "profile",
      name: "UserProfile",
      component: UserProfile
    },
    {
      path: "BHA",
      name: "Bhaaratha",
      component: Bhaaratha,
      children:
      [
        {
          path: "home",
          name: "BhaarathaHome",
          component: BhaarathaHome
        },
        {
          path: "EQ",
          name: "BhaarathaShares",
          component: BhaarathaShares,
          children:
          [
            {
              path: "home",
              name: "BhaarathaSharesHome",
              component: BhaarathaSharesHome
            },
            {
              path: "bought",
              name: "BhaarathaSharesBought",
              component: BhaarathaSharesBought
            },
            {
              path: "sold",
              name: "BhaarathaSharesSold",
              component: BhaarathaSharesSold
            },
            {
              path: "analysis",
              name: "BhaarathaSharesAnalysis",
              component: BhaarathaSharesAnalysis
            },
            {
              path: "bought-shares",
              name: "BhaarathaSharesAddBought",
              component: BhaarathaSharesAddBought
            },
            {
              path: "sold-shares",
              name: "BhaarathaSharesAddSold",
              component: BhaarathaSharesAddSold
            },
            {
              path: "one-share",
              name: BhaarathaSharesOneShare,
              component: BhaarathaSharesOneShare
            },
            {
              path: "one-analysis",
              name: BhaarathaSharesOneAnalysis,
              component: BhaarathaSharesOneAnalysis
            },
            {
              path: "short-term-investment",
              name: BhaarathaSharesShortTermInvestment,
              component: BhaarathaSharesShortTermInvestment
            },
            {
              path: "add-short-term-investment",
              name: "BhaarathaSharesAddShortTermInvestment",
              component: BhaarathaSharesAddShortTermInvestment
            },
            {
              path: "one-share-short-term-investment",
              name: "BhaarathaSharesOneShareShortTermInvestment",
              component: BhaarathaSharesOneShareShortTermInvestment
            },
            {
              path: "bulk-upload",
              name: BhaarathaSharesBulkUpload,
              component: BhaarathaSharesBulkUpload
            },
            {
              path: "complete-analysis",
              name: BhaarathaSharesCompleteAnalysis,
              component: BhaarathaSharesCompleteAnalysis
            },
            {
              path: "dividend-yield-analysis",
              name: BhaarathaSharesDividendYieldAnalysis,
              component: BhaarathaSharesDividendYieldAnalysis
            },
            {
              path: "investment-research",
              name: BhaarathaSharesInvestmentResearch,
              component: BhaarathaSharesInvestmentResearch
            }
          ]
        },
        {
          path: "MF",
          name: "BhaarathaMutualFunds",
          component: BhaarathaMutualFunds,
          children:
          [
            {
              path: "home",
              name: "BhaarathaMutualFundsHome",
              component: BhaarathaMutualFundsHome
            },
            {
              path: "bought",
              name: "BhaarathaMutualFundsBought",
              component: BhaarathaMutualFundsBought
            },
            {
              path: "sold",
              name: "BhaarathaMutualFundsSold",
              component: BhaarathaMutualFundsSold
            },
            {
              path: "analysis",
              name: "BhaarathaMutualFundsAnalysis",
              component: BhaarathaMutualFundsAnalysis
            }
          ]
        }
      ]
    },
    {
      path: "USA",
      name: "UnitedStatesOfAmerica",
      component: UnitedStatesOfAmerica,
      children:
      [
        {
          path: "home",
          name: "UnitedStatesOfAmericaHome",
          component: UnitedStatesOfAmericaHome
        },
        {
          path: "EQ",
          name: "UnitedStatesOfAmericaShares",
          component: UnitedStatesOfAmericaShares,
          children:
          [
            {
              path: "home",
              name: "UnitedStatesOfAmericaSharesHome",
              component: UnitedStatesOfAmericaSharesHome
            },
            {
              path: "bought",
              name: "UnitedStatesOfAmericaSharesBought",
              component: UnitedStatesOfAmericaSharesBought
            },
            {
              path: "sold",
              name: "UnitedStatesOfAmericaSharesSold",
              component: UnitedStatesOfAmericaSharesSold
            },
            {
              path: "analysis",
              name: "UnitedStatesOfAmericaSharesAnalysis",
              component: UnitedStatesOfAmericaSharesAnalysis
            },
            {
              path: "bought-shares",
              name: "UnitedStatesOfAmericaSharesAddBought",
              component: UnitedStatesOfAmericaSharesAddBought
            },
            {
              path: "sold-shares",
              name: "UnitedStatesOfAmericaSharesAddSold",
              component: UnitedStatesOfAmericaSharesAddSold
            },
            {
              path: "one-share",
              name: UnitedStatesOfAmericaSharesOneShare,
              component: UnitedStatesOfAmericaSharesOneShare
            },
            {
              path: "one-analysis",
              name: UnitedStatesOfAmericaSharesOneAnalysis,
              component: UnitedStatesOfAmericaSharesOneAnalysis
            },
            {
              path: "complete-analysis",
              name: UnitedStatesOfAmericaSharesCompleteAnalysis,
              component: UnitedStatesOfAmericaSharesCompleteAnalysis
            },
            {
              path: "dividend-yield-analysis",
              name: UnitedStatesOfAmericaSharesDividendYieldAnalysis,
              component: UnitedStatesOfAmericaSharesDividendYieldAnalysis
            },
            {
              path: "investment-research",
              name: UnitedStatesOfAmericaSharesInvestmentResearch,
              component: UnitedStatesOfAmericaSharesInvestmentResearch
            },
            {
              path: "short-term-investment",
              name: UnitedStatesOfAmericaSharesShortTermInvestment,
              component: UnitedStatesOfAmericaSharesShortTermInvestment
            },
            {
              path: "one-share-short-term-investment",
              name: "UnitedStatesOfAmericaSharesOneShareShortTermInvestment",
              component: UnitedStatesOfAmericaSharesOneShareShortTermInvestment
            },
            {
              path: "add-short-term-investment",
              name: "UnitedStatesOfAmericaSharesAddShortTermInvestment",
              component: UnitedStatesOfAmericaSharesAddShortTermInvestment
            }
          ]
        }
      ]
    }
  ]
},
{
  path: "/demo",
  name: "Demo",
  component: Demo
}]

const router = createRouter({ history: createWebHistory(), routes })

export default router
