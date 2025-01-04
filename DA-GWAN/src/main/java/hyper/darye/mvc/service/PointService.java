package hyper.darye.mvc.service;

import hyper.darye.mvc.mapper.MemberMapper;
import hyper.darye.mvc.mapper.PointTransactionMapper;
import hyper.darye.mvc.mapper.PointTransactionTypeMapper;
import hyper.darye.mvc.model.entity.Member;
import hyper.darye.mvc.model.entity.PointTransaction;
import hyper.darye.mvc.model.entity.PointTransactionType;
import hyper.darye.mapper.*;
import hyper.darye.mvc.mapper.order.OrderMainMapper;
import hyper.darye.mvc.mapper.order.payment.OrderPaymentMainMapper;
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

    @Autowired
    private PointTransactionTypeMapper pointTransactionTypeMapper;

    /**
     * 멤버의 사용가능한 포인트를 수정 합니다.
     * 가감할 포인트 량만 입력하세요.
     * @param pointTransactionType 포인트가 수정된 이유
     * @param memberId 포인트가 사용될 사용자
     * @param orderMainId 포인트가 발생하거나 사용된 주문
     * @param description 설명
     * @param amount 포인트 발생이 발생하 주문 금액(현금성 금액만, 포인트 결제값은 제외해주세요)
     */
    public void addPoint(PointTransactionType pointTransactionType, Long memberId, Long orderMainId, String description, Integer amount) {

        // 포인트 적립
        memberMapper.addPoint(memberId, (int)(amount * 0.01));

        // 트랜잭션 쌓기
        PointTransaction pt = new PointTransaction();
        pt.setPointTransactionTypeId(pointTransactionType.getId());
        pt.setMemberId(memberId);
        pt.setAmount(amount);
        pt.setDescription(description);
        pt.setOrderMainId(orderMainId);
        ptMapper.insertPointTransaction(pt);
    }

    //포인트 사용 취소
    public void cancelUsePoint(PointTransactionType pointTransactionType, Long memberId, Long orderMainId, String description){
        Integer cancelPoint = (Integer)( ptMapper.selectByMemberIdAndOrderId(memberId, orderMainId).getAmount() * -1);

        // 포인트 반환
        memberMapper.addPoint(memberId, cancelPoint);

        // 트랜잭션 쌓기
        PointTransaction pt = new PointTransaction();
        pt.setPointTransactionTypeId(pointTransactionType.getId());
        pt.setMemberId(memberId);
        pt.setAmount(cancelPoint);
        pt.setDescription(description);
        pt.setOrderMainId(orderMainId);
        ptMapper.insertPointTransaction(pt);
    }

    //포인트 사용
    public void usePoint(PointTransactionType pointTransactionType, Long memberId, Long orderMainId, String description, Integer point){

        memberMapper.usePoint(memberId, point);

        // 트랜잭션 쌓기
        PointTransaction pt = new PointTransaction();
        pt.setPointTransactionTypeId(pointTransactionType.getId());
        pt.setMemberId(memberId);
        pt.setAmount((Integer) (-point));
        pt.setDescription(description);
        pt.setOrderMainId(orderMainId);
        ptMapper.insertPointTransaction(pt);

    }

    // 포인트 조회
    public Member selectPoint(Long memberId){
        return memberMapper.selectByPrimaryKey(memberId);
    }

}
