import Image from "next/image";
import progress from "../../assets/progress.webp";

export default function Progress() {
	return (
		<figure className="">
			<Image src={progress} alt={"공사중"}/>
			<figcaption className="mt-20 text-7xl text-center ">공사중</figcaption>
		</figure>
	)
}