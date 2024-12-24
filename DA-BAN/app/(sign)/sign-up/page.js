import {Input} from "@/components/assets/Input";
import {Button} from "@/components/assets/Button";
import Link from "next/link";


export default function signIn() {
	return (
			<>
				<form className="flex flex-col space-y-4">
					<label
							className={`font-medium`}
					>
						이메일
						<Input placeholder={"이메일을 입력하세요"}/>
					</label>
					<label className={`font-medium`}>
						비밀번호
						<Input placeholder={"비밀번호를 입력하세요"}/>
					</label>

					<Button name={"로그인"}/>

					<Link href="sign-in" className={"py-3 text-center text-semantic-primary-normal rounded border"}>
						회원가입
					</Link>
				</form>
			</>
	);
}

