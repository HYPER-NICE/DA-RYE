export function DaRyeHadding1({children, className = ''}) {
	return (<h1 className={`text-xl font-bold ${className}`}>
		{children}
	</h1>);
}