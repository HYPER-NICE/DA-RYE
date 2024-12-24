import {Navigation} from "@/components/common/Navigation";
import {Footer} from "@/components/common/Footer";
import {Header} from "@/components/common/Header";

export default function RootLayout({children}) {
	return (
		<>{/* 푸터를 위한 바텀 패딩, 좀 더 나이스한 방식이 필요 */}
			<main className="pb-[6.25rem]">
				<Header/>
				{children}
				<Footer/>
			</main>
			<Navigation/>
		</>
	);
}
