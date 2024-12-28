
export const mockBoardData = [{
	id: 1,
	parentId: null,
	subCategoryId: 1,
	subCategoryName: '중요',
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
	subCategoryId: 2,
	subCategoryName: '일반',
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

export const mockSubCategories = [{
	id: 0, name: '전체' }, {
	id: 1, name: '중요' }, {
	id: 2, name: '일반' }];

export const mockFaqSubCategories = [{
	id: 0, name: '전체' }, {
	id: 1, name: '배송' }, {
	id: 2, name: '환불/교환' }, {
	id: 3, name: '회원' }];

export const mockFaqData = [
	{
		id: 1,
		parentId: null,
		subCategoryId: 1,
		subCategoryName: '배송',
		title: '배송은 얼마나 걸리나요?',
		content: '일반적으로 3-5일 내에 배송됩니다. 지역에 따라 다소 차이가 있을 수 있습니다.',
		fixed: false,
		regDate: '2024-12-27T12:27:07',
		createdDate: '2024-12-27T12:27:07',
		lastModifiedDate: '2024-12-27T12:27:07',
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 2,
		parentId: null,
		subCategoryId: 2,
		subCategoryName: '환불/교환',
		title: '상품을 환불하고 싶은데 어떻게 해야 하나요?',
		content: '상품 수령 후 7일 이내에 고객센터로 연락주시면 환불 절차를 안내해드립니다.',
		fixed: false,
		regDate: '2024-12-27T13:15:22',
		createdDate: '2024-12-27T13:15:22',
		lastModifiedDate: '2024-12-27T13:15:22',
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 3,
		parentId: null,
		subCategoryId: 3,
		subCategoryName: '회원',
		title: '회원가입을 어떻게 하나요?',
		content: '홈페이지 우측 상단의 "회원가입" 버튼을 클릭하여 간단한 정보만 입력하시면 가입이 완료됩니다.',
		fixed: true,
		regDate: '2024-12-28T09:45:10',
		createdDate: '2024-12-28T09:45:10',
		lastModifiedDate: '2024-12-28T09:45:10',
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 4,
		parentId: null,
		subCategoryId: 4,
		subCategoryName: '결제',
		title: '어떤 결제 수단을 지원하나요?',
		content: '신용카드, 체크카드, 계좌이체, 그리고 다양한 전자지갑을 지원하고 있습니다.',
		fixed: false,
		regDate: '2024-12-28T10:30:45',
		createdDate: '2024-12-28T10:30:45',
		lastModifiedDate: '2024-12-28T10:30:45',
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 5,
		parentId: null,
		subCategoryId: 5,
		subCategoryName: '제품',
		title: '상품의 원산지를 알 수 있나요?',
		content: '각 상품 페이지에 원산지 정보가 명시되어 있습니다. 추가적인 정보가 필요하시면 고객센터로 문의주세요.',
		fixed: false,
		regDate: '2024-12-28T11:05:33',
		createdDate: '2024-12-28T11:05:33',
		lastModifiedDate: '2024-12-28T11:05:33',
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 6,
		parentId: null,
		subCategoryId: 1,
		subCategoryName: '배송',
		title: '배송 추적은 어떻게 하나요?',
		content: '주문 완료 후 발송 이메일에 포함된 배송 추적 번호를 통해 배송 상태를 확인하실 수 있습니다.',
		fixed: false,
		regDate: '2024-12-28T12:20:15',
		createdDate: '2024-12-28T12:20:15',
		lastModifiedDate: '2024-12-28T12:20:15',
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 7,
		parentId: null,
		subCategoryId: 2,
		subCategoryName: '환불/교환',
		title: '교환 신청 시 추가 비용이 발생하나요?',
		content: '제품의 하자로 인한 교환의 경우 추가 비용이 발생하지 않습니다. 사용자의 변심으로 인한 교환은 배송비가 발생할 수 있습니다.',
		fixed: false,
		regDate: '2024-12-28T13:50:50',
		createdDate: '2024-12-28T13:50:50',
		lastModifiedDate: '2024-12-28T13:50:50',
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 8,
		parentId: null,
		subCategoryId: 3,
		subCategoryName: '회원',
		title: '비밀번호를 잊어버렸어요.',
		content: '로그인 페이지에서 "비밀번호 찾기"를 클릭하여 비밀번호를 재설정할 수 있습니다.',
		fixed: false,
		regDate: '2024-12-28T14:35:25',
		createdDate: '2024-12-28T14:35:25',
		lastModifiedDate: '2024-12-28T14:35:25',
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 9,
		parentId: null,
		subCategoryId: 4,
		subCategoryName: '결제',
		title: '결제가 중간에 실패했어요. 어떻게 해야 하나요?',
		content: '다시 시도해주시거나, 다른 결제 수단을 이용해 주세요. 문제가 지속될 경우 고객센터로 문의 바랍니다.',
		fixed: false,
		regDate: '2024-12-28T15:10:05',
		createdDate: '2024-12-28T15:10:05',
		lastModifiedDate: '2024-12-28T15:10:05',
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 10,
		parentId: null,
		subCategoryId: 5,
		subCategoryName: '제품',
		title: '제품의 사용 방법을 알고 싶어요.',
		content: '각 제품 페이지에 상세 사용 방법이 안내되어 있습니다. 추가적인 정보가 필요하시면 고객센터로 문의주세요.',
		fixed: false,
		regDate: '2024-12-28T16:45:30',
		createdDate: '2024-12-28T16:45:30',
		lastModifiedDate: '2024-12-28T16:45:30',
		lastModifiedMember: null,
		deletedDate: null
	}
];

export const mockInquirySubCategories = [
	{ id: 0, name: '전체' },
	{ id: 1, name: '답변완료' },
	{ id: 2, name: '미답변' }
];

export const mockInquiryData = [
	{
		id: 1,
		parentId: null,
		subCategoryId: 1,
		subCategoryName: '답변완료',
		title: '배송 지연 관련 문의',
		content: '주문한 상품이 아직 도착하지 않았습니다. 배송이 언제 이루어질지 확인 부탁드립니다.',
		fixed: true,
		regDate: '2024-12-25T10:15:30',
		createdDate: '2024-12-25T10:15:30',
		lastModifiedDate: '2024-12-26T14:20:10',
		lastModifiedMember: '고객센터 직원',
		deletedDate: null
	},
	{
		id: 2,
		parentId: 1, // id 1번 문의에 대한 답변 데이터
		subCategoryId: 1,
		subCategoryName: '답변완료',
		title: '배송 지연 관련 답변',
		content: '고객님, 주문하신 상품은 현재 배송 중이며 2-3일 내에 도착할 예정입니다. 불편을 드려 죄송합니다.',
		fixed: false,
		regDate: '2024-12-26T14:30:00',
		createdDate: '2024-12-26T14:30:00',
		lastModifiedDate: '2024-12-26T14:30:00',
		lastModifiedMember: '고객센터 직원',
		deletedDate: null
	},
	{
		id: 3,
		parentId: null,
		subCategoryId: 2,
		subCategoryName: '미답변',
		title: '상품 교환 요청',
		content: '구매한 상품에 결함이 있어 교환을 요청드립니다. 교환 절차와 소요 시간을 알려주세요.',
		fixed: false,
		regDate: '2024-12-27T09:30:15',
		createdDate: '2024-12-27T09:30:15',
		lastModifiedDate: null,
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 4,
		parentId: null,
		subCategoryId: 1,
		subCategoryName: '답변완료',
		title: '결제 오류 문의',
		content: '결제 시도 중 오류가 발생했습니다. 추가 결제는 이루어지지 않았는지 확인 부탁드립니다.',
		fixed: false,
		regDate: '2024-12-20T15:45:00',
		createdDate: '2024-12-20T15:45:00',
		lastModifiedDate: '2024-12-21T11:10:00',
		lastModifiedMember: '고객센터 직원',
		deletedDate: null
	},
	{
		id: 5,
		parentId: 4, // id 4번 문의에 대한 답변 데이터
		subCategoryId: 1,
		subCategoryName: '답변완료',
		title: '결제 오류 문의 답변',
		content: '결제 내역을 확인한 결과, 중복 결제는 이루어지지 않았습니다. 다른 문의 사항이 있으시면 알려주세요.',
		fixed: false,
		regDate: '2024-12-21T11:20:00',
		createdDate: '2024-12-21T11:20:00',
		lastModifiedDate: '2024-12-21T11:20:00',
		lastModifiedMember: '고객센터 직원',
		deletedDate: null
	},
	{
		id: 6,
		parentId: null,
		subCategoryId: 2,
		subCategoryName: '미답변',
		title: '환불 요청',
		content: '구매한 상품이 마음에 들지 않아 환불을 요청드립니다. 절차를 안내 부탁드립니다.',
		fixed: false,
		regDate: '2024-12-28T08:00:00',
		createdDate: '2024-12-28T08:00:00',
		lastModifiedDate: null,
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 7,
		parentId: null,
		subCategoryId: 2,
		subCategoryName: '미답변',
		title: '회원 탈퇴 문의',
		content: '회원 탈퇴 절차에 대해 알고 싶습니다. 탈퇴 시 데이터는 어떻게 처리되는지도 궁금합니다.',
		fixed: false,
		regDate: '2024-12-29T12:50:45',
		createdDate: '2024-12-29T12:50:45',
		lastModifiedDate: null,
		lastModifiedMember: null,
		deletedDate: null
	},
	{
		id: 8,
		parentId: null,
		subCategoryId: 1,
		subCategoryName: '답변완료',
		title: '포인트 사용 문의',
		content: '적립된 포인트를 어떻게 사용할 수 있는지 알려주세요. 사용 가능한 조건이 있나요?',
		fixed: false,
		regDate: '2024-12-22T17:20:30',
		createdDate: '2024-12-22T17:20:30',
		lastModifiedDate: '2024-12-23T10:05:00',
		lastModifiedMember: '고객센터 직원',
		deletedDate: null
	},
	{
		id: 9,
		parentId: 8, // id 8번 문의에 대한 답변 데이터
		subCategoryId: 1,
		subCategoryName: '답변완료',
		title: '포인트 사용 문의 답변',
		content: '포인트는 1000포인트 이상 적립 시 사용할 수 있습니다. 자세한 사항은 고객센터로 문의주세요.',
		fixed: false,
		regDate: '2024-12-23T10:10:00',
		createdDate: '2024-12-23T10:10:00',
		lastModifiedDate: '2024-12-23T10:10:00',
		lastModifiedMember: '고객센터 직원',
		deletedDate: null
	}
];

