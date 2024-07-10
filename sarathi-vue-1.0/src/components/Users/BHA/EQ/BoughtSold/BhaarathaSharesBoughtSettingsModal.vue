<template>
  <div class="bhaaratha-shares-bought-settings-modal">
    <div class="services-bought-settings-modal">
      <div class="services-bought-settings-modal-backdrop">
        <div class="services-bought-settings-modal-dialog">
          <div class="services-bought-settings-modal-content">
            <div class="services-bought-settings-modal-header">
              <h3 class="services-bought-settings-modal-title">Settings</h3>
            </div>
            <div
              class="services-bought-settings-modal-body"
            >
              <div class="services-bought-settings-modal-body-content">
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
            </div>
            <div class="services-bought-settings-modal-footer">
              <div class="services-bought-settings-close">
                <span>
                  <MoneyManagerButton
                    :buttonTitle="'Close'"
                    :buttonClass="'btn btn-danger'"
                    @onButtonClick="onCloseSettingsClick()"
                  ></MoneyManagerButton>
                </span>
                <div class="services-bought-settings-continue">
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
  name: "BhaarathaSharesBoughtSettingsModal",
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
    }
  },

  data() {
    return {
      longTermRadio: {}
    }
  },

  created() {
    this.longTermRadio = this.longTermRadioDefault;
  },

  methods: {
    onLongTermRadioSelect(longTermRadioSelect) {
      this.longTermRadio = longTermRadioSelect;
    },

    onCloseSettingsClick() {
      this.$emit("onCloseSettingsClick");
    },

    onConfirmSettingsClick() {
      this.$emit("onConfirmSettingsClick", this.longTermRadio);
    }
  }
}
</script>
