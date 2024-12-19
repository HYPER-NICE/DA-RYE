package hyper.darye.service;

import hyper.darye.dto.PointTransaction;
import hyper.darye.dto.PointTransactionType;
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
    @Autowired
    private PointTransactionTypeMapper pointTransactionTypeMapper;

    public void cancelUsePoint(PointTransactionType pointTransactionType, Long memberId, Long orderMainId, String description){
        Integer cancelPoint = ptMapper.selectByMemberIdAndOrderId(memberId, orderMainId).getAmount() * -1;

        // 포인트 반환
        memberMapper.updatePoint(memberId, cancelPoint);

        // 트랜잭션 쌓기
        PointTransaction pt = new PointTransaction();
        pt.setPointTransactionTypeId(pointTransactionType.getId());
        pt.setMemberId(memberId);
        pt.setAmount(cancelPoint);
        pt.setDescription(description);
        pt.setOrderMainId(orderMainId);
        ptMapper.insertPointTransaction(pt);
    }
}
