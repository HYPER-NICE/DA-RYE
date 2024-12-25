'use client';

import {DaRyeLabel} from "@/components/assets/inputs/DaRyeLabel";
import {DaRyeNakedInput} from "@/components/assets/inputs/DaRyeNakedInput";
import {DaRyeAccentButton} from "@/components/assets/actions/buttons/DaRyeAccentbutton";
import {DaRyeHadding1} from "@/components/assets/DaRyeHadding1";
import {Prev} from "@/components/assets/actions/navi/Prev";
import {signUpAction} from "@/actions/signUpAction";
import {useActionState} from "react";

export default function SignIn() {
	const [state, formAction] = useActionState(signUpAction, {
		name: '',
		email: '',
		contact: '',
		password: '',
		confirmPassword: '',
		valid: {},
	});

	return (
			<div>
				{/* 헤딩 영역 */}
				<div className="p-4 flex gap-4">
					<Prev className="mb-4">뒤로가기</Prev>
					<DaRyeHadding1>회원가입</DaRyeHadding1>
				</div>

				{/* 본문 영역 */}
				<div className="p-4">
					<form action={formAction}
							className="flex flex-col gap-8 group">
						{/* 데이터 영역 */}
						<div className="flex flex-col gap-2">
							<DaRyeLabel title={"이름"} subTitle={"필수"}>
								<DaRyeNakedInput
										type={"text"}
										required
										placeholder={"이름을 입력하세요"}
										defaultValue={state.name}
										name={"name"}
										pattern={"^[가-힣a-zA-Z ]{2,50}$"}  // 한글/영문/공백, 2~50자
										title={"이름은 한글, 영문 및 공백만 포함하며 2자 이상 50자 이하여야 합니다."}
										className="w-full"
								/>
								<p className={"text-semantic-status-error"}>{state.valid.name}</p>
							</DaRyeLabel>

							<DaRyeLabel title={"이메일"} subTitle={"필수"}>
								<DaRyeNakedInput
										type="email"
										required
										placeholder={"이메일을 입력하세요"}
										defaultValue={state.email}
										name={"email"}
										title={"올바른 이메일 주소를 입력하세요."}
										className="w-full"
								/>
								<p className={"text-semantic-status-error"}>{state.valid.email}</p>
							</DaRyeLabel>

							<DaRyeLabel title={"연락처"} subTitle={"필수"}>
								<DaRyeNakedInput
										type="tel"
										required
										placeholder={"연락처를 입력하세요"}
										defaultValue={state.contact}
										name={"contact"}
										pattern={"^01[0-9]-[0-9]{3,4}-[0-9]{4}$"}  // 한국 휴대전화 형식
										title={"연락처는 010-1234-5678 형식으로 입력해야 합니다."}
										className="w-full"
								/>
								<p className={"text-semantic-status-error"}>{state.valid.contact}</p>
							</DaRyeLabel>

							<DaRyeLabel title={"비밀번호"} subTitle={"필수"}>
								<DaRyeNakedInput
										type="password"
										required
										placeholder={"비밀번호를 입력하세요"}
										defaultValue={state.password}
										name={"password"}
										pattern={"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"}
										title={"비밀번호는 최소 8자, 영문, 숫자, 특수문자를 포함해야 합니다."}
										className="w-full"
								/>
								<p className={"text-semantic-status-error"}>{state.valid.password}</p>
							</DaRyeLabel>

							<DaRyeLabel title={"비밀번호 확인"} subTitle={"필수"}>
								<DaRyeNakedInput
										type="password"
										required
										placeholder={"비밀번호를 다시 입력하세요"}
										defaultValue={state.confirmPassword}
										name={"confirmPassword"}
										title={"비밀번호가 일치해야 합니다."}
										className="w-full"
								/>
								<p className={"text-semantic-status-error"}>{state.valid.confirmPassword}</p>
							</DaRyeLabel>

						</div>

						{/* 액션 영역 */}
						<DaRyeAccentButton
								type="submit"
								className="w-full group-invalid:bg-semantic-button-disabledFill group-invalid:text-semantic-button-disabledText group-invalid:pointer-events-none"
						>
							회원가입
						</DaRyeAccentButton>
					</form>
				</div>
			</div>
	);
}

