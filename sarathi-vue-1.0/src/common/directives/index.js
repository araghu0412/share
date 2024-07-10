export default {
  "highlight-by-numbers": {
    mounted(el, binding) {
      let htmlValue = el.innerHTML.split('%')[0];

      if (binding.value === "bold") {
        el.style.fontWeight = binding.value;
      }

      if (htmlValue > 0) {
        el.style.color = 'green'
      } else if (htmlValue < 0) {
        el.style.color = 'red'
      } else if (htmlValue == 0) {
        el.style.color = 'darkgoldenrod'
      }
    }
  }
}
