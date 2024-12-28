import { SECRET_BACKEND_HOST } from '$env/static/private';
import { languageTag } from '$lib/paraglide/runtime.js';
import {
	mockFaqData as mockBoardData,
	mockFaqSubCategories,
	mockFaqSubCategories as mockSubCategories
} from '$lib/dummy/mockBoard.js';

const pageData = {
	title: 'FAQ'
};

/** @type {import('./$types').PageServerLoad} */
export async function load({url}) {
	const paramSubCategory = url.searchParams.get('sub-category') ?? 0;
	const keyword = url.searchParams.get('keyword') ?? '';

	const requestUrl = new URL(`${SECRET_BACKEND_HOST}/api/notification-board`);
	requestUrl.searchParams.set('sub-category', paramSubCategory);
	requestUrl.searchParams.set('keyword', keyword);

	let boards;
	try {
		const response = await fetch(requestUrl);
		boards = await response.json();
	} catch (error) {
		// 요청실패시 더미로 교체
		boards = mockBoardData
			.filter((board) => paramSubCategory == 0 || board.subCategoryId == paramSubCategory)
			.filter((board) => board.title.includes(keyword))
			.map((board) => ({
			...board, regDateFormatted: new Intl.DateTimeFormat(languageTag(), {
				year: 'numeric', month: '2-digit', day: '2-digit'
			}).format(new Date(board.regDate)) // regDate를 포맷
		}));
	}

	return {
		...pageData,
		boards,
		paramSubCategory,
		subCategories : mockSubCategories
	};
}

export const actions = {
	default: async () => {
	}
};