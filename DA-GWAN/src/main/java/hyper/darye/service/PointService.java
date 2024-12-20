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

    public void usePoint(PointTransactionType pointTransactionType, Long memberId, Long orderMainId, String description, Integer point){
        // 포인트 사용
        if(point <= 0)
            throw new IllegalArgumentException("유효하지 않는 포인트입니다.");
        else {
            memberMapper.usePoint(memberId, point);

            // 트랜잭션 쌓기
            PointTransaction pt = new PointTransaction();
            pt.setPointTransactionTypeId(pointTransactionType.getId());
            pt.setMemberId(memberId);
            pt.setAmount(point);
            pt.setDescription(description);
            pt.setOrderMainId(orderMainId);
            ptMapper.insertPointTransaction(pt);
        }

    }
}
