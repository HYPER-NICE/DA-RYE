'use client'

import { useState } from "react";
import { Input, inputState } from "@/components/assets/Input";
import { Button } from "@/components/assets/Button";

export default function Assets() {
	const [lastInputState, setLastInputState] = useState("disabled"); // 초기 상태를 'disabled'로 설정

	return (
			<div>
				<h1 className="my-4 text-5xl">assets - 컴포넌트 샘플</h1>

				<h2 className="my-4 text-3xl">Buttons</h2>
				<div className="flex gap-4">
					<Button name="기본" state="default" />
					<Button name="액센트" state="accent" />
					<Button name="비활성화" state="disabled" />
				</div>

				<h2 className="my-4 text-3xl">Inputs</h2>
				<div className="flex gap-4 flex-wrap">
					<Input placeholder="플레이스 홀더" state={inputState.ERROR} errorMessage="asdf"/>
					<Input placeholder="기본, 내용 없음" />
					<Input placeholder="비활성화" disabled={true} state={inputState.DISABLED} />
				</div>
			</div>
	);
}
