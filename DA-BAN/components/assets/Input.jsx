/**
 * Input 컴포넌트
 *
 * @param {symbol} [props.state=inputState.DEFAULT] - 입력 필드의 상태
 * @param {Object} props - 나머지 입력 필드 속성
 * @returns {JSX.Element} 입력 필드 엘리먼트
 */
export function Input({
	                      state = inputState.DEFAULT,
											  errorMessage,
	                      ...props
                      }) {
	const isDisabled = state === inputState.DISABLED;
	const isError = state === inputState.ERROR;

	return (
			<div>
				<input
						type="text"
						{...props}
						disabled={isDisabled} // HTML `disabled` 속성 설정
						className={`
          px-4 py-2 rounded-md border text-sm focus:outline-none
          focus:border-primary
          ${isDisabled ? 'border-gray-300 bg-gray-100 text-gray-400 cursor-not-allowed' : ''}
          ${isError ? 'border-red-500' : ''}
          ${!isDisabled && !isError ? 'border-gray-300' : ''}
        `}
				/>

				<p className="text-red-500 text-sm">{isError && errorMessage}</p>
			</div>
	);
}

export const inputState = {
	DEFAULT: Symbol('default'),
	DISABLED: Symbol('disabled'),
	ERROR: Symbol('error'),
};

