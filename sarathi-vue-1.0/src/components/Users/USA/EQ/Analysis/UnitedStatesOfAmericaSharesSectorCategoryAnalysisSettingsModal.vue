<template>
  <div class="united-states-of-america-shares-sector-category-analysis-settings-modal">
    <div class="services-analysis-settings-modal">
      <div class="services-analysis-settings-modal-backdrop">
        <div class="services-analysis-settings-modal-dialog">
          <div class="services-analysis-settings-modal-content">
            <div class="services-analysis-settings-modal-header">
              <h3 class="services-analysis-settings-modal-title">Settings</h3>
            </div>
            <div
              class="services-analysis-settings-modal-body"
            >
              <div class="services-analysis-settings-modal-body-content">
                <div class="services-analysis-settings-form">
                  <div class="key">Long Term Only</div>
                  <div class="value">
                    <MoneyManagerRadio
                      :resetRadio="false"
                      :radioList="longTermRadioList"
                      :radioDefault="longTermRadioDefault"
                      @onRadioSelect="onLongTermRadioSelect($event)"
                    ></MoneyManagerRadio>
                  </div>
                </div>

                <div class="services-analysis-settings-form">
                  <div class="key">Value In %</div>
                  <div class="value">
                    <MoneyManagerRadio
                      :resetRadio="false"
                      :radioList="valueInPercentageRadioList"
                      :radioDefault="valueInPercentageRadioDefault"
                      @onRadioSelect="onValueInPercentageRadioSelect($event)"
                    ></MoneyManagerRadio>
                  </div>
                </div>
              </div>
            </div>
            <div class="services-analysis-settings-modal-footer">
              <div class="services-analysis-settings-close">
                <span>
                  <MoneyManagerButton
                    :buttonTitle="'Close'"
                    :buttonClass="'btn btn-danger'"
                    @onButtonClick="onCloseSettingsClick()"
                  ></MoneyManagerButton>
                </span>
                <div class="services-analysis-settings-continue">
                  <MoneyManagerButton
                    :buttonTitle="'Confirm'"
                    :buttonClass="'btn btn-primary'"
                    @onButtonClick="onConfirmSettingsClick()"
                  ></MoneyManagerButton>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MoneyManagerButton from "@/components/common/MoneyManagerButton.vue";
import MoneyManagerRadio from "@/components/common/MoneyManagerRadio.vue";

export default {
  name: "UnitedStatesOfAmericaSharesAnalysisSettingsModal",
  components: {
    MoneyManagerButton,
    MoneyManagerRadio
  },

  props: {
    longTermRadioList: {
      type: Array,
      required: true
    },

    longTermRadioDefault: {
      type: Object,
      required: true
    },
    valueInPercentageRadioList: {
      type: Array,
      required: true
    },

    valueInPercentageRadioDefault: {
      type: Object,
      required: true
    }
  },

  data() {
    return {
      longTermRadio: {},
      valueInPercentageRadio: {}
    }
  },

  created() {
    this.longTermRadio = this.longTermRadioDefault;
    this.valueInPercentageRadio = this.valueInPercentageRadioDefault;
  },

  methods: {
    onLongTermRadioSelect(longTermRadioSelect) {
      this.longTermRadio = longTermRadioSelect;
    },

    onValueInPercentageRadioSelect(valueInPercentageSelect) {
      this.valueInPercentageRadio = valueInPercentageSelect;
    },

    onCloseSettingsClick() {
      this.$emit("onCloseSettingsClick");
    },

    onConfirmSettingsClick() {
      this.$emit("onConfirmSettingsClick", { longTermRadio: this.longTermRadio, valueInPercentageRadio: this.valueInPercentageRadio});
    }
  }
}
</script>
