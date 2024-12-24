import {RootCategoryItem} from "@/components/main/RootCategoryItem";

export function RootCategory() {

// TODO: 루트 카테고리 목록을 제공하는 API가 필요합니다.
	const categories = [
		{src: "/main/sample/root-category-01.png", alt: "녹차/말차", name: "녹차/녹차"},
		{src: "/main/sample/root-category-02.png", alt: "명차", name: "명차"},
		{src: "/main/sample/root-category-03.png", alt: "발효차", name: "발효차"},
		{src: "/main/sample/root-category-04.png", alt: "홍차", name: "홍차"},
		{src: "/main/sample/root-category-05.png", alt: "허브티", name: "허브티"},
	];

	return (<div className="flex justify-between items-center self-stretch">
		{categories.map((category, index) => (<RootCategoryItem
				key={index}
				src={category.src}
				alt={category.alt}
				name={category.name}
		/>))}
	</div>);
}