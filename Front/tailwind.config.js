/** @type {import('tailwindcss').Config} */
module.exports = {
  presets: [],

  content: [
    "./src/**/*.{html,ts}"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")]
};
