export default function signIn() {
	return (
			<form className="flex flex-col space-y-4">
				<label htmlFor="email">Email:</label>
				<input type="text" id="email" name="email" required />

				<label htmlFor="password">Password:</label>
				<input type="password" id="password" name="password" required />

				<button type="submit" className="bg-blue-500 text-white p-2 rounded">
					로그인
				</button>
			</form>
	);
}
