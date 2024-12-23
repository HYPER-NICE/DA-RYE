/**
 * Button 컴포넌트
 *
 * @param {Object} props - 컴포넌트의 속성
 * @param {'button' | 'submit' | 'reset'} [props.type='button'] - 버튼의 타입
 * @param {string} [props.name='버튼'] - 버튼에 표시될 텍스트
 * @param {'default' | 'accent' | 'disabled'} [props.state='default'] - 버튼의 상태
 * @param {Object} props - 나머지 버튼 속성
 * @returns {JSX.Element} 버튼 엘리먼트
 */
export function Button({ type = 'button', name = '버튼', state = 'default', ...props }) {
	return (
		<button
				type={type}
				{...props} // className을 제외한 나머지 속성 전달
				className={`
      px-8 py-3 rounded-md font-semibold
      ${state === 'default' ? 'border border-primary bg-white text-primary' : ''}
      ${state === 'accent' ? 'bg-primary text-white' : ''}
      ${state === 'disabled' ? 'bg-disabledSub text-disabled' : ''}
    `}
				disabled={state === 'disabled'}
		>
			{name}
		</button>
	);
}
