import Image from "next/image";
import {Banner} from "@/components/main/Banner";
import {RootCategory} from "@/components/main/RootCategory";
import {TodaySuggestions} from "@/components/main/TodaySuggestions";

export default function Home() {
  return (
      <div>

        <Banner/>

        <div
            className={'p-12 px-4 flex flex-col gap-12'}
        >
          <RootCategory />
          <TodaySuggestions />
        </div>
      </div>
  );
}
