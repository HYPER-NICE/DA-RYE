package hyper.darye.service;

import hyper.darye.dto.*;
import hyper.darye.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    @Autowired
    MemberMapper memberMapper;

    @Autowired
    PointTransactionMapper ptMapper;

    @Autowired
    PointTransactionTypeMapper pttMapper;

    @Autowired
    OrderPaymentMainMapper opmMapper;

    @Autowired
    OrderMainMapper omMapper;

    /**
     * 멤버의 사용가능한 포인트를 수정 합니다.
     * 가감할 포인트 량만 입력하세요.
     * @param pointTransactionType 포인트가 수정된 이유
     * @param memberId 포인트가 사용될 사용자
     * @param orderMainId 포인트가 발생하거나 사용된 주문
     * @param description 설명
     * @param amount 포인트 발생이 발생하 주문 금액(현금성 금액만, 포인트 결제값은 제외해주세요)
     */
    public void update(PointTransactionType pointTransactionType, Long memberId, Long orderMainId, String description, Integer amount) {

        // 포인트 가감
        memberMapper.updatePoint(memberId, (int)(amount * 0.01));

        // 트랜잭션 쌓기
        PointTransaction pt = new PointTransaction();
        pt.setPointTransactionTypeId(pointTransactionType.getId());
        pt.setMemberId(memberId);
        pt.setAmount(amount);
        pt.setDescription(description);
        pt.setOrderMainId(orderMainId);
        ptMapper.insertPointTransaction(pt);
    }
}
