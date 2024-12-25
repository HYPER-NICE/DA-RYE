import {DaRyeNakedButton} from "@/components/assets/actions/buttons/DaRyeNakedButton";

/**
 * DaRyeNakedButton 컴포넌트
 *
 * @param {Object} props - 버튼 속성
 * @param {React.ReactNode} props.children - 버튼 내용
 * @param {'small' | 'medium' | 'large'} [props.size='medium'] - 버튼 크기 (small, medium, large)
 * @param {string} [props.className=''] - 추가 Tailwind 클래스
 * @param {React.ButtonHTMLAttributes<HTMLButtonElement>} props - 기본 HTML 버튼 속성
 * @returns {JSX.Element} 버튼 엘리먼트
 */
export function DaRyeAccentButton({children, size = 'medium', className = '', ...props}) {
	return (<DaRyeNakedButton
			size={size}
			{...props}
			className={`text-semantic-component-static-white bg-semantic-primary-normal rounded ${className}`}
	>
		{children}
	</DaRyeNakedButton>);
}
