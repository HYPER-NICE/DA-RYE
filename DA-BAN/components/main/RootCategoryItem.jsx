export function RootCategoryItem({src, alt, name, href}) {	return (
		<a
				href={href}
				className="group flex flex-col items-center gap-3"
		>
			<img src={src} alt={alt}
			     className="w-[5.75rem] h-[5.75rem] rounded-full bg-lightgray bg-center bg-cover bg-no-repeat transition-transform duration-300 transform group-hover:scale-110"
			/>
<div className="select-none">{name}</div>
		</a>
)
}