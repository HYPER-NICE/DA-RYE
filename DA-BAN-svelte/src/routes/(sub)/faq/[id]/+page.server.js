import { mockFaqData as mockBoardData } from '$lib/dummy/mockBoard.js';
import { languageTag } from '$lib/paraglide/runtime.js';
import { SECRET_BACKEND_HOST } from '$env/static/private';

const pageData = {
	title: 'FAQ'
}

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
	const boardId = params.id;
	const requestUrl = new URL(`${SECRET_BACKEND_HOST}/api/faq-board/${boardId}`);

	let board;
	try {
		const response = await fetch(requestUrl);
		board = await response.json();
	} catch (error) {
		// 요청실패시 더미로 교체
		board = mockBoardData
			.filter((board) => board.id == boardId)
			.map((board) => ({
				...board, regDateFormatted: new Intl.DateTimeFormat(languageTag(), {
					year: 'numeric', month: '2-digit', day: '2-digit'
				}).format(new Date(board.regDate)) // regDate를 포맷
			}))[0];
	}

	return {
		...pageData,
		board,
	};
}