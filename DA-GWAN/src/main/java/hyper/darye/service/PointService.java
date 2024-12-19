package hyper.darye.service;

import hyper.darye.dto.*;
import hyper.darye.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    public void addPoints(Long memberId, String description) {
        PointTransaction pt = new PointTransaction();
        List<PointTransactionType> pttList = pttMapper.selectAll();
        Long pointTransactionTypeId = pttList.get(0).getId();
        Long orderId = omMapper.selectByMemberId(memberId).getId();
        Integer point = opmMapper.selectByOrderNo(orderId).getTotalAmount() / 100; // Integer이라서 소수점 안나옴


        if(point<=0){
            throw new IllegalArgumentException("포인트는 0보다 커야 합니다.");
        }

        try{
            if(opmMapper.selectByOrderNo(orderId).getOrderId().equals(orderId)) { // orderMain 과 orderPaymemtMain의 orderMain이 같읕때
                // 멤버 포인트 적립
                memberMapper.updatePoint(memberId, point);

                // 포인트 트랜잭션 삽입
                pt.setMemberId(memberId);
                pt.setPointTransactionTypeId(pointTransactionTypeId);
                pt.setOrderMainId(opmMapper.selectByOrderNo(orderId).getOrderId());
                pt.setAmount(point);
                if (description == null) {
                    description = pttList.get(0).getDescription();
                    pt.setDescription(description);
                }
                ptMapper.insertPointTransaction(pt);
            }
        }
        catch (Exception e){
            throw new RuntimeException("포인트 적립 중 문제가 발생했습니다.", e);
        }
    }
}
