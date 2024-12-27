/**
 * Button 컴포넌트
 *
 * @param {Object} props - 컴포넌트의 속성
 * @param {'button' | 'submit' | 'reset'} [props.type='button'] - 버튼의 타입
 * @param {string} [props.name='버튼'] - 버튼에 표시될 텍스트
 * @param {'default' | 'accent' | 'disabled'} [props.state='default'] - 버튼의 상태
 * @param {string} [props.className=''] - 추가 Tailwind 클래스
 * @returns {JSX.Element} 버튼 엘리먼트
 */
export function Button({ type = 'button', name = '버튼', state = 'default', className = '', ...props }) {
	return (
			<button
					type={type}
					{...props}
					className={`px-8 py-3 rounded-md font-semibold
        ${state === 'default' ? 'border border-semantic-button-normalLine bg-semantic-button-normalFill text-semantic-button-primary' : ''}
        ${state === 'accent' ? 'bg-semantic-button-primary text-semantic-labelText-inverse' : ''}
        ${state === 'disabled' ? 'bg-semantic-button-disabledFill text-semantic-button-disabledText border-semantic-button-disabledLine cursor-not-allowed' : ''}
        ${className} // 추가 클래스 적용
      `}
					disabled={state === 'disabled'}
			>
				{name}
			</button>
	);
}
