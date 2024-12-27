import { SECRET_BACKEND_HOST } from '$env/static/private';

const mockBoardData = [{
	id: 1,
	parentId: null,
	categoryId: 1,
	categoryName: '중요',
	writerId: 5,
	title: '배송 지연 안내입니다.',
	content: '과도한 물량으로 인해 배송이 지연될 예정입니다.',
	fixed: true,
	regDate: '2024-12-27T12:27:07', // 문자열(ISO 8601)이거나 Date 객체
	createdDate: '2024-12-27T12:27:07',
	lastModifiedDate: '2024-12-27T12:27:07',
	lastModifiedMember: null,
	deletedDate: null
}, {
	id: 2,
	parentId: null,
	categoryId: 2,
	categoryName: '일반',
	writerId: 5,
	title: '용량 변경 안내입니다.',
	content: '녹차 상품의 용량이 변경될 예정입니다.',
	fixed: false,
	regDate: '2024-12-27T12:27:07',
	createdDate: '2024-12-27T12:27:07',
	lastModifiedDate: '2024-12-27T12:27:07',
	lastModifiedMember: null,
	deletedDate: null
}];


const pageData = {
	title: '공지사항'
};

/** @type {import('./$types').LayoutServerLoad} */
export async function load() {
	let boards;
	try {
		const response = await fetch(`${SECRET_BACKEND_HOST}/api/notification-board`, {
			method: 'GET', headers: { 'Content-Type': 'application/json' }
		});

		boards = await response.json();
	} catch (error) {
		boards = mockBoardData.map((board) => ({
			...board, regDateFormatted: new Intl.DateTimeFormat('ko-KR', {
				year: 'numeric', month: '2-digit', day: '2-digit'
			}).format(new Date(board.regDate)) // regDate를 포맷
		}));
	}

	return {
		title: '공지사항', boards
	};
}

export const actions = {
	default: async () => {

	}
};