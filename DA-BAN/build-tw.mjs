import StyleDictionary from 'style-dictionary';
import { makeSdTailwindConfig } from 'sd-tailwindcss-transformer';

const config = makeSdTailwindConfig({
	type: 'all',
	source: ['token.json'], // 토큰 스튜디오에서 만들어준 json 파일 경로
	buildPath: 'build/', // 생성된 파일이 저장될 경로
});

// StyleDictionary 인스턴스 생성
const styleDictionary = new StyleDictionary(config);

// 빌드 실행
await styleDictionary.buildAllPlatforms();
