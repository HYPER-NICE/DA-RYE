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
export function DaRyeNakedButton({children, size = 'medium', className = '', ...props}) {
	// 사이즈별 클래스 정의
	const sizeClasses = {
		small: 'px-4 py-3 text-sm', medium: 'px-8 py-3 text-base', large: 'px-8 py-4 text-lg',
	};

	return (<button
			{...props}
			className={`font-semibold ${sizeClasses[size]} ${className}`}
	>
		{children}
	</button>);
}