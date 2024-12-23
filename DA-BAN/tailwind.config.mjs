/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        pretendard: ['var(--font-pretendard-variable)']
      },
      colors: {
        primary: "var(--primary, #09B1A7)",
        error: "var(--error, #F44336)",
        basicWhite: "var(--basic, #FFF)",
        basicBlack: "var(--basic, #000)",
        disabled: "#A3A3A3",
        disabledSub: "#EBEBEB"
      },
    },
  },
  plugins: [],
};
