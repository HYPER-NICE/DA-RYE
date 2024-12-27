package hyper.darye.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBoardDTO {

    private Long id;
    private String title;
    private Long subCategoryId;
    private String subCategoryName;
    private Date regDate;

    // 시간을 제외한 날짜만 설정
    public void setRegDate(Date regDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 형식 설정

        // 날짜를 문자열로 변환
        String dateString = sdf.format(regDate);

        try {
            // 변환된 날짜 문자열을 다시 Date 객체로 변환 (시간 부분이 00:00:00으로 설정됨)
            this.regDate = sdf.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리
        }
    }
}
