package hyper.darye.model.dto.controller.response;

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


}
