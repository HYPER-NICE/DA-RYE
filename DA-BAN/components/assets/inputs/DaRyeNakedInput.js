/**
 * DaRyeNakedInput 컴포넌트
 *
 * @param {string} [props.className] - 추가 Tailwind 클래스
 * @param {React.InputHTMLAttributes<HTMLInputElement>} props - 입력 필드 속성
 * @returns {JSX.Element} 입력 필드 엘리먼트
 */
export function DaRyeNakedInput({chidren, className = '', ...props}) {
	return (<input
			{...props}
			className={`px-4 py-2 rounded border text-sm focus:outline-semantic-primary-normal disabled:bg-semantic-component-fill-disabled ${className}`}
	/>);
}