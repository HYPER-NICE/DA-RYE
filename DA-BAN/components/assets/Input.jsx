/**
 * Input 컴포넌트
 *
 * @param {symbol} [props.state=inputState.DEFAULT] - 입력 필드의 상태
 * @param {string} [props.className] - 추가 Tailwind 클래스
 * @param {string} [props.errorMessage] - 에러 메시지
 * @param {Object} props - 나머지 입력 필드 속성
 * @returns {JSX.Element} 입력 필드 엘리먼트
 */
export function Input({
	                      state = inputState.DEFAULT,
	                      errorMessage,
	                      className = '',
	                      ...props
                      }) {
	const isDisabled = state === inputState.DISABLED;
	const isError = state === inputState.ERROR;

	return (
			<div>
				<input
						type="text"
						{...props}
						disabled={isDisabled}
						className={`px-4 py-2 rounded-md border text-sm focus:outline-none
          focus:border-semantic-primary-normal
          ${isDisabled ? 'border-semantic-line-disabled bg-semantic-component-fill-disabled text-semantic-labelText-disabled cursor-not-allowed' : ''}
          ${isError ? 'border-semantic-status-error' : ''}
          ${!isDisabled && !isError ? 'border-semantic-line-normal' : ''}
          ${className} // 추가 클래스 적용
        `}
				/>
				{isError && (
						<p className="text-semantic-status-error text-sm">{errorMessage}</p>
				)}
			</div>
	);
}

export const inputState = {
	DEFAULT: Symbol('default'),
	DISABLED: Symbol('disabled'),
	ERROR: Symbol('error'),
};
