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
				pretendard: ['var(--font-pretendard-variable)'],
			},
			colors: {
				semantic: {
					primary: {
						normal: "var(--colors-soda-30)",
						strong: "var(--colors-soda-20)",
					},
					labelText: {
						normal: "var(--colors-common-0)",
						inverse: "var(--colors-common-100)",
						neutral: "var(--colors-cool-neutral-50)",
						alternative: "var(--colors-neutral-60)",
						assistive: "var(--colors-neutral-80)",
						disabled: "var(--colors-neutral-60)",
					},
					background: {
						normal: "var(--colors-common-100)",
						strong: "var(--colors-cool-neutral-95)",
					},
					line: {
						normal: "var(--colors-neutral-95)",
						divider: "var(--colors-cool-neutral-85)",
						disabled: "var(--colors-neutral-80)",
						strong: "var(--colors-neutral-90)",
						heavy: "var(--colors-common-0)",
					},
					component: {
						fill: {
							normal: "var(--colors-common-100)",
							strong: "var(--colors-neutral-70)",
							hover: "var(--colors-neutral-99)",
							disabled: "var(--colors-neutral-95)",
							alternative: "var(--colors-cool-neutral-80)",
						},
						material: {
							dimmer: "#1e1e1e85",
						},
						static: {
							white: "var(--colors-common-100)",
							black: "var(--colors-common-0)",
						},
					},
					status: {
						error: "var(--colors-red-70)",
					},
					button: {
						primary: "var(--colors-soda-30)",
						normalLine: "var(--colors-cool-neutral-85)",
						normalFill: "var(--colors-common-100)",
						disabledFill: "var(--colors-neutral-95)",
						disabledLine: "var(--colors-neutral-90)",
						disabledText: "var(--colors-neutral-70)",
					},
				},
			},
		},
	},
	plugins: [],
};
