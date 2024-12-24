export function Navigation() {
	return (
			<div
					className="flex justify-around p-4 pb-8 fixed bottom-0 w-[600px] bg-white"
			>
				<button className={'flex flex-col items-center gap-1'}>
					<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
						<path fillRule="evenodd" clipRule="evenodd"
						      d="M9.49617 2.71266C11.0149 1.76244 12.9851 1.76245 14.5038 2.71267L19.9584 6.12538C21.233 6.92288 22 8.2739 22 9.7217V17.691C22 20.0708 19.9649 22 17.4545 22H6.54545C4.03507 22 2 20.0708 2 17.691V9.7217C2 8.27391 2.76697 6.92288 4.04163 6.12538L9.49617 2.71266ZM8.36364 15.9674C8.36364 15.4915 8.77065 15.1056 9.27273 15.1056H14.7273C15.2294 15.1056 15.6364 15.4915 15.6364 15.9674C15.6364 16.4434 15.2294 16.8292 14.7273 16.8292H9.27273C8.77065 16.8292 8.36364 16.4434 8.36364 15.9674Z"
						      fill="#1E1E1E"/>
					</svg>
					<div>홈</div>
				</button>
				<button className={'flex flex-col items-center gap-1'}>
					<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
						<path fillRule="evenodd" clipRule="evenodd"
						      d="M2.57153 6C2.57153 5.44772 2.95529 5 3.42868 5H20.5715C21.0449 5 21.4287 5.44772 21.4287 6C21.4287 6.55228 21.0449 7 20.5715 7H3.42868C2.95529 7 2.57153 6.55228 2.57153 6Z"
						      fill="#1E1E1E"/>
						<path fillRule="evenodd" clipRule="evenodd"
						      d="M2.57153 12C2.57153 11.4477 2.95529 11 3.42868 11H20.5715C21.0449 11 21.4287 11.4477 21.4287 12C21.4287 12.5523 21.0449 13 20.5715 13H3.42868C2.95529 13 2.57153 12.5523 2.57153 12Z"
						      fill="#1E1E1E"/>
						<path fillRule="evenodd" clipRule="evenodd"
						      d="M2.57153 18C2.57153 17.4477 2.95529 17 3.42868 17H20.5715C21.0449 17 21.4287 17.4477 21.4287 18C21.4287 18.5523 21.0449 19 20.5715 19H3.42868C2.95529 19 2.57153 18.5523 2.57153 18Z"
						      fill="#1E1E1E"/>
					</svg>
					<div>메뉴</div>
				</button>
				<button className={'flex flex-col items-center gap-1'}>
					<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
						<path fillRule="evenodd" clipRule="evenodd"
						      d="M11.9511 0.857147C9.58419 0.857147 7.66541 2.77593 7.66541 5.14286V6H5.90483C4.99501 6 4.24366 6.71077 4.19319 7.6192L3.43128 21.3335C3.37669 22.3161 4.15878 23.1429 5.14293 23.1429H18.7593C19.7435 23.1429 20.5256 22.3161 20.471 21.3335L19.7091 7.6192C19.6586 6.71077 18.9073 6 17.9974 6H16.2368V5.14286C16.2368 2.77593 14.3181 0.857147 11.9511 0.857147ZM14.5226 7.71429V8.57143C14.5226 9.04482 14.9063 9.42858 15.3797 9.42858C15.8531 9.42858 16.2368 9.04482 16.2368 8.57143V7.71429L17.9974 7.71429L18.7593 21.4286H5.14293L5.90483 7.71429H7.66541V8.57143C7.66541 9.04482 8.04917 9.42858 8.52256 9.42858C8.99594 9.42858 9.3797 9.04482 9.3797 8.57143V7.71429L14.5226 7.71429ZM14.5226 6V5.14286C14.5226 3.7227 13.3713 2.57143 11.9511 2.57143C10.531 2.57143 9.3797 3.7227 9.3797 5.14286V6H14.5226Z"
						      fill="#1E1E1E"/>
					</svg>
					<div>장바구니</div>
				</button>
				<button className={'flex flex-col items-center gap-1'}>
					<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
						<g clipPath="url(#clip0_462_1097)">
							<path fillRule="evenodd" clipRule="evenodd"
							      d="M12 2C8.68629 2 6 4.68629 6 8C6 11.3137 8.68629 14 12 14C15.3137 14 18 11.3137 18 8C18 4.68629 15.3137 2 12 2ZM8 8C8 5.79086 9.79086 4 12 4C14.2091 4 16 5.79086 16 8C16 10.2091 14.2091 12 12 12C9.79086 12 8 10.2091 8 8Z"
							      fill="#1E1E1E"/>
							<path
									d="M6.46334 18.0231L7.88563 17.7645C10.6062 17.2699 13.3938 17.2699 16.1144 17.7645L17.5367 18.0231C18.9631 18.2825 20 19.5249 20 20.9748V25.0001H22V20.9748C22 18.5583 20.2719 16.4877 17.8944 16.0554L16.4721 15.7968C13.515 15.2591 10.485 15.2591 7.52786 15.7968L6.10557 16.0554C3.72812 16.4877 2 18.5583 2 20.9748V25.0001H4V20.9748C4 19.5249 5.03687 18.2825 6.46334 18.0231Z"
									fill="#1E1E1E"/>
						</g>
						<defs>
							<clipPath id="clip0_462_1097">
								<rect width="24" height="24" fill="white"/>
							</clipPath>
						</defs>
					</svg>
					<div>마이페이지</div>
				</button>
			</div>
	)
}