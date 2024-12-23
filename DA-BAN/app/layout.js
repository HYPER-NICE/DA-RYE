import "./globals.css";
import localFont from 'next/font/local';

// todo: 올바른 폰트 사용법으로 고치기, 스벨트에서는 더 나이스한 코드가 나왔는데, 넥스트는 어떻게 하는지 알아보기
export const pretendardVariable = localFont({
	src: '../node_modules/pretendard/dist/web/variable/woff2/PretendardVariable.woff2',
	display: 'swap',
	weight: '45 920',
	variable: '--font-pretendard-variable',
});

export const metadata = {
	title: "다례", description: "다례는 차를 사랑하는 사람들을 위한 공간입니다.",
};

export default function RootLayout({children}) {
	return (
		<html lang="ko">
			<body
					className={`${pretendardVariable.variable} font-pretendard antialiased w-[600px] min-h-[600px] mx-auto relative`}
			>
				{children}
			</body>
		</html>
	);
}
