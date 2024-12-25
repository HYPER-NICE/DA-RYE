/**
 * DaRyeLabel 컴포넌트
 *
 * @param {string} props.title - 레이블 제목
 * @param {string} [props.subTitle] - 레이블 부제목
 * @param {React.LabelHTMLAttributes<HTMLLabelElement>} props - 레이블 속성
 * @param {React.ReactNode} props.children - 레이블 내용
 */
export function DaRyeLabel({title, subTitle, children}) {
	return (<label>
		<span className={`font-medium`}>
			<span>{title}</span>
			{subTitle && <span className={`text-xs text-semantic-primary-normal`}>{`(${subTitle})`}</span>}
		</span>
		{children}
	</label>);
}