package hyper.darye.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * MEMBER
 */
@Data
@NoArgsConstructor
public class Member implements Serializable {
    /**
     * 회원 ID (기본 키)
     */
    private Long id;

    /**
     * 회원 이메일
     */
    private String email;

    /**
     * 계정 역할
     */
    private String role;

    /**
     * 비밀번호
     */
    private String password;

    /**
     * 회원 이름
     */
    private String name;

    /**
     * 성별
     */
    private Character sex;

    /**
     * 생년월일
     */
    private Date birthdate;

    /**
     * 휴대폰 번호
     */
    private String mobile;

    /**
     * 현재 보유 포인트
     */
    private Integer point;

    /**
     * 계정 잠금 여부
     */
    private Boolean locked;

    /**
     * 비밀번호 실패 횟수
     */
    private Integer pwFailedCount;

    /**
     * 마지막 로그인 날짜
     */
    private Date latestLoginDate;

    /**
     * 가입 날짜
     */
    private Date regDate;

    /**
     * 레코드 생성 날짜 및 시간
     */
    private Date createdDate;

    /**
     * 마지막 수정된 날짜 및 시간
     */
    private Date lastModifiedDate;

    /**
     * 레코드를 마지막으로 수정한 회원 ID
     */
    private Long lastModifiedMember;

    /**
     * 레코드가 삭제된 날짜 (논리 삭제)
     */
    private Date deletedDate;

    private static final long serialVersionUID = 1L;

    public Member(long id, String email, String password, String rePassword, String name, Character sex, Date birthdate, String mobile) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.birthdate = birthdate;
        this.mobile = mobile;
    }
}